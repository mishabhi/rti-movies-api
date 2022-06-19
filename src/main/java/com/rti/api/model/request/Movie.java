package com.rti.api.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Movie {

    private String title;
    private String imageUrl;
    private String description;
    private float rating;
    private LocalDate releaseDate;
}
