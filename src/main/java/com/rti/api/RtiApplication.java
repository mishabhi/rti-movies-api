package com.rti.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rti.api.seed.SeedMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class RtiApplication {

    @Autowired
    private SeedMoviesService seedMoviesService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public static void main(String[] args) {
        SpringApplication.run(RtiApplication.class, args);
    }

    @PostConstruct
    public void init() throws JsonProcessingException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        if(activeProfile.equalsIgnoreCase("dev")) seedMoviesService.seedMovies();
    }
}
