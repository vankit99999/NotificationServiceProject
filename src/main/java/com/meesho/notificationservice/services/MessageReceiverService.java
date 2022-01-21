package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repository.BlacklistedRepository;
import com.meesho.notificationservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageReceiverService {

    private final MessageRepository messageRepository;
    private final BlacklistingService blacklistingService;

    @Autowired
    public MessageReceiverService(MessageRepository messageRepository, BlacklistingService blacklistingService) {
        this.messageRepository = messageRepository;
        this.blacklistingService = blacklistingService;
    }

    public void queryDatabaseById(Long messageId) {
        boolean exists = messageRepository.existsById(messageId);
        if(!exists) {
            throw new IllegalStateException("message with id "+ messageId +" does not exist");
        }
        Message message = messageRepository.findById(messageId).orElseThrow(()->new IllegalStateException("message with id "+ messageId +" does not exist"));
        System.out.println(message);
        Optional<String> checkBlacklist = blacklistingService.isNumberPresentInBlackList(message.getPhoneNumber());
        if(!checkBlacklist.isPresent()) {
            System.out.println("Number not present in blacklist,init 3rd party API");
        }
        else {
            System.out.println("Cannot send message,number present in blacklist");
        }
    }

}
