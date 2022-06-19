package com.rti.api.model.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    @JsonProperty(value = "metadata")
    private PageMetadata pageMetadata;
    private int status;
    private String message;
    private Object data;
    private List<String> errors;

    public Response(String message, int status, List<String> errors) {
        this.message = message;
        this.errors = errors;
        this.status = status;
    }
}
