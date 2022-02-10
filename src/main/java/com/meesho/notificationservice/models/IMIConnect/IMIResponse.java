package com.meesho.notificationservice.models.IMIConnect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IMIResponse {
    private List<Response> response;
}
