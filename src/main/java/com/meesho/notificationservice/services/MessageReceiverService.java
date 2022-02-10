package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.IMIConnect.IMIResponse;
import com.meesho.notificationservice.models.SMS.Message;
import com.meesho.notificationservice.repositories.MessageRepository;
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
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private BlacklistingNumberService blacklistingNumberService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private IMIConnectService IMIConnectService;

    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    public void initiateServicesOnConsumerEnd(Long messageId) {
        performConsumerEndServices(messageId);
    }

    private void performConsumerEndServices(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(()->new IllegalStateException("message with id "+ messageId +" does not exist"));
        logger.info(String.format("retrieved message on consumer side -> %s", String.valueOf(message)));
        Optional<Long> checkBlacklist = blacklistingNumberService.isNumberPresentInBlackList(message.getPhoneNumber());
        if(!checkBlacklist.isPresent()) {
            logger.info("number not present in blacklist,initialising 3rd party API");
            try {
                ResponseEntity<IMIResponse> restResponseResponseEntity = IMIConnectService.sendRequest(message);
                message.setLastUpdatedAt(LocalDateTime.now());
                message.setStatus(MESSAGE_STATUS.MESSAGE_SENT_SUCCESSFULLY.toString());
                messageRepository.save(message);
                if(restResponseResponseEntity.getBody().getResponse().get(0).getCode()
                        .equals("1001")) {
                    searchService.createSearchIndex(message);
                }
            }catch (Exception e) {
                message.setLastUpdatedAt(LocalDateTime.now());
                message.setStatus(MESSAGE_STATUS.MESSAGE_SENDING_FAILED_REST_EXCEPTION.toString());
                messageRepository.save(message);
            }
        }
        else {
            logger.info("Cannot send message,number present in blacklist");
            message.setStatus(MESSAGE_STATUS.MESSAGE_SENDING_FAILED_PHONE_NUMBER_BLACKLISTED.toString());
            message.setLastUpdatedAt(LocalDateTime.now());
            messageRepository.save(message);
        }
    }
}
