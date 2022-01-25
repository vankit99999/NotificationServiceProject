package com.meesho.notificationservice.models;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
//@Table(name = "Messages")
public class Message {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
//   @Field
   private String text;
//   @Field
   private String phoneNumber;
//   @Field
   private String status;
//   @Field
   private LocalDateTime createdOn;
//   @Field
   private LocalDateTime lastUpdatedAt;
}
