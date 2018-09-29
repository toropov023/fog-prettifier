package ca.toropov.microcad.fogPrettifier;

import javafx.application.Platform;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Author: toropov
 * Date: 9/28/2018
 */
class Prettifier {
    static void prettify(List<File> files, Consumer<String> callback) {
        ThreadUtil.onNewThread(() -> {
            AtomicInteger failed = new AtomicInteger();
            for (File file : files) {
                if (prettify(file)) {
                    failed.incrementAndGet();
                }
            }

            Platform.runLater(() -> callback.accept(failed.get() == 0 ? "Success!" : ("Failed to load " + failed.get() + " files out of " + files.size())));
        });
    }

    private static boolean prettify(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                //TODO
            }

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
