package ca.toropov.microcad.fogPrettifier.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Author: toropov
 * Date: 10/1/2018
 */
public class LenovoLookup {
    private static Map<String, String> cache = new HashMap<>();

    public static void init() {
        File file = new File(System.getProperty("user.home") + "/Documents/fogPrettifier/.lookup");
        file.getParentFile().mkdirs();
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    String[] split = scanner.nextLine().split(":");
                    cache.put(split[0], split[1]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                System.out.println("Created cache file: " + file.createNewFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String lookup(String model) {
        if (cache.containsKey(model)) {
            return cache.get(model);
        }

        try {
            URL url = new URL("https://pcsupport.lenovo.com/ca/en/products/" + model);

            URLConnection connection = url.openConnection();
            try (Scanner scanner = new Scanner(connection.getInputStream())) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.contains("Product.Sub-Series")) {
                        String parse = line.replace(" ", "")
                                .replace("\"", "")
                                .replace("<metaname=Product.Sub-Seriescontent=", "")
                                .replace("/>", "")
                                .replace("THINKPAD-", "");

                        cache(model, parse);
                        return parse;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return model;
    }

    private static void cache(String key, String value) {
        cache.put(key, value);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.home") + "/Documents/fogPrettifier/.lookup", true))) {
            writer.write(key + ":" + value);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
