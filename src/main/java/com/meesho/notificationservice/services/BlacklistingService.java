package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.BlacklistedNumber;
import com.meesho.notificationservice.repositories.JPArepositories.BlacklistedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlacklistingService {
    private final BlacklistedRepository blacklistedRepository;

    @Autowired
    public BlacklistingService(BlacklistedRepository blacklistedRepository) {
        this.blacklistedRepository = blacklistedRepository;
    }

    @Cacheable("messagesL1")
    public Optional<String> isNumberPresentInBlackList(String phoneNumber) {
        Optional<String> phoneNumberOptional = blacklistedRepository.findPhoneNumberAndReturnAsString(phoneNumber);
        return phoneNumberOptional;
    }

    public void addPhoneNumberToBlacklist(String phoneNumber) {
        Optional<BlacklistedNumber> phoneNumberOptional = blacklistedRepository.findPhoneNumber(phoneNumber);
        if (phoneNumberOptional.isPresent()) {
            throw new IllegalStateException("phone number already blacklisted");
        }
        BlacklistedNumber blacklistedNumber = new BlacklistedNumber();
        blacklistedNumber.setPhoneNumber(phoneNumber);
        blacklistedRepository.save(blacklistedNumber);
    }

    public List<BlacklistedNumber> getAllBlacklistedNumbers() {
        return blacklistedRepository.findAll();
    }

    public void deleteByPhoneNumber(String phoneNumber) {
        Optional<BlacklistedNumber> phoneNumberOptional = blacklistedRepository.findPhoneNumber(phoneNumber);
        if(!phoneNumberOptional.isPresent()) {
            throw new IllegalStateException("phone number: "+ phoneNumber +" not present in blacklist");
        }
        blacklistedRepository.deleteById(phoneNumberOptional.get().getId());
    }
}