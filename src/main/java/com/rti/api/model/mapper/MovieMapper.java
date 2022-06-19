package com.rti.api.model.mapper;

import com.rti.api.model.movie.MoviePresentation;
import com.rti.api.model.persistence.MovieEntity;
import com.rti.api.model.request.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    MovieEntity mapToMovieEntity(Movie movie);

    MoviePresentation mapToMoviePresentation(MovieEntity movieEntity);
}
