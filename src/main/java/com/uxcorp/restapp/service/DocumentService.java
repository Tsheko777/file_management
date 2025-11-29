package com.uxcorp.restapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.uxcorp.restapp.dto.request.UpdateDocumentNameDTO;
import com.uxcorp.restapp.dto.response.DocumentResponseDTO;
import com.uxcorp.restapp.factory.DocumentFactory;
import com.uxcorp.restapp.model.Document;

@Service
public class DocumentService {

    private DocumentFactory documentFactory;

    @Value("${app.base-url}")
    private String baseUrl;

    public DocumentService(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    public ResponseEntity<?> newDocument(MultipartFile file, String description) {
        List<Document> existingDocs = documentFactory.findByFileName(file.getOriginalFilename());
        if (!existingDocs.isEmpty()) {
            return ResponseEntity.status(422)
                    .body(Map.of("message", "File with name '" + file.getOriginalFilename() + "' already exists."));
        }

        Path uploadPath = Paths.get("upload/documents");
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String name = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(name);

            InputStream input = file.getInputStream();
            Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);

            Document doc = new Document();
            doc.setFileName(name);
            doc.setFileType(file.getContentType());
            doc.setDescription(description);
            documentFactory.save(doc);
            return ResponseEntity.status(200).body(doc);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("message", "Unable to upload document"));
        }
    }

    @SuppressWarnings("null")
    public ResponseEntity<?> updateDescription(String description, Integer id) throws IOException {
        Document doc = documentFactory.findById(id).orElse(null);
        if (doc != null) {
            doc.setDescription(description);
            documentFactory.save(doc);
            return ResponseEntity.status(200).body(Map.of("message", "Document description updated"));
        }
        return ResponseEntity.status(404).body(Map.of("message", "Document not found"));
    }

    @SuppressWarnings("null")
    public ResponseEntity<?> updateName(UpdateDocumentNameDTO document, Integer id) throws IOException {
        Document doc = documentFactory.findById(id).orElse(null);
        if (doc != null) {
            Path oldFile = Paths.get("upload/documents", doc.getFileName());
            Path newFile = Paths.get("upload/documents", document.getFileName());

            if (Files.exists(oldFile))
                Files.move(oldFile, newFile, StandardCopyOption.REPLACE_EXISTING);

            doc.setFileName(document.getFileName());
            documentFactory.save(doc);
            return ResponseEntity.status(200).body(Map.of("message", "Document name updated"));
        }
        return ResponseEntity.status(404).body(Map.of("message", "Document not found"));
    }

    public ResponseEntity<?> getDocuments() {
        List<Document> docs = documentFactory.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        if (!docs.iterator().hasNext())
            return ResponseEntity.status(404).body(Map.of("message", "No documents"));

        List<DocumentResponseDTO> response = docs.stream().map(doc -> {
            DocumentResponseDTO dto = new DocumentResponseDTO();
            dto.setId(doc.getId());
            dto.setFileName(doc.getFileName());
            dto.setFileType(doc.getFileType());
            dto.setDescription(doc.getDescription());
            dto.setCreatedAt(doc.getCreatedAt());
            dto.setUpdatedAt(doc.getUpdatedAt());
            dto.setUrl(this.baseUrl + doc.getFileName());
            return dto;
        }).toList();
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("null")
    public ResponseEntity<?> getDocument(Integer id) {
        Document doc = documentFactory.findById(id).orElse(null);
        if (doc == null)
            return ResponseEntity.status(404).body(Map.of("message", "Document not found"));
        DocumentResponseDTO response = new DocumentResponseDTO();
        response.setId(doc.getId());
        response.setFileName(doc.getFileName());
        response.setFileType(doc.getFileType());
        response.setDescription(doc.getDescription());
        response.setCreatedAt(doc.getCreatedAt());
        response.setUpdatedAt(doc.getUpdatedAt());
        response.setUrl(this.baseUrl + doc.getFileName());
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("null")
    public ResponseEntity<?> deleteDocument(Integer id) throws IOException {
        Document doc = documentFactory.findById(id).orElse(null);
        if (doc != null) {
            String file = doc.getFileName();
            Path uploadPath = Paths.get("upload/documents/" + file);
            Files.delete(uploadPath);
            documentFactory.deleteById(id);
            return ResponseEntity.status(200).body(Map.of("message", "Document deleted"));
        }
        return ResponseEntity.status(404).body(Map.of("message", "Document not found"));
    }
}
