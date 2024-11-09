package com.example.notificationservice.service;

import com.example.notificationservice.document.RappelNotification;
import com.example.notificationservice.repository.RappelNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RappelNotificationService {
    private final RappelNotificationRepository repository;
    public RappelNotificationService(RappelNotificationRepository repository) {
        this.repository = repository;
    }

    public void createNotification(RappelNotification notifications){
        repository.save(notifications);
    }

    public RappelNotification getById(String id){
        return repository.findById(id).orElse(null);
    }

    public List<RappelNotification> getAll(){
        return repository.findAll();
    }
}
