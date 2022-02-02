package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repositories.JPArepositories.MessageRepository;
import com.meesho.notificationservice.services.pipelineservice.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.meesho.notificationservice.constants.Constants.LOGGER_NAME;

@Service
public class MessageSenderService {
    private final MessageRepository messageRepository;
    private final ProducerService producerService;
    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    @Autowired
    public MessageSenderService(MessageRepository messageRepository, ProducerService producerService) {
        this.messageRepository = messageRepository;
        this.producerService = producerService;
    }

    public void sendNewMessage(Message message) {
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
