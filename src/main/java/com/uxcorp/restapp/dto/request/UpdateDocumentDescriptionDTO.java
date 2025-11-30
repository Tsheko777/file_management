package com.uxcorp.restapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateDocumentDescriptionDTO {
    @NotBlank(message = "Description is required")
    private String description;
}
