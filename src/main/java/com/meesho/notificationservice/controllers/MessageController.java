package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.meesho.notificationservice.constants.Constants.MESSAGE_SEND_INIT;

@RestController
@RequestMapping(path = "v1/sms")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(path = "/send")
    public void sendNewMessage(@RequestBody Message message) {
        message.setStatus(MESSAGE_SEND_INIT);
        LocalDateTime currentTime = LocalDateTime.now();
        message.setCreatedOn(currentTime);
        message.setLastUpdatedAt(currentTime);
        messageService.sendNewMessage(message);
    }

//    @PostMapping(path = "/send")
//    public @ResponseBody String sendMessage(@RequestBody Message message) {
//        messageService.updateDataBase(message);
//        return "done";
//    }

    @GetMapping(path = "/all")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
}
