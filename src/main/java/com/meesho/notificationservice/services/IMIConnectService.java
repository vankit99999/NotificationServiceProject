package com.meesho.notificationservice.services;

import com.meesho.notificationservice.models.SMS.Message;
import com.meesho.notificationservice.models.IMIConnect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static com.meesho.notificationservice.constants.Constants.LOGGER_NAME;

@Service
public class IMIConnectService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${imi.key}")
    private String IMICONNECT_KEY;

    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    public ResponseEntity<IMIResponse> sendRequest(Message message) {
        IMIRequest iMIRequest = new IMIRequest("sms",new Channels(new Sms(message.getText())),
                Arrays.asList(new Destination(Arrays.asList("+91"+message.getPhoneNumber()))));
        String resourceUrl = "https://api.imiconnect.in/resources/v1/messaging";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("key",IMICONNECT_KEY);
        HttpEntity<IMIRequest> httpEntity = new HttpEntity<IMIRequest>(iMIRequest,httpHeaders);
        ResponseEntity<IMIResponse> restResponseResponseEntity = restTemplate.exchange(resourceUrl,
                HttpMethod.POST,httpEntity, IMIResponse.class);
        logger.info(String.format("message %s successfully sent to %s via 3rd party API",message.getText(),
                message.getPhoneNumber()));
        return restResponseResponseEntity;
    }
}