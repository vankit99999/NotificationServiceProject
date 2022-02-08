package com.meesho.notificationservice.models.RESTEntities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//IMI Response
public class RESTRequest {
    private String deliverychannel;
    private Channels channels;
    private List<Destination> destination;
}
