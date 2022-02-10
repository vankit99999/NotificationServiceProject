package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.Blacklist.BlacklistRequest;
import com.meesho.notificationservice.models.Blacklist.BlacklistResponse;
import com.meesho.notificationservice.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlacklistingService {
    @Autowired
    private BlacklistingNumberService blacklistingNumberService;

    public BlacklistResponse addPhoneNumbersToBlacklist(BlacklistRequest blacklistRequest) {
        List<String> invalidNumbers = new ArrayList<>();
        List<String> blacklistedNumbers = new ArrayList<>();
        List<String> toBlacklist = new ArrayList<>();
        for(String phoneNumber : blacklistRequest.getPhoneNumbers()) {
            if(Validator.isPhoneNumberValid(phoneNumber)) {
                toBlacklist.add(phoneNumber);
            }
            else {
                invalidNumbers.add(phoneNumber);
            }
        }
        for(String phoneNumber : toBlacklist) {
            blacklistingNumberService.addPhoneNumberToBlacklist(phoneNumber);
            blacklistedNumbers.add(phoneNumber);
        }
        BlacklistResponse blacklistResponse = new BlacklistResponse(invalidNumbers, blacklistedNumbers);
        return blacklistResponse;
    }

    public BlacklistResponse deletePhoneNumbersFromBlacklist(BlacklistRequest blacklistRequest) {
        List<String> invalidNumbers = new ArrayList<>();
        List<String> removedFromBlacklistNumbers = new ArrayList<>();
        List<String> toRemoveFromBlacklist = new ArrayList<>();
        for(String phoneNumber : blacklistRequest.getPhoneNumbers()) {
            if(Validator.isPhoneNumberValid(phoneNumber)) {
                toRemoveFromBlacklist.add(phoneNumber);
            }
            else {
                invalidNumbers.add(phoneNumber);
            }
        }
        for(String phoneNumber : toRemoveFromBlacklist) {
            blacklistingNumberService.deletePhoneNumberFromBlacklist(phoneNumber);
            removedFromBlacklistNumbers.add(phoneNumber);
        }
        BlacklistResponse blacklistResponse = new BlacklistResponse(invalidNumbers, removedFromBlacklistNumbers);
        return blacklistResponse;
    }
}
