package com.example.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OverdueUser {
    private User user;
    private List<Emprunt> emprunts = new ArrayList<>();

    public void addEmprunt(Emprunt emprunt) {
        this.emprunts.add(emprunt);
    }
}
