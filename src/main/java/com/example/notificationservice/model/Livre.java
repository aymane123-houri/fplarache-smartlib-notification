package com.example.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Livre {
    private Long id;
    private String titre;
    private String auteur;
    private String genre;
    private String description;
    private String status;
}
