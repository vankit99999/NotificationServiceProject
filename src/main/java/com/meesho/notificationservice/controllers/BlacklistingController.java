package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.BlacklistedNumber;
import com.meesho.notificationservice.services.BlacklistingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "v1/blacklist")
@Validated
public class BlacklistingController {
    private final BlacklistingService blacklistingService;

    @Autowired
    public BlacklistingController(BlacklistingService blacklistingService) {
        this.blacklistingService = blacklistingService;
    }

    @PostMapping(path = "/add/{phoneNumber}")
    public ResponseEntity<String> addPhoneNumberToBlacklist(
        @PathVariable
        @Pattern(regexp="[6-9][0-9]{9}",
        message = "phone number must be of 10 digits,1st digit must be in range-[6,9]") String phoneNumber) {
        Optional<Long> checkBlackList = blacklistingService.isNumberPresentInBlackList(phoneNumber);
        if(checkBlackList.isPresent())
            throw new IllegalArgumentException("phone number already blacklisted");
        blacklistingService.addPhoneNumberToBlacklist(phoneNumber);
        return new ResponseEntity<>(phoneNumber+" added to blacklist", HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<String>> getAllBlacklistedNumbers() {
        List<BlacklistedNumber> blacklistedNumbersList=blacklistingService.getAllBlacklistedNumbers();
        if (blacklistedNumbersList.isEmpty())
            throw new NoSuchElementException("No numbers blacklisted");
        List<String> phoneNumbers = new ArrayList<>();
        for(BlacklistedNumber blacklistedNumber:blacklistedNumbersList) {
            phoneNumbers.add(blacklistedNumber.getPhoneNumber());
        }
        return new ResponseEntity<>(phoneNumbers, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{phoneNumber}")
    public ResponseEntity<String> deleteByPhoneNumber(
            @PathVariable
            @Pattern(regexp="[6-9][0-9]{9}",
                    message = "phone number must be of 10 digits,1st digit must be in range-[6,9]") String phoneNumber) {
        Optional<Long> checkBlackList = blacklistingService.isNumberPresentInBlackList(phoneNumber);
        if(!checkBlackList.isPresent())
            throw new IllegalArgumentException("phone number not present in blacklist");
        blacklistingService.deleteByPhoneNumber(phoneNumber,checkBlackList.get());
        return new ResponseEntity<>(phoneNumber+" deleted from blacklist", HttpStatus.OK);
    }
}