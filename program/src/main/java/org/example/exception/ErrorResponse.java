package org.example.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Standard error response from VirusTotal API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ErrorResponse {
    private Error error;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Error {
        private String code;
        private String message;
    }
}
