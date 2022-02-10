package com.meesho.notificationservice.models.ElasticSearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

import static com.meesho.notificationservice.constants.Constants.DATE_PATTERN;

@Data
public class SearchMessagesByPhoneNumberAndTimeRequest {
    @Pattern(regexp="[6-9][0-9]{9}",message = "phone number must be of 10 digits,1st digit must be in range-[6,9]")
    private String phoneNumber;

    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime startTime;
    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime endTime;

    @Min(0)
    private int page;

    @Min(1)
    private int size;
}
