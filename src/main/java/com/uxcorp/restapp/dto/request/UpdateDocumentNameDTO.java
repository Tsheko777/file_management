package com.uxcorp.restapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateDocumentNameDTO {

    @NotBlank(message = "File name is required")
    private String fileName;

    public UpdateDocumentNameDTO() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
