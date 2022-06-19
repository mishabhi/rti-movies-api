package com.rti.api.exception.handler;

import com.rti.api.exception.CustomStandardException;
import com.rti.api.exception.MovieNotFoundException;
import com.rti.api.model.response.Response;
import com.rti.api.rest.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Order(20)
@ControllerAdvice
@Slf4j
public class NotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
        MovieNotFoundException.class
    })
    public final ResponseEntity<Response> handleNotFoundExceptions(CustomStandardException ex, WebRequest request) {
        log.error(((ServletWebRequest) request).getRequest().getRequestURI(), ex);
        return ResponseHandler.handleNotFoundResponse(ex.getCategory(), List.of(ex.getLocalizedMessage()));
    }
}
