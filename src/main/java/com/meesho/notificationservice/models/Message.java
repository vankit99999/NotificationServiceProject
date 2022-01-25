package com.meesho.notificationservice.models;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Message {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   private String text;
   private String phoneNumber;
   private String status;
   private LocalDateTime createdOn;
   private LocalDateTime lastUpdatedAt;
}
