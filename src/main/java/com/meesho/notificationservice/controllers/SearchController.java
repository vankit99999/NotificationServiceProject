package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.SearchEntity;
import com.meesho.notificationservice.services.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "v1/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(path = "/phoneNumber={phoneNumber}/startTime={startTime}/endTime={endTime}/page={page}/size={size}")
    public List<SearchEntity> searchByPhoneNumberAndTime(@PathVariable("phoneNumber") String phoneNumber,
                                                  @PathVariable("startTime") String startTime,
                                                  @PathVariable("endTime") String endTime,
                                                  @PathVariable("page") int page,
                                                  @PathVariable("size") int size) {
        LocalDateTime startTimeObj = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSS"));
        LocalDateTime endTimeObj = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSS"));
        return searchService.findByPhoneNumberAndTime(phoneNumber,startTimeObj,endTimeObj,page,size);
    }

    @GetMapping(path = "/text={text}/startTime={startTime}/endTime={endTime}/page={page}/size={size}")
    public List<SearchEntity> searchByTextAndTime(@PathVariable("text") String text,
                                                     @PathVariable("startTime") String startTime,
                                                     @PathVariable("endTime") String endTime,
                                                    @PathVariable("page") int page,
                                                    @PathVariable("size") int size) {
        LocalDateTime startTimeObj = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSS"));
        LocalDateTime endTimeObj = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSS"));
        return searchService.findByTextAndTime(text,startTimeObj,endTimeObj,page,size);
    }
}
