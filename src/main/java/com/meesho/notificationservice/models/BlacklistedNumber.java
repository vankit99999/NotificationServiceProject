package com.meesho.notificationservice.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
//@Table(name = "BlackList")
public class BlacklistedNumber implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String phoneNumber;
}
