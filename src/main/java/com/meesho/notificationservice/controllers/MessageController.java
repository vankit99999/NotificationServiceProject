package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.services.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public String sendNewMessage(@RequestBody Message message) {
        message.setStatus(MESSAGE_SEND_INIT);
        LocalDateTime currentTime = LocalDateTime.now();
        message.setCreatedOn(currentTime);
        message.setLastUpdatedAt(currentTime);
        messageSenderService.sendNewMessage(message);
        return "Message Send Initiated";
    }

    @GetMapping(path = "/all")
    public List<Message> getAllMessages() {
        return messageSenderService.getAllMessages();
    }

    @GetMapping(path = "/{messageId}")
    public Message getMessageById(@PathVariable("messageId") Long messageId) {
        return messageSenderService.getMessageById(messageId);
    }
}
