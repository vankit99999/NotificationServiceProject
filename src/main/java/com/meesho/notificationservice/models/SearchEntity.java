package com.meesho.notificationservice.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

import static com.meesho.notificationservice.constants.Constants.DATE_PATTERN;

@Data
@Document(indexName = "messagesindex5")
@Builder
public class SearchEntity {
    @Id
    private String id;
    @Field
    private String text;
    @Field
    private String phoneNumber;
    @Field
    private String status;
    @Field(type = FieldType.Date, format = {} ,pattern = DATE_PATTERN)
    private LocalDateTime createdAt;
    @Field(type = FieldType.Date, format = {},pattern = DATE_PATTERN)
    private LocalDateTime lastUpdatedAt;
}
