package com.meesho.notificationservice.models.RESTEntities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Destination {
    private List<String> msisdn;
}
