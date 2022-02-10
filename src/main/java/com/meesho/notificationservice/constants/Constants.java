package com.meesho.notificationservice.constants;

public class Constants {

    public enum MESSAGE_STATUS {
        MESSAGE_SENDING_INITIALISED,
        MESSAGE_SENDING_FAILED_PHONE_NUMBER_BLACKLISTED,
        MESSAGE_SENDING_FAILED_REST_EXCEPTION,
        MESSAGE_SENT_SUCCESSFULLY
    }
    public static final String DATE_PATTERN="uuuu-MM-dd'T'HH:mm:ss.SSSSSS";
    public static final String CACHE_NAME="messagesL1";
    public static final String CACHE_KEY="#phoneNumber";
    public static final String INDEX_NAME="messagesindex5";
    public static final String KAFKA_TOPIC_NAME="KAFKA_MESSAGE_PIPELINE";
    public static final String LOGGER_NAME="TERMINAL_LOGGER";
}
