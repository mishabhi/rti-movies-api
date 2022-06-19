package com.rti.api.exception;

public class MovieNotFoundException extends CustomStandardException {

    public MovieNotFoundException(Long id) {
        super("Movie Not Found", String.format("Movie with id %s not found", id));
    }
}
