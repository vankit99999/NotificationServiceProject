package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.RESTEntities.RESTResponse;
import com.meesho.notificationservice.models.SearchEntity;
import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repositories.JPArepositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.meesho.notificationservice.constants.Constants.*;

@Service
public class MessageReceiverService {
    private final MessageRepository messageRepository;
    private final BlacklistingService blacklistingService;
    private final SearchService searchService;
    private final RESTConsumerService restConsumerService;

    @Autowired
    public MessageReceiverService(MessageRepository messageRepository, BlacklistingService blacklistingService, SearchService searchService, RESTConsumerService restConsumerService) {
        this.messageRepository = messageRepository;
        this.blacklistingService = blacklistingService;
        this.searchService = searchService;
        this.restConsumerService = restConsumerService;
    }

    public void queryDatabaseById(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(()->new IllegalStateException("message with id "+ messageId +" does not exist"));
        System.out.println(message);
        Optional<Long> checkBlacklist = blacklistingService.isNumberPresentInBlackList(message.getPhoneNumber());
        if(!checkBlacklist.isPresent()) {
            System.out.println("Number not present in blacklist,init 3rd party API");
            try {
                RESTResponse restResponse = restConsumerService.sendRequest(message.getPhoneNumber(),message.getText());
                message.setLastUpdatedAt(LocalDateTime.now());
                message.setStatus(restResponse.getResponse().get(0).getDescription());
                messageRepository.save(message);
                SearchEntity searchEntity = new SearchEntity(message.getText(),message.getPhoneNumber(),
                        message.getStatus(),message.getCreatedOn(),message.getLastUpdatedAt());
                searchService.createSearchIndex(searchEntity);
            }catch (Exception e) {
                message.setLastUpdatedAt(LocalDateTime.now());
                message.setStatus(MESSAGE_SEND_FAILED_REST_EXCEPTION);
                messageRepository.save(message);
            }
        }
        else {
            System.out.println("Cannot send message,number present in blacklist");
            message.setStatus(MESSAGE_SEND_FAILED_BLACKLISTED_NUMBER);
            message.setLastUpdatedAt(LocalDateTime.now());
            messageRepository.save(message);
        }
    }
}
