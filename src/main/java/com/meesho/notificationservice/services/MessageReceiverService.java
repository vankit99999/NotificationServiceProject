package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiverService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageReceiverService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    public void queryDatabaseById(Long messageId) {
        boolean exists = messageRepository.existsById(messageId);
        if(!exists) {
            throw new IllegalStateException("message with id "+ messageId +" does not exist");
        }
        Message message = messageRepository.findById(messageId).orElseThrow(()->new IllegalStateException("message with id "+ messageId +" does not exist"));
        System.out.println(message);
    }

}
