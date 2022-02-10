package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.Blacklist.BlacklistedNumber;
import com.meesho.notificationservice.repositories.BlacklistedNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.meesho.notificationservice.constants.Constants.*;

@Service
public class BlacklistingNumberService {
    @Autowired
    private BlacklistedNumberRepository blacklistedRepository;

    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    @Cacheable(value = CACHE_NAME,key = CACHE_KEY)
    public Optional<Long> isNumberPresentInBlackList(String phoneNumber) {
        Optional<Long> phoneNumberID = blacklistedRepository.findPhoneNumberAndReturnID(phoneNumber);
        return phoneNumberID;
    }

    @CachePut(value = CACHE_NAME,key = CACHE_KEY)
    public Long addPhoneNumberToBlacklist(String phoneNumber) {
        Optional<Long> phoneNumberID = blacklistedRepository.findPhoneNumberAndReturnID(phoneNumber);
        if (phoneNumberID.isPresent()) {
            logger.info(String.format("already blacklisted -> %s", phoneNumber));
            return phoneNumberID.get();
        }
        BlacklistedNumber blacklistedNumber = new BlacklistedNumber();
        blacklistedNumber.setPhoneNumber(phoneNumber);
        blacklistedRepository.save(blacklistedNumber);
        logger.info(String.format("blacklisted -> %s", phoneNumber));
        return blacklistedNumber.getId();
    }

    @CacheEvict(value = CACHE_NAME, key = CACHE_KEY)
    public Optional<Long> deletePhoneNumberFromBlacklist(String phoneNumber) {
        Optional<Long> phoneNumberID = blacklistedRepository.findPhoneNumberAndReturnID(phoneNumber);
        if (phoneNumberID.isPresent()) {
            blacklistedRepository.deleteById(phoneNumberID.get());
            logger.info(String.format("removed from blacklist -> %s", phoneNumber));
        }
        else {
            logger.info(String.format("cannot remove, not present in blacklist -> %s", phoneNumber));
        }
        return phoneNumberID;
    }

    public List<BlacklistedNumber> getAllBlacklistedNumbers() {
        return blacklistedRepository.findAll();
    }
}