package com.uxcorp.restapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateDocumentDescriptionDTO {

    @NotBlank(message = "Description is required")
    private String description;

    public UpdateDocumentDescriptionDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
