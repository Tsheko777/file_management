package com.uxcorp.restapp.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class DocumentResponseDTO {
    private Integer id;
    private String fileType;
    private String fileName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String url;
}
