package com.meesho.notificationservice.models.SMS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
