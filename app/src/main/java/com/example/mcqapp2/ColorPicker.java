package com.example.mcqapp2;

public class ColorPicker {
    static String[] color = {"#F16E33", "#C83125", "#6FD93E", "#0E5990", "#89B9DC", "#7F34C7", "#E43FBD", "#E4EA26"};

    static int currentColor = 0;

    public static String getCurrentColor() {
        currentColor = (currentColor + 1) % color.length;
        return color[currentColor];
    }
}