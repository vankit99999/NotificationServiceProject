package com.meesho.notificationservice.models;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Document(indexName = "messagesindex5")
public class SearchEntity {
    @Id
    private String id;
    @Field
    private String text;
    @Field
    private String phoneNumber;
    @Field
    private String status;
    @Field(type = FieldType.Date, format = {} ,pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;
    @Field(type = FieldType.Date, format = {},pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime lastUpdatedAt;
}
