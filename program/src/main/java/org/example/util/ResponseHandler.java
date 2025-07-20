package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.AuthenticationRequiredException;
import org.example.exception.ErrorResponse;
import org.example.exception.FileNotFoundException;
import org.example.exception.GeneralVirusTotalException;
import org.example.exception.IncorrectJsonMappingException;
import org.example.exception.QuotaExceededException;
import org.example.exception.UnsupportedQueryContentException;
import org.example.exception.WrongApiKeyException;
import java.net.http.HttpResponse;

/**
 * Handles API response parsing and error mapping.
 */
public class ResponseHandler {
    private static final ObjectMapper mapper = new ObjectMapper();

    private ResponseHandler() {
        throw new IllegalStateException(Localization.get("exception.utility_class"));
    }

    /**
     * Processes HTTP response and maps to Java object.
     * 
     * @param <T> Response type
     * @param response HTTP response
     * @param responseClass Target class for deserialization
     * @return Deserialized response object
     * @throws UnsupportedQueryContentException For content-related API errors
     * @throws AuthenticationRequiredException For authentication issues
     * @throws FileNotFoundException For 404 responses
     * @throws QuotaExceededException For usage limit errors
     * @throws GeneralVirusTotalException For other API errors
     */
    public static <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) {
        try {
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return mapper.readValue(response.body(), responseClass);
            } else {
                ErrorResponse error = mapper.readValue(response.body(), ErrorResponse.class);
                switch (error.getError().getCode()) {
                    case "UnsupportedContentQueryError" ->
                            throw new UnsupportedQueryContentException(error.getError().getMessage());
                    case "AuthenticationRequiredError" ->
                            throw new AuthenticationRequiredException(error.getError().getMessage());
                    case "WrongCredentialsError" ->
                            throw new WrongApiKeyException(error.getError().getMessage());
                    case "NotFoundError" ->
                            throw new FileNotFoundException();
                    case "QuotaExceededError" ->
                            throw new QuotaExceededException(error.getError().getMessage());
                    default -> throw new GeneralVirusTotalException("Code: " + error.getError().getCode() +
                            "; Message: " + error.getError().getMessage());
                }
            }
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonMappingException(e.getMessage());
        }
    }
}
