package com.meesho.notificationservice.models.IMIConnect;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IMIRequest {
    private String deliverychannel;
    private Channels channels;
    private List<Destination> destination;
}
