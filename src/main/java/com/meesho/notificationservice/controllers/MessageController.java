package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.exceptions.ResourceNotFoundException;
import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.services.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public String sendNewMessage(@Valid @RequestBody Message message, Errors errors) {
        if(errors.hasErrors()) {
            System.out.println(errors.getAllErrors());
            return errors.toString();
        }
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

//    @GetMapping(path = "/{messageId}")
//    public ResponseEntity<Message> getMessageById(@PathVariable("messageId") @Min(1) Long messageId) {
//        Message message = messageSenderService.getMessageById(messageId)
//                .orElseThrow(() -> new ResourceNotFoundException("No message with id: "+messageId ));
//        return new ResponseEntity<>(message, HttpStatus.OK);
//    }

    @GetMapping(path = "/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable("messageId") String messageId) {
        Long messageIdLong;
        try {
            messageIdLong = Long.parseLong(messageId);
        }catch (NumberFormatException n) {
            throw new NumberFormatException("Only integer id allowed");
        }
        if(messageIdLong<1)
        {
            throw new IllegalArgumentException("message id must be greater than 0");
        }
        Message message = messageSenderService.getMessageById(messageIdLong)
                .orElseThrow(() -> new ResourceNotFoundException("No message with id: "+messageIdLong ));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
