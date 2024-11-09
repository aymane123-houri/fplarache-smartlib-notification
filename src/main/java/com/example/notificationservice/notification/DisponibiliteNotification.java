package com.example.notificationservice.notification;

import com.example.notificationservice.model.Livre;
import com.example.notificationservice.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter @Getter @NoArgsConstructor
public class DisponibiliteNotification{
    private Date date;
    private List<User> users;
    private Livre livre;

    public DisponibiliteNotification(Date date, List<User> users, Livre livre) {
        this.date = date;
        this.users = users;
        this.livre = livre;
    }
}
