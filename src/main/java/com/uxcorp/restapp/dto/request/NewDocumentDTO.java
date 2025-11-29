package com.uxcorp.restapp.dto.request;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;

public class NewDocumentDTO {

    private MultipartFile file;

    @NotBlank(message = "Description is required")
    private String description;

    public NewDocumentDTO() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
