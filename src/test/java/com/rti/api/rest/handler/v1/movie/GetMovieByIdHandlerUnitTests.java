package com.rti.api.rest.handler.v1.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rti.api.config.security.CustomWebSecurityConfigurerAdapter;
import com.rti.api.exception.MovieNotFoundException;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
class GetMovieByIdHandlerUnitTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    @Tag("testUnit")
    @DisplayName("/v1/movies/{id} returns 200 status code with movie details")
    public void getMovieByIdReturnsMovie() throws Exception {
        MoviePresentation expectedMovie = new MoviePresentation();
        expectedMovie.setId(1L);
        expectedMovie.setTitle("Test Movie 1");
        expectedMovie.setImageUrl("https://www.google.com");
        expectedMovie.setDescription("Test Movie");
        expectedMovie.setRating(2.3);
        expectedMovie.setReleaseDate("2021-11-23");

        Mockito
            .when(movieService.getById(expectedMovie.getId()))
            .thenReturn(expectedMovie);
        String rawResponse = mockMvc.perform(get("/v1/movies/" + expectedMovie.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Mockito
            .verify(movieService, Mockito.times(1))
            .getById(Mockito.any(Long.class));
        Response response = objectMapper.readValue(rawResponse, Response.class);
        MoviePresentation actualMovie = objectMapper.convertValue(response.getData(), MoviePresentation.class);
        Assertions.assertThat(actualMovie).isEqualTo(expectedMovie);
    }

    @Test
    @Tag("testUnit")
    @DisplayName("/v1/movies/{id} returns 404 when given id does not exists")
    public void getMovieByIdThrowsMovieNotFoundException() throws Exception {
        Long id = 1L;
        Mockito
            .when(movieService.getById(id))
            .thenThrow(new MovieNotFoundException(id));

        mockMvc.perform(get("/v1/movies/" + id))
            .andExpect(status().isNotFound())
            .andExpectAll(
                jsonPath("$.errors", hasSize(1)),
                jsonPath("$.errors[0]", is("Movie with id 1 not found")),
                jsonPath("$.message", is("Movie Not Found"))
            );

        Mockito
            .verify(movieService, Mockito.times(1))
            .getById(Mockito.any(Long.class));

    }
}
