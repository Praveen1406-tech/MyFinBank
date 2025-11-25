package com.myfinbank.myfinbank.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @Column(nullable = false, unique = true)
    private String username;
    

    @Column(nullable = false)
    private String password;
}
