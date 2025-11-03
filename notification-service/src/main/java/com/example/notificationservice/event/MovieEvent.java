package com.example.notificationservice.event;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MovieEvent implements Serializable {
    private String eventType;
    private long movieId;
    private String movieTitle;
    private LocalDateTime timestamp;

    public MovieEvent(String eventType, long movieId, String movieTitle, LocalDateTime timestamp) {
        this.eventType = eventType;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.timestamp = timestamp;
    }

    public MovieEvent(){

    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
