package com.meesho.notificationservice.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
public class Message {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   @NotBlank(message = "text field cannot be empty")
   private String text;
   @NotNull
   @Size(min=10,max=10,message = "phone number must be of 10 characters")
   private String phoneNumber;
   private String status;
   private LocalDateTime createdOn;
   private LocalDateTime lastUpdatedAt;
}
