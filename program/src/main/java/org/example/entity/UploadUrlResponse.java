package org.example.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Response containing upload URL for large files.
 */
@Getter
@Setter
public class UploadUrlResponse {
    /**
     * URL string for file upload.
     */
    private String data;
}
