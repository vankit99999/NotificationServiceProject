package com.meesho.notificationservice.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Data
public class Message {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   @NotBlank(message = "text field cannot be empty")
   private String text;
   @Pattern(regexp="[6-9][0-9]{9}",message = "phone number must be of 10 digits,1st digit must be in range-[6,9]")
   private String phoneNumber;
   private String status;
   private LocalDateTime createdOn;
   private LocalDateTime lastUpdatedAt;
}
