package com.meesho.notificationservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ControllerSuccessResponse {
    private Object data;
    private String message;
}
