package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

/**
 * Represents file analysis response from hash lookup.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class FileResponse {
    private Data data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Data {
        private Attributes attributes;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        @Setter
        public static class Attributes {
            @JsonProperty("last_analysis_results")
            private Map<String, AnalysisResponse.Data.Attributes.Result> results;
            @JsonProperty("last_analysis_stats")
            private AnalysisResponse.Data.Attributes.Stats stats;
        }
    }
}
