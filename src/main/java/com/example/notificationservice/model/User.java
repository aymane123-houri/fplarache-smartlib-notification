package com.example.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class User {
    private Long id;
    private String nom;
    private String email;
    private String tel;
    private String interest;
}
