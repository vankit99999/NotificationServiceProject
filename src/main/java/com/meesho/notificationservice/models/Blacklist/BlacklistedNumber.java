package com.meesho.notificationservice.models.Blacklist;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class BlacklistedNumber implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String phoneNumber;
}
