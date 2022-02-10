package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.Blacklist.BlacklistRequest;
import com.meesho.notificationservice.models.Blacklist.BlacklistResponse;
import com.meesho.notificationservice.models.Blacklist.BlacklistedNumber;
import com.meesho.notificationservice.models.ControllerSuccessResponse;
import com.meesho.notificationservice.services.BlacklistingNumberService;
import com.meesho.notificationservice.services.BlacklistingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "v1/blacklist")
@Validated
public class BlacklistingController {
    @Autowired
    private BlacklistingNumberService blacklistingNumberService;

    @Autowired
    private BlacklistingService blacklistingService;

    @PostMapping(path = "/add")
    public ResponseEntity<ControllerSuccessResponse> addPhoneNumbersToBlacklist(
            @RequestBody BlacklistRequest blacklistRequest) {
        if(blacklistRequest.getPhoneNumbers().isEmpty())
            throw new IllegalArgumentException("No numbers to blacklist");
        BlacklistResponse blacklistResponse = blacklistingService.addPhoneNumbersToBlacklist(blacklistRequest);
        return new ResponseEntity<>(new ControllerSuccessResponse(
                blacklistResponse,"successfully added valid phone numbers to blacklist"), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ControllerSuccessResponse> getAllBlacklistedNumbers() {
        List<BlacklistedNumber> blacklistedNumbersList= blacklistingNumberService.getAllBlacklistedNumbers();
        if (blacklistedNumbersList.isEmpty())
            throw new NoSuchElementException("No numbers blacklisted");
        List<String> phoneNumbers = new ArrayList<>();
        for(BlacklistedNumber blacklistedNumber:blacklistedNumbersList) {
            phoneNumbers.add(blacklistedNumber.getPhoneNumber());
        }
        return new ResponseEntity<>(new ControllerSuccessResponse(
                phoneNumbers,"successfully fetched list of blacklisted numbers"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<ControllerSuccessResponse> deletePhoneNumbersFromBlacklist(
            @RequestBody BlacklistRequest blacklistRequest) {
        if(blacklistRequest.getPhoneNumbers().isEmpty())
            throw new IllegalArgumentException("No numbers to delete");
        BlacklistResponse blacklistResponse = blacklistingService.deletePhoneNumbersFromBlacklist(blacklistRequest);
        return new ResponseEntity<>(new ControllerSuccessResponse(
                blacklistResponse,"successfully deleted valid phone numbers from blacklist"), HttpStatus.OK);
    }
}