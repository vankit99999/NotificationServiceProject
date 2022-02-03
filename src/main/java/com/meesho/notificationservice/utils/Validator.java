package com.meesho.notificationservice.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.meesho.notificationservice.constants.Constants.DATE_PATTERN;

public class Validator {
    public static LocalDateTime localDateTimeValidator(String dateTime,String paramName) {
        LocalDateTime dateTimeObj;
        try {
            dateTimeObj = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_PATTERN));
        }catch (DateTimeParseException e) {
            throw new IllegalArgumentException(paramName +" has invalid date-time format, please follow the format: "+DATE_PATTERN);
        }
        return dateTimeObj;
    }
}
