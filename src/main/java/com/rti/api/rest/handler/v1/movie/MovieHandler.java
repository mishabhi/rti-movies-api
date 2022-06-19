package com.rti.api.rest.handler.v1.movie;

import com.rti.api.exception.MovieNotFoundException;
import com.rti.api.model.movie.MoviePresentation;
import com.rti.api.model.response.Response;
import com.rti.api.rest.ResponseHandler;
import com.rti.api.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieHandler {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<Response> listMovies(@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable requestedPage) {
        Page<MoviePresentation> movies = movieService.getPaginatedResults(requestedPage);
        return ResponseHandler.handlePaginatedResponse(movies);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response> getMovieById(@PathVariable Long id) throws MovieNotFoundException {
        return ResponseHandler.handleSuccessResponse(movieService.getById(id));
    }
}
