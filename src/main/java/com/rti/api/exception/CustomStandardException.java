package com.rti.api.exception;

import lombok.Getter;

@Getter
public class CustomStandardException extends Exception{

    private final String category;
    private final String message;

    public CustomStandardException(String category, String message) {
        this.category = category;
        this.message = message;
    }
}
