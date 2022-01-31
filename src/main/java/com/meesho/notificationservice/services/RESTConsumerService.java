package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.RESTEntities.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RESTConsumerService {

    public RESTResponse sendRequest(String phoneNumber,String text) {
        RESTRequest restRequest = new RESTRequest("sms",new Channels(new Sms(text)),
                Arrays.asList(new Destination(Arrays.asList("+91"+phoneNumber))));
        System.out.println(restRequest);
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://api.imiconnect.in/resources/v1/messaging";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("key","93ceffda-5941-11ea-9da9-025282c394f2");
        HttpEntity<RESTRequest> httpEntity = new HttpEntity<RESTRequest>(restRequest,httpHeaders);
        ResponseEntity<RESTResponse> restResponseResponseEntity = restTemplate.exchange(resourceUrl,
                HttpMethod.POST,httpEntity,RESTResponse.class);
        System.out.println(restResponseResponseEntity);
        return restResponseResponseEntity.getBody();
    }
}