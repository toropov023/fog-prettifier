package ca.toropov.microcad.fogPrettifier;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

/**
 * Author: toropov
 * Date: 10/4/2018
 */
@Builder
@Getter
public class Laptop implements Comparable<Laptop> {
    private String manufacturer;
    @Setter
    private String model;
    private String serial;
    private String cpu;
    private String memory;
    private String drive;
    private String grade;

    public static String headers() {
        StringBuilder builder = new StringBuilder().append("\"");
        for (Field field : Laptop.class.getDeclaredFields()) {
            field.setAccessible(true);

            builder.append(field.getName().toUpperCase()).append("\",\"");
        }
        builder.append("\"");

        return builder.toString();
    }

    public boolean isGrade(String grade) {
        return this.grade.toLowerCase().startsWith(grade.toLowerCase());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("\"");
        try {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);

                builder.append(String.valueOf(field.get(this))).append("\",\"");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        builder.append("\"");

        return builder.toString();
    }

    @Override
    public int compareTo(Laptop other) {
        String a1 = model;
        String a2 = other.getModel();
        int sComp = a1.compareTo(a2);

        if (sComp != 0) {
            return sComp;
        }

        String b1 = grade;
        String b2 = other.getGrade();
        return b1.compareTo(b2);
    }
}