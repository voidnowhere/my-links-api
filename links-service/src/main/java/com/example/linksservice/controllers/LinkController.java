package com.example.linksservice.controllers;

import com.example.linksservice.entities.Link;
import com.example.linksservice.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService service;

    @GetMapping
    public ResponseEntity<List<Link>> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}/set-favorite")
    public ResponseEntity<String> setFavorite(
            @PathVariable Long id,
            @RequestBody boolean favorite
    ) {
        return service.setFavorite(id, favorite);
    }
}
