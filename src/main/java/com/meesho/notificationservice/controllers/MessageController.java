package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.services.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.meesho.notificationservice.constants.Constants.MESSAGE_SEND_INIT;

@RestController
@RequestMapping(path = "v1/sms")
public class MessageController {
    private final MessageSenderService messageSenderService;

    @Autowired
    public MessageController(MessageSenderService messageSenderService) {
        this.messageSenderService = messageSenderService;
    }

    @PostMapping(path = "/send")
    public ResponseEntity<Message> sendNewMessage(@Valid @RequestBody Message message) {
        message.setStatus(MESSAGE_SEND_INIT);
        LocalDateTime currentTime = LocalDateTime.now();
        message.setCreatedOn(currentTime);
        message.setLastUpdatedAt(currentTime);
        messageSenderService.sendNewMessage(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messageList=messageSenderService.getAllMessages();
        if (messageList.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @GetMapping(path = "/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable("messageId") String messageId) {
        Long messageIdLong;
        try {
            messageIdLong = Long.parseLong(messageId);
        }catch (Exception n) {
            throw new IllegalArgumentException("Only integer id allowed");
        }
        if(messageIdLong<1)
        {
            throw new IllegalArgumentException("message id must be greater than 0");
        }
        Message message = messageSenderService.getMessageById(messageIdLong)
                .orElseThrow(() -> new NoSuchElementException("No message with id: "+messageIdLong ));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
