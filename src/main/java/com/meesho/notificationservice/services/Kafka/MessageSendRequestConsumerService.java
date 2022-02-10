package com.meesho.notificationservice.services.Kafka;

import com.meesho.notificationservice.services.MessageReceiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.meesho.notificationservice.constants.Constants.KAFKA_TOPIC_NAME;
import static com.meesho.notificationservice.constants.Constants.LOGGER_NAME;

@Service
public class MessageSendRequestConsumerService {
    @Autowired
    private MessageReceiverService messageReceiverService;

    private final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    @KafkaListener(topics = KAFKA_TOPIC_NAME, groupId = "group_id")
    public void consume(Long messageId) throws IOException {
        logger.info(String.format("Received message id on consumer side from pipeline-> %s", String.valueOf(messageId)));
        messageReceiverService.initiateServicesOnConsumerEnd(messageId);
    }
}
