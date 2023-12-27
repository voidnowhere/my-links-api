package com.example.linksservice.controllers;

import com.example.linksservice.dtos.LinkDTO;
import com.example.linksservice.entities.Link;
import com.example.linksservice.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService service;

    @GetMapping
    public ResponseEntity<List<LinkDTO>> getAll(@RequestHeader("User-Id") UUID userId) {
        return service.getAllByUserId(userId);
    }


    @PutMapping("/{id}/set-favorite")
    public ResponseEntity<String> setFavorite(
            @PathVariable Long id,
            @RequestBody boolean favorite,
            @RequestHeader("User-Id") UUID userId
    ) {
        return service.setFavorite(id, userId, favorite);
    }

    @PostMapping
    public ResponseEntity<String> saveLink(
            @RequestHeader("User-Id") UUID userId,
            @RequestBody LinkDTO linkDTO
    ) {
        return service.setLink(new Link(linkDTO.getId(), userId, linkDTO.getUrl(), linkDTO.isFavorite()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLink(
            @PathVariable Long id,
            @RequestHeader("User-Id") UUID userId
    ) {
        return service.deleteLink(id, userId);
    }
}
