package com.example.notificationservice.service;

import com.example.notificationservice.event.MovieEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @RabbitListener(queues = "movie.notifications")
    public void handleMovieEvent(MovieEvent event) {
        System.out.println("========================================");
        System.out.println("NOTIFICATION RECEIVED!");
        System.out.println("Event Type: " + event.getEventType());
        System.out.println("Movie ID: " + event.getMovieId());
        System.out.println("Movie Title: " + event.getMovieTitle());
        System.out.println("Timestamp: " + event.getTimestamp());
        System.out.println("========================================");
    }
}