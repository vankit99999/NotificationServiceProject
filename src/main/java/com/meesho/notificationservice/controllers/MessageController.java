package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/sms")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(path = "/send")
    public void registerNewMessage(@RequestBody Message message) {
        messageService.registerNewMessage(message);
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
