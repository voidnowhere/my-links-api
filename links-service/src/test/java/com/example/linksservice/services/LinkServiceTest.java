package com.example.linksservice.services;

import com.example.linksservice.dtos.LinkDTO;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {
    @Mock
    private LinkRepository linkRepository;
    @InjectMocks
    private LinkService linkService;


    @Test
    void testGetAll() {
        UUID userId = UUID.randomUUID();
        List<Link> links = List.of(new Link(1L, userId, "www.google.com", true));

        when(linkRepository.findAllByUserId(userId)).thenReturn(links);

        ResponseEntity<List<LinkDTO>> responseEntity = linkService.getAllByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<LinkDTO> expectedLinks = links.stream()
                .map(l -> new LinkDTO(l.getId(), l.getUrl(), l.isFavorite())).toList();
        List<LinkDTO> actualLinks = responseEntity.getBody();

        assertNotNull(actualLinks);
        for (int i = 0, s = expectedLinks.size(); i < s; i++) {
            assertEquals(expectedLinks.get(i).getId(), actualLinks.get(i).getId());
            assertEquals(expectedLinks.get(i).getUrl(), actualLinks.get(i).getUrl());
            assertEquals(expectedLinks.get(i).isFavorite(), actualLinks.get(i).isFavorite());
        }
    }

    @Test
    void testGetAllEmpty() {
        UUID userId = UUID.randomUUID();
        when(linkRepository.findAllByUserId(userId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<LinkDTO>> responseEntity = linkService.getAllByUserId(userId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testSetFavorite() {
        UUID userId = UUID.randomUUID();
        when(linkRepository.findByIdAndUserId(1L, userId))
                .thenReturn(Optional.of(new Link(1L, userId, "www.google.com", true)));
        assertEquals(ResponseEntity.ok().build(), linkService.setFavorite(1L, userId, false));
    }

    @Test
    void testSetFavoriteOfNotExists() {
        assertEquals(ResponseEntity.notFound().build(), linkService.setFavorite(1L, UUID.randomUUID(), false));
    }
}