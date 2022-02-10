package com.meesho.notificationservice.models.Blacklist;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BlacklistResponse {
    private List<String> invalidPhoneNumbers;
    private List<String> operationSuccessfullOnValidPhoneNumbers;
}
