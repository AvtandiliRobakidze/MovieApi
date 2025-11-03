package com.example.movieapi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitConfig {
    public static final String MOVIE_QUEUE = "movie.notifications";
    public static final String MOVIE_EXCHANGE = "movie.exchange";
    public static final String MOVIE_ROUTING_KEY = "movie.event";


    @Bean
    public Queue movieQueue() {
        return new Queue(MOVIE_QUEUE, true);
    }

    @Bean
    public DirectExchange movieExchange() {
        return new DirectExchange(MOVIE_EXCHANGE);
    }
    @Bean
    public Binding movieBinding() {
        return BindingBuilder
                .bind(movieQueue())
                .to(movieExchange())
                .with(MOVIE_ROUTING_KEY);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }

}
