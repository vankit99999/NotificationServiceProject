package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repositories.JPArepositories.MessageRepository;
import com.meesho.notificationservice.services.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageSenderService {
    private final MessageRepository messageRepository;
    private final Producer producer;

    @Autowired
    public MessageSenderService(MessageRepository messageRepository, Producer producer) {
        this.messageRepository = messageRepository;
        this.producer = producer;
    }

    public void sendNewMessage(Message message) {
        messageRepository.save(message);
        producer.sendMessage(message.getId());
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Long messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        return messageOptional;
    }
}
