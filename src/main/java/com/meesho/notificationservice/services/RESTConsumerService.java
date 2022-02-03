package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.RESTEntities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static com.meesho.notificationservice.constants.Constants.LOGGER_NAME;

@Service
public class RESTConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    public ResponseEntity<RESTResponse> sendRequest(String phoneNumber,String text) {
        RESTRequest restRequest = new RESTRequest("sms",new Channels(new Sms(text)),
                Arrays.asList(new Destination(Arrays.asList("+91"+phoneNumber))));
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://api.imiconnect.in/resources/v1/messaging";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("key","93ceffda-5941-11ea-9da9-025282c394f2");
        HttpEntity<RESTRequest> httpEntity = new HttpEntity<RESTRequest>(restRequest,httpHeaders);
        ResponseEntity<RESTResponse> restResponseResponseEntity = restTemplate.exchange(resourceUrl,
                HttpMethod.POST,httpEntity,RESTResponse.class);
        logger.info(String.format("message %s successfully sent to %s via 3rd party API",text,phoneNumber));
        return restResponseResponseEntity;
    }
}