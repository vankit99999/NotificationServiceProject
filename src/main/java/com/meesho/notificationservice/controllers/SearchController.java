package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.SearchEntity;
import com.meesho.notificationservice.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.meesho.notificationservice.constants.Constants.DATE_PATTERN;

@RestController
@RequestMapping(path = "v1/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(path = "/phoneNumber={phoneNumber}/startTime={startTime}/endTime={endTime}/page={page}/size={size}")
    public ResponseEntity<List<SearchEntity>> searchByPhoneNumberAndTime(@PathVariable("phoneNumber") String phoneNumber,
                                                  @PathVariable("startTime") String startTime,
                                                  @PathVariable("endTime") String endTime,
                                                  @PathVariable("page") String page,
                                                  @PathVariable("size") String size) {
        Long phoneNumberLong;
        int pageInt,sizeInt;
        try {
            phoneNumberLong = Long.parseLong(phoneNumber);
            pageInt = Integer.parseInt(page);
            sizeInt = Integer.parseInt(size);
        }catch (NumberFormatException n) {
            throw new IllegalArgumentException("invalid phone number,page number or page size!!!");
        }
        if(phoneNumber.length()!=10 || pageInt<0 || sizeInt<0)
        {
            throw new IllegalArgumentException("invalid phone number,page number or page size!!!");
        }
        LocalDateTime startTimeObj,endTimeObj;
        try {
            startTimeObj = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern(DATE_PATTERN));
            endTimeObj = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(DATE_PATTERN));
        }catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid time format, please follow the format: "+DATE_PATTERN);
        }
        if(endTimeObj.isBefore(startTimeObj))
            throw new IllegalArgumentException("start date must be before end time");
        List<SearchEntity> searchEntities = searchService.findByPhoneNumberAndTime(phoneNumber,startTimeObj,endTimeObj,
                pageInt,sizeInt);
        if (searchEntities.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(searchEntities, HttpStatus.OK);
    }

    @GetMapping(path = "/text={text}/startTime={startTime}/endTime={endTime}/page={page}/size={size}")
    public ResponseEntity<List<SearchEntity>> searchByTextAndTime(@PathVariable("text") String text,
                                                     @PathVariable("startTime") String startTime,
                                                     @PathVariable("endTime") String endTime,
                                                    @PathVariable("page") String page,
                                                    @PathVariable("size") String size) {
        int pageInt,sizeInt;
        try {
            pageInt = Integer.parseInt(page);
            sizeInt = Integer.parseInt(size);
        }catch (NumberFormatException n) {
            throw new IllegalArgumentException("invalid page number or page size!!!");
        }
        if(pageInt<0 || sizeInt<0)
        {
            throw new IllegalArgumentException("invalid page number or page size!!!");
        }
        LocalDateTime startTimeObj,endTimeObj;
        try {
            startTimeObj = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern(DATE_PATTERN));
            endTimeObj = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(DATE_PATTERN));
        }catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid time format, please follow the format: "+DATE_PATTERN);
        }
        if(endTimeObj.isBefore(startTimeObj))
            throw new IllegalArgumentException("start date must be before end time");
        List<SearchEntity> searchEntities = searchService.findByTextAndTime(text,startTimeObj,endTimeObj,
                pageInt,sizeInt);
        if (searchEntities.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(searchEntities, HttpStatus.OK);
    }
}
