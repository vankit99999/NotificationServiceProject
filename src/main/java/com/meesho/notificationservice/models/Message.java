package com.meesho.notificationservice.models;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
//@Table(name = "Messages")
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
