package com.example.linksservice.controllers;

import com.example.linksservice.entities.Link;
import com.example.linksservice.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService service;

    @GetMapping
    public ResponseEntity<List<Link>> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<String> store(@RequestBody Link link) {
        return service.store(link);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> destroy(@PathVariable Long id) {
        return service.destroy(id);
    }
}
