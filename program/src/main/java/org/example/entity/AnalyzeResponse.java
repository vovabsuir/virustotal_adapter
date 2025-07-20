package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the initial analysis submission response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AnalyzeResponse {
    private Data data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Data {
        private String type;
        private String id;
    }
}
