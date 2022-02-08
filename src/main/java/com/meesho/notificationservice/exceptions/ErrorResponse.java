package com.meesho.notificationservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;
    private String description;
}
