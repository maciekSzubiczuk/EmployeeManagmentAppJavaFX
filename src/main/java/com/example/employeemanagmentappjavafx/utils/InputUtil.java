package com.example.employeemanagmentappjavafx.utils;

public class InputUtil {

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public static boolean isValidInput(String text, String input) {
        // Check if the input is a digit or dash
        if (!input.matches("[0-9\\-]")) {
            return false;
        }

        // Append the input to the existing text
        String newText = text + input;

        // Check if the resulting text matches the desired format
        return newText.matches(DATE_REGEX);
    }

}
