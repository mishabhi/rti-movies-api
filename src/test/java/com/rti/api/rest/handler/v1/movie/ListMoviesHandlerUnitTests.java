package com.rti.api.rest.handler.v1.movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rti.api.config.security.CustomWebSecurityConfigurerAdapter;
import com.rti.api.exception.handler.GenericExceptionHandler;
import com.rti.api.exception.handler.NotFoundExceptionHandler;
import com.rti.api.model.movie.MoviePresentation;
import com.rti.api.model.response.Response;
import com.rti.api.service.movie.MovieService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = {
    GenericExceptionHandler.class,
    NotFoundExceptionHandler.class,
    CustomWebSecurityConfigurerAdapter.class
})
@WebMvcTest(MovieHandler.class)
@Import(MovieHandler.class)
class ListMoviesHandlerUnitTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    @Tag("testUnit")
    @DisplayName("/v1/movies returns 200 with paginated list of movies")
    public void listMoviesReturnsListOfMovies() throws Exception {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "createdAt");
        List<MoviePresentation> expectedMovies = this.movies();
        Page<MoviePresentation> paginatedPartners = new PageImpl<>(expectedMovies, pageable, expectedMovies.size());

        Mockito
            .when(movieService.getPaginatedResults(Mockito.any(Pageable.class)))
            .thenReturn(paginatedPartners);
        String rawResponse = mockMvc.perform(get("/v1/movies"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Mockito
            .verify(movieService, Mockito.times(1))
            .getPaginatedResults(Mockito.any(Pageable.class));
        Response response = objectMapper.readValue(rawResponse, Response.class);
        List<MoviePresentation> actualMovies = objectMapper.convertValue(response.getData(), new TypeReference<>() {});
        for (int index = 0; index < actualMovies.size(); index++) {
            Assertions.assertThat(actualMovies.get(index)).isEqualTo(expectedMovies.get(index));
        }
    }

    @Test
    @Tag("testUnit")
    @DisplayName("/v1/movies returns 200 with empty list of movies")
    public void listMoviesReturnsEmptyList() throws Exception {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "createdAt");
        Page<MoviePresentation> paginatedPartners = new PageImpl<>(Collections.emptyList(), pageable, 0);

        Mockito
            .when(movieService.getPaginatedResults(Mockito.any(Pageable.class)))
            .thenReturn(paginatedPartners);
        String rawResponse = mockMvc.perform(get("/v1/movies"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Mockito
            .verify(movieService, Mockito.times(1))
            .getPaginatedResults(Mockito.any(Pageable.class));
        Response response = objectMapper.readValue(rawResponse, Response.class);
        List<MoviePresentation> actualMovies = objectMapper.convertValue(response.getData(), new TypeReference<>() {});
        Assertions.assertThat(actualMovies.isEmpty()).isTrue();
    }

    private List<MoviePresentation> movies() {
        MoviePresentation movie2 = new MoviePresentation();
        movie2.setId(2L);
        movie2.setTitle("Test Movie 2");
        movie2.setImageUrl("https://www.google.com");
        movie2.setDescription("Test Movie");
        movie2.setRating(2.3);
        movie2.setReleaseDate("2020-01-12");

        MoviePresentation movie1 = new MoviePresentation();
        movie1.setId(1L);
        movie1.setTitle("Test Movie 1");
        movie1.setImageUrl("https://www.google.com");
        movie1.setDescription("Test Movie");
        movie1.setRating(2.3);
        movie1.setReleaseDate("2021-11-23");
        return List.of(movie1, movie2);
    }
}
