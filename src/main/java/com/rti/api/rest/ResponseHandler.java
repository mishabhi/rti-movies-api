package com.rti.api.rest;

import com.rti.api.model.response.Response;
import com.rti.api.model.response.PageMetadata;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class ResponseHandler {

    private static ResponseEntity<Response> sendResponse(Object data, HttpStatus status) {
        Response response = new Response();
        response.setStatus(status.value());

        if(Objects.isNull(data)){
            return ResponseEntity.status(status).body(response);
        }

        boolean isPaginatedData = data instanceof Page;
        if(isPaginatedData) {
            response.setPageMetadata(preparePageMetadata((Page)data));
            response.setData(((Page<?>) data).getContent());
        }
        else {
            response.setData(data);
        }
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<Response> handleCreated(Object data) {
        return sendResponse(data, HttpStatus.CREATED);
    }

    public static ResponseEntity<Response> handleSuccessResponse(Object data) {
        return sendResponse(data, HttpStatus.OK);
    }

    public static ResponseEntity<Response> handlePaginatedResponse(Object data) {
        return sendResponse(data, HttpStatus.OK);
    }

    public static ResponseEntity<Response> handleBadRequestResponse(String message, List<String> errors){
        Response response = new Response(message, HttpStatus.BAD_REQUEST.value(), errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public static ResponseEntity<Response> handleNotFoundResponse(String message, List<String> errors){
        Response response = new Response(message, HttpStatus.NOT_FOUND.value(), errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public static ResponseEntity<Response> handleInternalServerErrorResponse(String message, List<String> errors){
        Response response = new Response(message, HttpStatus.NOT_FOUND.value(), errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public static <T> PageMetadata preparePageMetadata(Page<T> page){
        List<String> sortOrders = page
                .getSort()
                .stream()
                .map(order -> order.toString().toUpperCase())
                .collect(Collectors.toList());

        return new PageMetadata(
            page.getNumberOfElements(),
            page.getTotalElements(),
            page.getPageable().getPageNumber(),
            page.getTotalPages(),
            page.getSize(),
            sortOrders
        );
    }
}
