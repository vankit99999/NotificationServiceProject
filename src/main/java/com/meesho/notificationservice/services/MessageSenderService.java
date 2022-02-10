package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.SMS.Message;
import com.meesho.notificationservice.models.SMS.SendMessageRequest;
import com.meesho.notificationservice.repositories.MessageRepository;
import com.meesho.notificationservice.services.Kafka.MessageSendRequestProducerService;
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
    private MessageSendRequestProducerService messageSendRequestProducerService;

    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    public Message sendNewMessage(SendMessageRequest sendMessageRequest) {
        LocalDateTime currentTime = LocalDateTime.now();
        Message message = new Message();
        message.setPhoneNumber(sendMessageRequest.getPhoneNumber());
        message.setText(sendMessageRequest.getText());
        message.setStatus(MESSAGE_STATUS.MESSAGE_SENDING_INITIALISED.toString());
        message.setCreatedOn(currentTime);
        message.setLastUpdatedAt(currentTime);
        messageRepository.save(message);
        logger.info(String.format("message-> %s sending initiated and saved to database", String.valueOf(message)));
        messageSendRequestProducerService.sendMessage(message.getId());
        return message;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Long messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        return messageOptional;
    }
}
