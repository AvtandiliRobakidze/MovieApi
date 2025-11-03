package com.example.movieapi.service;

import com.example.movieapi.RabbitConfig;
import com.example.movieapi.event.MovieEvent;
import com.example.movieapi.model.Movie;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MovieRepository movieRepository;

    public Movie addNewMovie(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);

        // Create and send event
        MovieEvent event = new MovieEvent(
                "MOVIE_CREATED",
                savedMovie.getId(),
                savedMovie.getTitle(),
                LocalDateTime.now()
        );

        rabbitTemplate.convertAndSend(
                RabbitConfig.MOVIE_EXCHANGE,
                RabbitConfig.MOVIE_ROUTING_KEY,
                event
        );

        return savedMovie;
    }
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        if (movieRepository.existsById(id)) {
            updatedMovie.setId(id);
            return movieRepository.save(updatedMovie);
        }
        return null;
    }

    public boolean deleteMovie(Long id) {
        if (movieRepository.existsById(id)) {
            Movie movie = movieRepository.findById(id).orElse(null);
            movieRepository.deleteById(id);

            // Create and send event
            if (movie != null) {
                MovieEvent event = new MovieEvent(
                        "MOVIE_DELETED",
                        movie.getId(),
                        movie.getTitle(),
                        LocalDateTime.now()
                );

                rabbitTemplate.convertAndSend(
                        RabbitConfig.MOVIE_EXCHANGE,
                        RabbitConfig.MOVIE_ROUTING_KEY,
                        event
                );
            }

            return true;
        }
        return false;
    }
}


