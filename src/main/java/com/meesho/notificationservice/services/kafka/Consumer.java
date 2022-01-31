package com.meesho.notificationservice.services.kafka;

import com.meesho.notificationservice.services.MessageReceiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.meesho.notificationservice.constants.Constants.KAFKA_TOPIC_NAME;

@Service
public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(Producer.class);
    private final MessageReceiverService messageReceiverService;

    @Autowired
    public Consumer(MessageReceiverService messageReceiverService) {
        this.messageReceiverService = messageReceiverService;
    }

    @KafkaListener(topics = KAFKA_TOPIC_NAME, groupId = "group_id")
    public void consume(Long messageId) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", String.valueOf(messageId)));
        messageReceiverService.performConsumerEndServices(messageId);
    }
}
