package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repository.MessageRepository;
import com.meesho.notificationservice.services.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageSenderService {

    private final MessageRepository messageRepository;
    private final Producer producer;

    @Autowired
    public MessageSenderService(MessageRepository messageRepository, Producer producer) {
        this.messageRepository = messageRepository;
        this.producer = producer;
    }

    public void sendNewMessage(Message message) {
        messageRepository.save(message);
        producer.sendMessage(message.getId());
    }

//    @Transactional
//    public void updateDataBase(Message message) {
//        boolean messageExists = messageRepository.existsById(message.getId());
//        if(messageExists) {
//            Message optionalMessage = messageRepository.findById(message.getId()).orElseThrow(()->new IllegalStateException("message with id "+message.getId()+" does not exist"));
//            optionalMessage.setText(message.getText());
//            optionalMessage.setLastUpdatedAt(message.getLastUpdatedAt());
//            optionalMessage.setPhoneNumber(message.getPhoneNumber());
//            optionalMessage.setStatus(message.getStatus());
//        }
//        else {
//            messageRepository.save(message);
//        }
//    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (!messageOptional.isPresent()) {
            throw new IllegalStateException("no message with this message id!!!");
        }
        return messageOptional.get();
    }
}
