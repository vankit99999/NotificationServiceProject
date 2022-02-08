package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repositories.MessageRepository;
import com.meesho.notificationservice.services.pipelineservice.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.meesho.notificationservice.constants.Constants.*;

@Service
public class MessageSenderService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ProducerService producerService;
    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    public void sendNewMessage(Message message) {
        message.setStatus(MESSAGE_SEND_INITIALISED);
        LocalDateTime currentTime = LocalDateTime.now();
        message.setCreatedOn(currentTime);
        message.setLastUpdatedAt(currentTime);
        messageRepository.save(message);
        logger.info(String.format("message-> %s sending initiated and saved to database", String.valueOf(message)));
        producerService.sendMessage(message.getId());
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Long messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        return messageOptional;
    }
}
