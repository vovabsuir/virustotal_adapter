package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

/**
 * Represents the analysis response from VirusTotal API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AnalysisResponse {
    /**
     * Contains the main data payload of the response.
     */
    private Data data;

    /**
     * Nested data structure for analysis response.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Data {
        /**
         * Attributes containing scan results.
         */
        private Attributes attributes;

        
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        @Setter
        public static class Attributes {
            /**
             * Overall analysis status.
             */
            private String status;
            /**
             * Statistical summary of scan results.
             */
            private Stats stats;
             /**
             * Detailed information per scanning engine.
             */
            private Map<String, Result> results;

            /**
             * Represents an individual scan engine result.
             */
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Getter
            @Setter
            public static class Result {
                @JsonProperty("engine-name")
                private String engineName;
                private String category;
                @JsonProperty("result")
                private String threatType;
            }

            /**
             * Statistical breakdown of scan categories.
             */
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Getter
            @Setter
            public static class Stats {
                private Integer malicious;
                private Integer suspicious;
                private Integer undetected;
                private Integer failure;
                private Integer harmless;
                @JsonProperty("type-unsupported")
                private Integer typeUnsupported;
            }
        }
    }
}
