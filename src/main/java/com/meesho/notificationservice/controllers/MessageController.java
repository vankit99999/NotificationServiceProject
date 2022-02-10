package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.SMS.Message;
import com.meesho.notificationservice.models.ControllerSuccessResponse;
import com.meesho.notificationservice.models.SMS.SendMessageRequest;
import com.meesho.notificationservice.services.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "v1/sms")
@Validated
public class MessageController {
    @Autowired
    private MessageSenderService messageSenderService;

    @PostMapping(path = "/send")
    public ResponseEntity<ControllerSuccessResponse> sendNewMessage(
            @Valid @RequestBody SendMessageRequest sendMessageRequest) {
        Message message = messageSenderService.sendNewMessage(sendMessageRequest);
        return new ResponseEntity<>(new ControllerSuccessResponse(
                message,"successfully initialised message sending"), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ControllerSuccessResponse> getAllMessages() {
        List<Message> messageList=messageSenderService.getAllMessages();
        if (messageList.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(new ControllerSuccessResponse(
                messageList,"successfully fetched all messages sent"), HttpStatus.OK);
    }

    @GetMapping(path = "/{messageId}")
    public ResponseEntity<ControllerSuccessResponse> getMessageById(@PathVariable("messageId") Long messageId) {
        Message message = messageSenderService.getMessageById(messageId)
                .orElseThrow(() -> new NoSuchElementException("No message with id: "+messageId));
        return new ResponseEntity<>(new ControllerSuccessResponse(
                message,"successfully fetched message with id "+messageId), HttpStatus.OK);
    }
}
