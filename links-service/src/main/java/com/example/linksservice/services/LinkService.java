package com.example.linksservice.services;

import com.example.linksservice.entities.Link;
import com.example.linksservice.repositories.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository repository;

    public ResponseEntity<List<Link>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<String> store(Link link) {
        repository.save(link);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<String> destroy(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
