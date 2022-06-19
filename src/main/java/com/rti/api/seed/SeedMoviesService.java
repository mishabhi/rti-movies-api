package com.rti.api.seed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rti.api.model.persistence.MovieEntity;
import com.rti.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeedMoviesService {

    private final MovieRepository movieRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    public void seedMovies() throws JsonProcessingException {
        log.info("*********************************Start - seeding of movies****************************");
        String moviesJson = "";
        try {
            InputStream inputStream = new ClassPathResource("seed/movies.json").getInputStream();
            byte[] rawData = FileCopyUtils.copyToByteArray(inputStream);
            moviesJson = new String(rawData, StandardCharsets.UTF_8);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        List<MovieEntity> movies = objectMapper.readValue(moviesJson, new TypeReference<>(){});
        movieRepository.saveAllAndFlush(movies);
        log.info("Total movies seeded: {}", movies.size());
        log.info("*********************************End - seeding of movies****************************");
    }
}
