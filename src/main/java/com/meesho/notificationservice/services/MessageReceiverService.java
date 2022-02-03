package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.RESTEntities.RESTResponse;
import com.meesho.notificationservice.models.SearchEntity;
import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repositories.JPArepositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    @Autowired
    public MessageReceiverService(MessageRepository messageRepository, BlacklistingService blacklistingService,
                                  SearchService searchService, RESTConsumerService restConsumerService) {
        this.messageRepository = messageRepository;
        this.blacklistingService = blacklistingService;
        this.searchService = searchService;
        this.restConsumerService = restConsumerService;
    }
    private RESTResponse connectTo3rdPartyAPI(Message message) {
        try {
            ResponseEntity<RESTResponse> restResponseResponseEntity = restConsumerService.sendRequest(
                    message.getPhoneNumber(),
                    message.getText());
            message.setLastUpdatedAt(LocalDateTime.now());
            message.setStatus(restResponseResponseEntity.getBody().getResponse().get(0).getDescription());
            messageRepository.save(message);
            return restResponseResponseEntity.getBody();
        }catch (Exception e) {
            message.setLastUpdatedAt(LocalDateTime.now());
            message.setStatus(MESSAGE_SEND_FAILED_REST_EXCEPTION);
            messageRepository.save(message);
            return null;
        }
    }

    private void addIndexToIndexTable(Message message) {
        SearchEntity searchEntity = new SearchEntity(message.getText(),message.getPhoneNumber(),
                message.getStatus(),message.getCreatedOn(),message.getLastUpdatedAt());
        searchService.createSearchIndex(searchEntity);
    }

    public void performConsumerEndServices(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(()->new IllegalStateException("message with id "+ messageId +" does not exist"));
        logger.info(String.format("retrieved message on consumer side -> %s", String.valueOf(message)));
        Optional<Long> checkBlacklist = blacklistingService.isNumberPresentInBlackList(message.getPhoneNumber());
        if(!checkBlacklist.isPresent()) {
            logger.info("number not present in blacklist,initialising 3rd party API");
            RESTResponse restResponse = connectTo3rdPartyAPI(message);
            if(restResponse != null && restResponse.getResponse().get(0).getCode().equals("1001")) {
                addIndexToIndexTable(message);
            }
        }
        else {
            logger.info("Cannot send message,number present in blacklist");
            message.setStatus(MESSAGE_SEND_FAILED_BLACKLISTED_NUMBER);
            message.setLastUpdatedAt(LocalDateTime.now());
            messageRepository.save(message);
        }
    }
}
