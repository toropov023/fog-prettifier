package ca.toropov.microcad.fogPrettifier;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * Author: toropov
 * Date: 9/28/2018
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select FOG generated report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Downloads/"));

        Button btn = new Button("Choose files...");
        btn.setOnAction(event -> {
            List<File> file = fileChooser.showOpenMultipleDialog(primaryStage);
            if (file != null) {
                btn.setText("Working...");
                btn.setDisable(true);

                Prettifier.prettify(file, (response) -> {
                    btn.setDisable(false);
                    btn.setText("Choose files...");

                    //TODO add text for response
                });
            }
        });
        grid.getChildren().add(btn);

        primaryStage.setTitle("Fog Report Prettifier");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icons/256.png")));
        primaryStage.setScene(new Scene(grid, 300, 350));
        primaryStage.setMinWidth(150);
        primaryStage.setMinHeight(100);
        primaryStage.show();
    }
}
