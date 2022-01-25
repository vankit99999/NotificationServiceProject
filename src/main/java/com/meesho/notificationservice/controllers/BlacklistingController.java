package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.BlacklistedNumber;
import com.meesho.notificationservice.services.BlacklistingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.meesho.notificationservice.constants.Constants.CACHE_NAME;

@RestController
@RequestMapping(path = "v1/blacklist")
public class BlacklistingController {
    private final BlacklistingService blacklistingService;

    @Autowired
    public BlacklistingController(BlacklistingService blacklistingService) {
        this.blacklistingService = blacklistingService;
    }

    @PostMapping(path = "/add/{phoneNumber}")
    @CachePut(value = CACHE_NAME)
    public String addPhoneNumberToBlacklist(@PathVariable String phoneNumber) {
        blacklistingService.addPhoneNumberToBlacklist(phoneNumber);
        return phoneNumber;
    }

    @GetMapping(path = "/all")
    public List<BlacklistedNumber> getAllBlacklistedNumbers() {
        return blacklistingService.getAllBlacklistedNumbers();
    }

    @DeleteMapping(path = "/delete/{phoneNumber}")
    @CacheEvict(value = CACHE_NAME)
    public String deleteByPhoneNumber(@PathVariable String phoneNumber) {
        blacklistingService.deleteByPhoneNumber(phoneNumber);
        return phoneNumber;
    }
}