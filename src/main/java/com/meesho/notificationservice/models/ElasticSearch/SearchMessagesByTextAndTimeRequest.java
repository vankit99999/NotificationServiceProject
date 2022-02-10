package com.meesho.notificationservice.models.ElasticSearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

import static com.meesho.notificationservice.constants.Constants.DATE_PATTERN;

@Data
public class SearchMessagesByTextAndTimeRequest {
    private String text;

    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime startTime;

    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime endTime;

    @Min(0)
    private int page;

    @Min(1)
    private int size;
}
