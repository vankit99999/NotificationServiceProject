package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.models.RESTEntities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static com.meesho.notificationservice.constants.Constants.LOGGER_NAME;

@Service
public class RESTConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        int connectTimeout = 5000;
        int readTimeout = 5000;
        clientHttpRequestFactory.setConnectTimeout(connectTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        return clientHttpRequestFactory;
    }

    public ResponseEntity<RESTResponse> sendRequest(Message message) {
        RESTRequest restRequest = new RESTRequest("sms",new Channels(new Sms(message.getText())),
                Arrays.asList(new Destination(Arrays.asList("+91"+message.getPhoneNumber()))));
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        String resourceUrl = "https://api.imiconnect.in/resources/v1/messaging";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("key","93ceffda-5941-11ea-9da9-025282c394f2");
        HttpEntity<RESTRequest> httpEntity = new HttpEntity<RESTRequest>(restRequest,httpHeaders);
        ResponseEntity<RESTResponse> restResponseResponseEntity = restTemplate.exchange(resourceUrl,
                HttpMethod.POST,httpEntity,RESTResponse.class);
        logger.info(String.format("message %s successfully sent to %s via 3rd party API",message.getText(),
                message.getPhoneNumber()));
        return restResponseResponseEntity;
    }
}