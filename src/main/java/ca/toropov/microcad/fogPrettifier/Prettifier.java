package ca.toropov.microcad.fogPrettifier;

import ca.toropov.microcad.fogPrettifier.util.LenovoLookup;
import ca.toropov.microcad.fogPrettifier.util.ThreadUtil;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Author: toropov
 * Date: 9/28/2018
 */
class Prettifier {
    static void prettify(File file, BiConsumer<String, Color> statusUpdater, Consumer<FogSummary> callback) {
        ThreadUtil.onNewThread(() -> {
            FogSummary summary = prettify(file, statusUpdater);
            Platform.runLater(() -> callback.accept(summary));
        });
    }

    private static FogSummary prettify(File file, BiConsumer<String, Color> statusUpdater) {
        FogSummary summary = new FogSummary();

        statusUpdater.accept("Working...", Color.GRAY);
        int size;
        int i = 0;
        try {
            size = (int) Files.lines(Paths.get(file.getPath())).count();
        } catch (IOException e) {
            e.printStackTrace();
            return summary;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); //Skip headers

            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\",\"");
                Laptop laptop = Laptop.builder()
                        .grade(split[13])
                        .manufacturer(split[15])
                        .model(split[16])
                        .serial(split[17])
                        .cpu(split[28])
                        .memory(split[30])
                        .drive(split[31])
                        .build();

                if (laptop.getManufacturer().equalsIgnoreCase("lenovo")) {
                    laptop.setModel(LenovoLookup.lookup(laptop.getModel()).toUpperCase());
                }

                summary.getLaptops().add(laptop);

                i++;
                int percent = (int) (i / (double) size * 100D);
                statusUpdater.accept("Working... (" + percent + "%)", Color.GRAY);
            }

            statusUpdater.accept("Complete! (" + summary.getLaptops().size() + " units)", Color.GREEN);
            return summary;
        } catch (IOException e) {
            statusUpdater.accept("Error occurred! Please try again", Color.RED);
            e.printStackTrace();
            return null;
        }
    }
}
