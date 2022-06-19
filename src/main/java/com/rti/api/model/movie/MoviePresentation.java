package com.rti.api.model.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MoviePresentation {

    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private double rating;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
//    private LocalDate releaseDate;
    private String releaseDate;
}
