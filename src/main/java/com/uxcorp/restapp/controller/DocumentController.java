package com.uxcorp.restapp.controller;

import java.io.IOException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uxcorp.restapp.dto.request.NewDocumentDTO;
import com.uxcorp.restapp.dto.request.UpdateDocumentDescriptionDTO;
import com.uxcorp.restapp.dto.request.UpdateDocumentNameDTO;
import com.uxcorp.restapp.service.DocumentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getDocuments() {
        return documentService.getDocuments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocuement(@PathVariable @NonNull Integer id) {
        return documentService.getDocument(id);
    }

    @PatchMapping("/update/description/{id}")
    public ResponseEntity<?> updateDescription(@PathVariable @NonNull Integer id,
            @RequestBody @Valid UpdateDocumentDescriptionDTO document)
            throws IOException {
        return documentService.updateDescription(document.getDescription(), id);
    }

    @PatchMapping("/update/name/{id}")
    public ResponseEntity<?> updateName(@PathVariable @NonNull Integer id,
            @RequestBody @Valid UpdateDocumentNameDTO doc)
            throws IOException {
        return documentService.updateName(doc, id);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newDocument(@Valid @ModelAttribute NewDocumentDTO document) {
        if (document.getFile() == null || document.getFile().isEmpty()) {
            return ResponseEntity.status(422).body(Map.of("message", "Please upload file"));
        }
        return documentService.newDocument(document.getFile(), document.getDescription());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable @NonNull Integer id) throws IOException {
        return documentService.deleteDocument(id);
    }
}
