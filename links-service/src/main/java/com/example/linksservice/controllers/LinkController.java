package com.example.linksservice.controllers;

import com.example.linksservice.entities.Link;
import com.example.linksservice.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    @PostMapping("")
    public ResponseEntity<String> saveLink(@RequestBody Link link) {

        return service.setLink(link);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLink(@PathVariable Long id){
        return service.deleteLink(id);
    }
}
