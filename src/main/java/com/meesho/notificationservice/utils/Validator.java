package com.meesho.notificationservice.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.meesho.notificationservice.constants.Constants.DATE_PATTERN;

public class Validator {
    public static Long longValidator(String value,Long minValue,String paramName) {
        Long valueLong;
        try {
            valueLong = Long.parseLong(value);
        }catch (Exception n) {
            throw new IllegalArgumentException(paramName+" must be integer");
        }
        if(valueLong<minValue)
        {
            throw new IllegalArgumentException(paramName+" must be greater than "+(minValue-1));
        }
        return valueLong;
    }

    public static int intValidator(String value,int minValue,String paramName) {
        int valueLong;
        try {
            valueLong = Integer.parseInt(value);
        }catch (Exception n) {
            throw new IllegalArgumentException(paramName+" must be integer");
        }
        if(valueLong<minValue)
        {
            throw new IllegalArgumentException(paramName+" must be greater than "+(minValue-1));
        }
        return valueLong;
    }

    public static void phoneNumberValidator(String value,String paramName) {
        Long valueLong;
        try {
            valueLong = Long.parseLong(value);
        }catch (Exception n) {
            throw new IllegalArgumentException(paramName+" must contain only integers");
        }
        if(value.length()!=10)
        {
            throw new IllegalArgumentException(paramName+" must be of 10 digits");
        }
    }

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
