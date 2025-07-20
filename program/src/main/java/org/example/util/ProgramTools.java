package org.example.util;

import org.example.entity.AnalysisResponse;
import org.example.entity.FileResponse;
import org.example.exception.ProcessingFileException;
import org.example.exception.ServerException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for file operations and data mapping.
 */
public class ProgramTools {

    private ProgramTools() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates multipart request body for file upload.
     * 
     * @param type Form data field name
     * @param filePath Path to target file
     * @return Byte array containing request body
     */
    public static byte[] createMultipartBody(String type, String filePath) {
        String boundary = "----WebKitFormBoundaryABC123";
        String lineSeparator = "\r\n";
        Path file = Path.of(filePath);
        String fileName = file.getFileName().toString();

        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             InputStream fileStream = Files.newInputStream(file)) {

            output.write(("--" + boundary + lineSeparator).getBytes());
            output.write(("Content-Disposition: form-data; name=\"" + type + "\"; filename=\"" + fileName + "\"" + lineSeparator).getBytes());
            output.write(("Content-Type: application/octet-stream" + lineSeparator + lineSeparator).getBytes());
            output.write(("data:application/octet-stream;name="+ fileName + ";base64,").getBytes());

            try (OutputStream base64Stream = Base64.getEncoder().wrap(output)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileStream.read(buffer)) != -1) {
                    base64Stream.write(buffer, 0, bytesRead);
                }
            }

            output.write((lineSeparator + "--" + boundary + "--" + lineSeparator).getBytes());

            return output.toByteArray();
        } catch (IOException e) {
            throw new ProcessingFileException(e.getMessage());
        }
    }

    /**
     * Calculates file hash using specified algorithm.
     * 
     * @param algorithm Hashing algorithm
     * @param filePath Target file path
     * @return Hexadecimal hash string
     * @throws ServerException If algorithm is unavailable
     */
    public static String calculateFileHash(String algorithm, String filePath) {
        try (InputStream stream = new FileInputStream(filePath)) {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            byte[] hashBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (IOException e) {
            throw new ProcessingFileException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new ServerException(e.getMessage());
        }
    }

    /**
     * Maps FileResponse to AnalysisResponse structure.
     * 
     * @param fileResponse Original file response
     * @return Converted analysis response
     */
    public static AnalysisResponse mapToAnalysisResponse(FileResponse fileResponse) {
        AnalysisResponse analysisResponse = new AnalysisResponse();
        analysisResponse.setData(new AnalysisResponse.Data());
        analysisResponse.getData().setAttributes(new AnalysisResponse.Data.Attributes());
        analysisResponse.getData().getAttributes().setStats(fileResponse.getData().getAttributes().getStats());
        analysisResponse.getData().getAttributes().setResults(fileResponse.getData().getAttributes().getResults());

        return analysisResponse;
    }
}
