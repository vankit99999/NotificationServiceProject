package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.SearchEntity;
import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repositories.JPArepositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageReceiverService {
    private final MessageRepository messageRepository;
    private final BlacklistingService blacklistingService;
    private final SearchService searchService;

    @Autowired
    public MessageReceiverService(MessageRepository messageRepository, BlacklistingService blacklistingService, SearchService searchService) {
        this.messageRepository = messageRepository;
        this.blacklistingService = blacklistingService;
        this.searchService = searchService;
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
            SearchEntity searchEntity = new SearchEntity();
            searchEntity.setText(message.getText());
            searchEntity.setPhoneNumber(message.getPhoneNumber());
            searchEntity.setCreatedAt(message.getCreatedOn());
            searchEntity.setStatus(message.getStatus());
            searchEntity.setLastUpdatedAt(message.getLastUpdatedAt());
            searchService.createSearchIndex(searchEntity);
        }
        else {
            System.out.println("Cannot send message,number present in blacklist");
        }
    }
}
