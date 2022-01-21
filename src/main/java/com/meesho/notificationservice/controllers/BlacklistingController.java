package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.BlacklistedNumber;
import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.services.BlacklistingService;
import com.meesho.notificationservice.services.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.meesho.notificationservice.constants.Constants.MESSAGE_SEND_INIT;

@RestController
@RequestMapping(path = "v1/blacklist")
public class BlacklistingController {

    private final BlacklistingService blacklistingService;

    @Autowired
    public BlacklistingController(BlacklistingService blacklistingService) {
        this.blacklistingService = blacklistingService;
    }

    @PostMapping(path = "/add/{phoneNumber}")
    @CachePut(value = "messagesL1")
    public String addPhoneNumberToBlacklist(@PathVariable String phoneNumber) {
        blacklistingService.addPhoneNumberToBlacklist(phoneNumber);
        return phoneNumber;
    }

    @GetMapping(path = "/all")
    public List<BlacklistedNumber> getAllBlacklistedNumbers() {
        return blacklistingService.getAllBlacklistedNumbers();
    }

    @DeleteMapping(path = "/delete/{phoneNumber}")
    @CacheEvict(value = "messagesL1")
    public String deleteByPhoneNumber(@PathVariable String phoneNumber) {
        blacklistingService.deleteByPhoneNumber(phoneNumber);
        return phoneNumber;
    }

}