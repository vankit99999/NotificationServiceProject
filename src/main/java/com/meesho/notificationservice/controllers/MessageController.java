package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.models.SuccessResponse;
import com.meesho.notificationservice.services.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "v1/sms")
@Validated
public class MessageController {
    @Autowired
    private MessageSenderService messageSenderService;

    @PostMapping(path = "/send")
    public ResponseEntity<SuccessResponse> sendNewMessage(@Valid @RequestBody Message message) {
        messageSenderService.sendNewMessage(message);
        return new ResponseEntity<>(new SuccessResponse(message,"success"), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<SuccessResponse> getAllMessages() {
        List<Message> messageList=messageSenderService.getAllMessages();
        if (messageList.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(new SuccessResponse(messageList,"success"), HttpStatus.OK);
    }

    @GetMapping(path = "/{messageId}")
    public ResponseEntity<SuccessResponse> getMessageById(@PathVariable("messageId") @Min(1) Long messageId) {
        Message message = messageSenderService.getMessageById(messageId)
                .orElseThrow(() -> new NoSuchElementException("No message with id: "+messageId));
        return new ResponseEntity<>(new SuccessResponse(message,"success"), HttpStatus.OK);
    }
}
