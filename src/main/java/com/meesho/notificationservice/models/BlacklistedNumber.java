package com.meesho.notificationservice.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
//@Table(name = "BlackList")
public class BlacklistedNumber {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String phoneNumber;

}
