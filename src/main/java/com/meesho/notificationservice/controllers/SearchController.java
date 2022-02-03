package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.SearchEntity;
import com.meesho.notificationservice.services.SearchService;
import com.meesho.notificationservice.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "v1/search")
@Validated
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(path = "/phoneNumber={phoneNumber}/startTime={startTime}/endTime={endTime}/page={page}/size={size}")
    public ResponseEntity<List<SearchEntity>> searchByPhoneNumberAndTime(
            @PathVariable
            @Pattern(regexp="[6-9][0-9]{9}",
            message = "phone number must be of 10 digits,1st digit must be in range-[6,9]") String phoneNumber,
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime,
            @PathVariable("page") @Min(0) int page,
            @PathVariable("size") @Min(1) int size) {
        LocalDateTime startTimeObj = Validator.localDateTimeValidator(startTime,"start-time");
        LocalDateTime endTimeObj = Validator.localDateTimeValidator(endTime,"end-time");
        if(endTimeObj.isBefore(startTimeObj))
            throw new IllegalArgumentException("start time must be before end time");
        List<SearchEntity> searchEntities = searchService.findByPhoneNumberAndTime(phoneNumber,startTimeObj,endTimeObj,
                page,size);
        if (searchEntities.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(searchEntities, HttpStatus.OK);
    }

    @GetMapping(path = "/text={text}/startTime={startTime}/endTime={endTime}/page={page}/size={size}")
    public ResponseEntity<List<SearchEntity>> searchByTextAndTime(@PathVariable("text") String text,
                                                                  @PathVariable("startTime") String startTime,
                                                                  @PathVariable("endTime") String endTime,
                                                                  @PathVariable("page") @Min(0) int page,
                                                                  @PathVariable("size") @Min(1) int size) {

        LocalDateTime startTimeObj = Validator.localDateTimeValidator(startTime,"start-time");
        LocalDateTime endTimeObj = Validator.localDateTimeValidator(endTime,"end-time");
        if(endTimeObj.isBefore(startTimeObj))
            throw new IllegalArgumentException("start time must be before end time");
        List<SearchEntity> searchEntities = searchService.findByTextAndTime(text,startTimeObj,endTimeObj,
                page,size);
        if (searchEntities.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(searchEntities, HttpStatus.OK);
    }
}
