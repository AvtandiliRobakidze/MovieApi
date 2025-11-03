package com.example.movieapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Director is required")
    @Column(nullable = false)
    private String director;

    @Min(value = 1800, message = "Release year must be after 1800")
    @Max(value = 2030, message = "Release year cannot be in the distant future")
    @Column(name = "release_year")
    private int releaseYear;

    @DecimalMin(value = "0.0", message = "Rating must be positive")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10.0")
    private double rating;

    @NotBlank(message = "Genre is required")
    private String genre;

    // Default constructor
    public Movie() {}

    // Constructor without id
    public Movie(String title, String director, int releaseYear, double rating, String genre) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.genre = genre;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}