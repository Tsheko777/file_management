package com.uxcorp.restapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateDocumentNameDTO {
    @NotBlank(message = "File name is required")
    private String fileName;
}
