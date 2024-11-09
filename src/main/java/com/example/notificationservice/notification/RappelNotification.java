package com.example.notificationservice.notification;

import com.example.notificationservice.model.OverdueUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter @Getter @NoArgsConstructor
public class RappelNotification {
    private Date date;
    private List<OverdueUser> overdueUsers;

    public RappelNotification(Date date, List<OverdueUser> overdueUsers) {
        this.date = date;
        this.overdueUsers = overdueUsers;
    }
}
