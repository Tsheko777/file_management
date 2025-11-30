package com.uxcorp.restapp.dto.request;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewDocumentDTO {

    private MultipartFile file;
    @NotBlank(message = "Description is required")
    private String description;

}
