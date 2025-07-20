package org.example.util;

import org.example.entity.AnalysisResponse;
import org.example.entity.AnalyzeResponse;
import org.example.entity.FileResponse;
import org.example.entity.UploadUrlResponse;
import org.example.exception.FileNotFoundException;
import org.example.exception.GeneralVirusTotalException;
import org.example.exception.ProcessingFileException;
import org.example.exception.SendingRequestException;
import org.example.exception.ThreadExecutingException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

/**
 * Utility class for VirusTotal API operations.
 */
public class ApiUtil {
    public static final String API_KEY = System.getenv("VIRUS_TOTAL_API_KEY");
    private static final String KEY_HEADER = "x-apikey";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String CONTENT_TYPE_HEADER = "application/json";

    private static final String CHECK_BY_HASH_URI = "https://www.virustotal.com/api/v3/files/";
    private static final String ANALYSIS_URI = "https://www.virustotal.com/api/v3/analyses/";
    private static final String ANALYZE_URI = "https://www.virustotal.com/api/v3/files";
    private static final String UPLOAD_URL_URI = "https://www.virustotal.com/api/v3/files/upload_url";

    private static final Integer ONE_MB = 1024 * 1024;
    private static final Integer MEDIUM_FILE_SIZE_MB = 32;
    private static final Integer LARGE_FILE_SIZE_MB = 650;

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private ApiUtil() {
        throw new IllegalStateException(Localization.get("exception.utility_class"));
    }

    /**
     * Processes a file for VirusTotal analysis.
     * 
     * @param args Command-line arguments containing file path
     * @return Analysis response or null for large files
     * @throws ProcessingFileException If file operations fail
     */
    public static AnalysisResponse processFile(String[] args) {
        try {
            FileResponse fileResponse = getAnalysisByHash(ProgramTools.calculateFileHash("MD5", args[0]));
            System.out.println(Localization.get("info.file_exists"));

            return ProgramTools.mapToAnalysisResponse(fileResponse);
        } catch (FileNotFoundException _) {
            System.out.println(Localization.get("info.file_not_found"));
        }

        try {
            long fileSize = Files.size(Path.of(args[0]));
            if (fileSize < (long) MEDIUM_FILE_SIZE_MB * ONE_MB) {
                System.out.println(Localization.get("info.small_file"));
                AnalyzeResponse analyzeResponse = analyzeFile(ANALYZE_URI, args[0]);

                return getAnalysisResponse(analyzeResponse);
            } else if (fileSize < (long) LARGE_FILE_SIZE_MB * ONE_MB) {
                System.out.printf(Localization.get("info.medium_file", fileSize / ONE_MB));
                UploadUrlResponse uploadUrlResponse = getUploadUrl();
                AnalyzeResponse analyzeResponse = analyzeFile(uploadUrlResponse.getData(), args[0]);

                return getAnalysisResponse(analyzeResponse);
            } else {
                System.out.println(Localization.get("info.large_file"));
            }
        } catch (IOException e) {
            throw new ProcessingFileException(e.getMessage());
        }

        return null;
    }

    /**
     * Prints formatted scan results to console.
     * 
     * @param analysisResponse Analysis results to display
     */
    public static void printScanResults(AnalysisResponse analysisResponse) {
        System.out.printf(Localization.get("info.scan_results",
                analysisResponse.getData().getAttributes().getStats().getMalicious(),
                analysisResponse.getData().getAttributes().getStats().getSuspicious(),
                analysisResponse.getData().getAttributes().getStats().getUndetected(),
                analysisResponse.getData().getAttributes().getStats().getTypeUnsupported(),
                analysisResponse.getData().getAttributes().getStats().getFailure(),
                analysisResponse.getData().getAttributes().getStats().getHarmless()));

        if (analysisResponse.getData().getAttributes().getStats().getMalicious()
                + analysisResponse.getData().getAttributes().getStats().getSuspicious()
                + analysisResponse.getData().getAttributes().getStats().getFailure()
                + analysisResponse.getData().getAttributes().getStats().getHarmless() != 0) {
            for (Map.Entry<String, AnalysisResponse.Data.Attributes.Result> result :
                    analysisResponse.getData().getAttributes().getResults().entrySet()) {

                if (!result.getValue().getCategory().equals("undetected")
                && !result.getValue().getCategory().equals("type-unsupported")
                && !result.getValue().getCategory().equals("confirmed-timeout")) {
                    System.out.println(Localization.get("info.engine_results",
                            result.getKey(), result.getValue().getCategory(),
                            result.getValue().getThreatType() == null ? "N/A"
                                    : result.getValue().getThreatType()));
                }
            }
        }
    }

    private static UploadUrlResponse getUploadUrl() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(UPLOAD_URL_URI))
                .header(ACCEPT_HEADER, CONTENT_TYPE_HEADER)
                .header(KEY_HEADER, API_KEY)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return ResponseHandler.handleResponse(response, UploadUrlResponse.class);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SendingRequestException(e.getMessage());
        }
    }

    private static FileResponse getAnalysisByHash(String hash) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CHECK_BY_HASH_URI + hash))
                .header(ACCEPT_HEADER, CONTENT_TYPE_HEADER)
                .header(KEY_HEADER, API_KEY)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return ResponseHandler.handleResponse(response, FileResponse.class);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SendingRequestException(e.getMessage());
        }
    }

    private static AnalysisResponse getAnalysis(String id) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(ANALYSIS_URI + id))
                .header(ACCEPT_HEADER, CONTENT_TYPE_HEADER)
                .header(KEY_HEADER, API_KEY)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return ResponseHandler.handleResponse(response, AnalysisResponse.class);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SendingRequestException(e.getMessage());
        }
    }

    /**
     * Submits a file for analysis.
     * 
     * @param uri API endpoint URL
     * @param filePath Path to file for analysis
     * @return Analysis submission response
     */
    public static AnalyzeResponse analyzeFile(String uri, String filePath) {
        byte[] multipartBody = ProgramTools.createMultipartBody("file", filePath);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody))
                .uri(URI.create(uri))
                .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryABC123")
                .header(ACCEPT_HEADER, CONTENT_TYPE_HEADER)
                .header(KEY_HEADER, API_KEY)
                .build();

        try {
            System.out.println(Localization.get("info.sending_file"));
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(Localization.get("info.handling_response"));
            return ResponseHandler.handleResponse(response, AnalyzeResponse.class);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SendingRequestException(e.getMessage());
        }
    }

    private static AnalysisResponse getAnalysisResponse(AnalyzeResponse analyzeResponse) {
        AnalysisResponse analysisResponse;
        try {
            do {
                System.out.println(Localization.get("info.waiting_response"));
                Thread.sleep(Duration.ofSeconds(10));
                analysisResponse = getAnalysis(analyzeResponse.getData().getId());

                if (analysisResponse.getData().getAttributes().getStatus().equals("failed")) {
                    throw new GeneralVirusTotalException(Localization.get("exception.failed_analysis"));
                }
            } while (!analysisResponse.getData().getAttributes().getStatus().equals("completed"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ThreadExecutingException(e.getMessage());
        }

        return analysisResponse;
    }
}
