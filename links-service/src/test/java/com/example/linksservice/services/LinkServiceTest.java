package com.example.linksservice.services;

import com.example.linksservice.entities.Link;
import com.example.linksservice.repositories.LinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {
    @Mock
    private LinkRepository linkRepository;
    @InjectMocks
    private LinkService linkService;


    @Test
    void testGetAll() {
        List<Link> links = List.of(new Link(1L, "www.google.com", true));

        when(linkRepository.findAll()).thenReturn(links);

        ResponseEntity<List<Link>> responseEntity = linkService.getAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(links, responseEntity.getBody());
    }

    @Test
    void testGetAllEmpty() {
        when(linkRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Link>> responseEntity = linkService.getAll();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testSetFavorite() {
        when(linkRepository.findById(1L)).thenReturn(Optional.of(new Link(1L, "www.google.com", true)));
        assertEquals(ResponseEntity.ok().build(), linkService.setFavorite(1L, false));
    }

    @Test
    void testSetFavoriteOfNotExists() {
        assertEquals(ResponseEntity.notFound().build(), linkService.setFavorite(1L, false));
    }
}