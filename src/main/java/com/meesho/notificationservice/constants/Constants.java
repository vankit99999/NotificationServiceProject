package com.meesho.notificationservice.constants;

public class Constants {
    public static final String MESSAGE_SEND_INIT="MESSAGE_SENDING_INITIALISED";
    public static final String MESSAGE_SEND_SUCCESS="MESSAGE_SENT_SUCCESSFULLY_VIA_IMI_CONNECT_AND_INDEXED_TO_ES";
    public static final String MESSAGE_SEND_FAILED="MESSAGE_SENDING_FAILED_PHONE_NUMBER_BLACKLISTED";
    public static final String DATE_PATTERN="uuuu-MM-dd'T'HH:mm:ss.SSSSSS";
    public static final String CACHE_NAME="messagesL1";
    public static final String INDEX_NAME="messagesindex5";
    public static final String KAFKA_TOPIC_NAME="message_pipeline";
}
