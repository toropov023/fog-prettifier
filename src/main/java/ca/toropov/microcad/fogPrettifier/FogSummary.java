package ca.toropov.microcad.fogPrettifier;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: toropov
 * Date: 10/4/2018
 */
@Getter
public class FogSummary {
    private final Set<Laptop> laptops = new HashSet<>();

    public ObservableList<Row> generateSummary() {
        ObservableList<Row> rows = FXCollections.observableArrayList();

        Set<String> models = laptops.parallelStream().map(Laptop::getModel).collect(Collectors.toSet());
        for (String model : models) {
            List<Laptop> laptops = this.laptops.parallelStream()
                    .filter(l -> l.getModel().equals(model))
                    .sorted(Comparator.comparing(Laptop::getGrade))
                    .collect(Collectors.toList());

            int aGrades = (int) laptops.parallelStream().filter(l -> l.isGrade("a")).count();
            int bGrades = (int) laptops.parallelStream().filter(l -> l.isGrade("b")).count();
            int cGrades = (int) laptops.parallelStream().filter(l -> l.isGrade("c")).count();
            int total = aGrades + bGrades + cGrades;

            rows.add(new Row(new SimpleStringProperty(model),
                    new SimpleIntegerProperty(aGrades),
                    new SimpleIntegerProperty(bGrades),
                    new SimpleIntegerProperty(cGrades),
                    new SimpleIntegerProperty(total)
            ));
        }

        rows.sort(Comparator.comparing(Row::getModel));
        return rows;
    }

    public File saveFile(String name) {
        String path = System.getProperty("user.home") + "/Documents/fogPrettifier/" + name + ".csv";
        File file = new File(path);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write(Laptop.headers() + "\n");

                for (Laptop laptop : laptops.stream().sorted().collect(Collectors.toList())) {
                    writer.write(laptop.toString() + "\n");
                }

                return file;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequiredArgsConstructor
    public class Row {
        private final SimpleStringProperty model;
        private final SimpleIntegerProperty aGrades;
        private final SimpleIntegerProperty bGrades;
        private final SimpleIntegerProperty cGrades;
        private final SimpleIntegerProperty total;

        public String getModel() {
            return model.get();
        }

        public Integer getAGrades() {
            return aGrades.get();
        }

        public Integer getBGrades() {
            return bGrades.get();
        }

        public Integer getCGrades() {
            return cGrades.get();
        }

        public Integer getTotal() {
            return total.get();
        }
    }
}
