package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.SearchEntity;
import com.meesho.notificationservice.services.SearchService;
import com.meesho.notificationservice.utils.Validator;
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
        int pageInt = Validator.intValidator(page,0,"page-number");
        int sizeInt = Validator.intValidator(size,1,"page-size");
        Validator.phoneNumberValidator(phoneNumber,"phone-number");
        LocalDateTime startTimeObj = Validator.localDateTimeValidator(startTime,"start-time");
        LocalDateTime endTimeObj = Validator.localDateTimeValidator(endTime,"end-time");
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

        int pageInt = Validator.intValidator(page,0,"page-number");
        int sizeInt = Validator.intValidator(size,1,"page-size");
        LocalDateTime startTimeObj = Validator.localDateTimeValidator(startTime,"start-time");
        LocalDateTime endTimeObj = Validator.localDateTimeValidator(endTime,"end-time");
        if(endTimeObj.isBefore(startTimeObj))
            throw new IllegalArgumentException("start date must be before end time");
        List<SearchEntity> searchEntities = searchService.findByTextAndTime(text,startTimeObj,endTimeObj,
                pageInt,sizeInt);
        if (searchEntities.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(searchEntities, HttpStatus.OK);
    }
}
