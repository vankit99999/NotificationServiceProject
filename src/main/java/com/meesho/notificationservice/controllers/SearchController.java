package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.ElasticSearch.SearchEntity;
import com.meesho.notificationservice.models.ControllerSuccessResponse;
import com.meesho.notificationservice.models.ElasticSearch.SearchMessagesByPhoneNumberAndTimeRequest;
import com.meesho.notificationservice.models.ElasticSearch.SearchMessagesByTextAndTimeRequest;
import com.meesho.notificationservice.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "v1/search")
@Validated
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping(path = "/byPhoneNumberAndTime")
    public ResponseEntity<ControllerSuccessResponse> searchByPhoneNumberAndTime(
            @Valid @RequestBody SearchMessagesByPhoneNumberAndTimeRequest searchMessagesByPhoneNumberAndTimeRequest) {
        if(searchMessagesByPhoneNumberAndTimeRequest.getEndTime().isBefore(
                searchMessagesByPhoneNumberAndTimeRequest.getStartTime()))
            throw new IllegalArgumentException("start time must be before end time");
        List<SearchEntity> searchEntities = searchService.findByPhoneNumberAndTime(searchMessagesByPhoneNumberAndTimeRequest);
        if (searchEntities.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(new ControllerSuccessResponse(
                searchEntities,"successfully fetched all messages with given constraints"), HttpStatus.OK);
    }

    @GetMapping(path = "/byTextAndTime")
    public ResponseEntity<ControllerSuccessResponse> searchByTextAndTime(
            @Valid @RequestBody SearchMessagesByTextAndTimeRequest searchMessagesByTextAndTimeRequest) {
        if(searchMessagesByTextAndTimeRequest.getEndTime().isBefore(searchMessagesByTextAndTimeRequest.getStartTime()))
            throw new IllegalArgumentException("start time must be before end time");
        List<SearchEntity> searchEntities = searchService.findByTextAndTime(searchMessagesByTextAndTimeRequest);
        if (searchEntities.isEmpty())
            throw new NoSuchElementException("No messages found");
        return new ResponseEntity<>(new ControllerSuccessResponse(
                searchEntities,"successfully fetched all messages with given constraints"), HttpStatus.OK);
    }
}
