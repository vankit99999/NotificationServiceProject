package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.BlacklistedNumber;
import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.repository.BlacklistedRepository;
import com.meesho.notificationservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class BlacklistingService {

    private final BlacklistedRepository blacklistedRepository;

    @Autowired
    public BlacklistingService(BlacklistedRepository blacklistedRepository) {
        this.blacklistedRepository = blacklistedRepository;
    }

    public void addPhoneNumberToBlacklist(BlacklistedNumber blacklistedNumber) {
        Optional<BlacklistedNumber> phoneNumberOptional = blacklistedRepository.findPhoneNumber(blacklistedNumber.getPhoneNumber());
        if (phoneNumberOptional.isPresent()) {
            throw new IllegalStateException("phone number already blacklisted");
        }
        blacklistedRepository.save(blacklistedNumber);
    }
    public List<BlacklistedNumber> getAllBlacklistedNumbers() {
        return blacklistedRepository.findAll();
    }

    public void deletePhoneNumberFromList(@RequestBody BlacklistedNumber blacklistedNumber) {
        Optional<BlacklistedNumber> phoneNumberOptional = blacklistedRepository.findPhoneNumber(blacklistedNumber.getPhoneNumber());
        if(!phoneNumberOptional.isPresent()) {
            throw new IllegalStateException("phone number: "+ blacklistedNumber.getPhoneNumber() +" not present in blacklist");
        }
        blacklistedRepository.deleteById(phoneNumberOptional.get().getId());
//        blacklistedRepository.deletePhoneNumberByNumber(blacklistedNumber.getPhoneNumber());
    }
}