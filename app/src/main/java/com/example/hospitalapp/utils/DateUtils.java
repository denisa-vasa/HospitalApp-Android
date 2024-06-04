package com.example.hospitalapp.utils;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Convert LocalDate to String
    public static String convertToLocalDateString(LocalDate date) {
        if (date != null) {
            return date.format(DATE_FORMAT);
        }
        return null;
    }

    // Convert String to LocalDate
    public static LocalDate convertToLocalDate(String dateString) {
        // Parse the string and return LocalDate
        return LocalDate.parse(dateString, DATE_FORMAT);
    }
}
