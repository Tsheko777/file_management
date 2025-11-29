package com.uxcorp.restapp.factory;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uxcorp.restapp.model.Document;

public interface DocumentFactory extends JpaRepository<Document, Integer> {
    List<Document> findByFileName(String fileName);
}
