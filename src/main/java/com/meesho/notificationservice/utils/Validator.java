package com.meesho.notificationservice.utils;

public class Validator {
    public static boolean isPhoneNumberValid(String phoneNumber) {
        Long phoneNumberLong;
        if (phoneNumber.length()!=10)
        {
            return false;
        }
        if (phoneNumber.charAt(0)-'0'<6) {
            return false;
        }
        try {
            phoneNumberLong = Long.parseLong(phoneNumber);
        }catch (NumberFormatException n) {
            return false;
        }
        return true;
    }
}
