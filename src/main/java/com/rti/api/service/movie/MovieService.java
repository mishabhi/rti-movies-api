package com.rti.api.service.movie;

import com.rti.api.exception.MovieNotFoundException;
import com.rti.api.model.mapper.MovieMapper;
import com.rti.api.model.movie.MoviePresentation;
import com.rti.api.model.persistence.MovieEntity;
import com.rti.api.model.request.Movie;
import com.rti.api.repository.MovieRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MoviePresentation saveNew(Movie movie) {
        MovieEntity newMovie = movieMapper.mapToMovieEntity(movie);
        return movieMapper.mapToMoviePresentation(movieRepository.saveAndFlush(newMovie));
    }

    public MoviePresentation getById(Long id) throws MovieNotFoundException {
        MovieEntity existingMovie = movieRepository.getById(id);
        if(Objects.isNull(existingMovie)) throw new MovieNotFoundException(id);

        return movieMapper.mapToMoviePresentation(existingMovie);
    }

    public Page<MoviePresentation> getPaginatedResults(@NonNull Pageable requestedPage) {
        Page<MovieEntity> existingMovies = movieRepository.findAll(requestedPage);
        return new PageImpl<>(
                existingMovies.stream().map(movieMapper::mapToMoviePresentation).collect(Collectors.toList()),
                requestedPage,
                existingMovies.getSize()
        );
    }
}
