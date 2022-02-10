package com.meesho.notificationservice.models.Blacklist;

import lombok.Data;

import java.util.List;

@Data
public class BlacklistRequest {
    private List<String> phoneNumbers;
}
