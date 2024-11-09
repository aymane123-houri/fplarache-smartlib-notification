package com.example.notificationservice.notification;

import com.example.notificationservice.model.Emprunt;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter @Getter @NoArgsConstructor
public class ConfirmationNotification {
    private Date date;
    private Emprunt emprunt;

    public ConfirmationNotification(Date date, Emprunt emprunt) {
        this.date = date;
        this.emprunt = emprunt;
    }
}
