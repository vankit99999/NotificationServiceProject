package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.BlacklistedNumber;
import com.meesho.notificationservice.repositories.JPArepositories.BlacklistedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.meesho.notificationservice.constants.Constants.CACHE_NAME;
import static com.meesho.notificationservice.constants.Constants.LOGGER_NAME;

@Service
public class BlacklistingService {
    private final BlacklistedRepository blacklistedRepository;
    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    @Autowired
    public BlacklistingService(BlacklistedRepository blacklistedRepository) {
        this.blacklistedRepository = blacklistedRepository;
    }

    @Cacheable(value = CACHE_NAME,key = "#phoneNumber",unless="#result==null")
    public Optional<Long> isNumberPresentInBlackList(String phoneNumber) {
        Optional<Long> phoneNumberID = blacklistedRepository.findPhoneNumberAndReturnID(phoneNumber);
        return phoneNumberID;
    }

    @CachePut(value = CACHE_NAME,key = "#phoneNumber")
    public Long addPhoneNumberToBlacklist(String phoneNumber) {
        BlacklistedNumber blacklistedNumber = new BlacklistedNumber();
        blacklistedNumber.setPhoneNumber(phoneNumber);
        blacklistedRepository.save(blacklistedNumber);
        logger.info(String.format("blacklisted -> %s", phoneNumber));
        return blacklistedNumber.getId();
    }

    public List<BlacklistedNumber> getAllBlacklistedNumbers() {
        return blacklistedRepository.findAll();
    }

    @CacheEvict(value = CACHE_NAME, key = "#phoneNumber")
    public Long deleteByPhoneNumber(String phoneNumber,Long id) {
        blacklistedRepository.deleteById(id);
        return id;
    }
}