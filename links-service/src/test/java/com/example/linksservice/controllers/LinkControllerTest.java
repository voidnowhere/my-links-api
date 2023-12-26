package com.example.linksservice.controllers;

import com.example.linksservice.entities.Link;
import com.example.linksservice.services.LinkService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(LinkController.class)
@ExtendWith(MockitoExtension.class)
class LinkControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LinkService linkService;

    @Test
    void testGetAll() throws Exception {
        Link firstLink = new Link(1L, "www.google.com", false);
        List<Link> links = List.of(firstLink);
        when(linkService.getAll()).thenReturn(ResponseEntity.ok(links));
        ResultActions resultActions = mockMvc.perform(get("/api/links"));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("size()", CoreMatchers.is(links.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(firstLink.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url", CoreMatchers.is(firstLink.getUrl())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].favorite", CoreMatchers.is(firstLink.isFavorite())));
    }

    @Test
    void testGetAllEmpty() throws Exception {
        when(linkService.getAll()).thenReturn(ResponseEntity.noContent().build());
        ResultActions resultActions = mockMvc.perform(get("/api/links"));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.is("")));
    }

    @Test
    void setFavorite() throws Exception {
        when(linkService.setFavorite(1L, true)).thenReturn(ResponseEntity.ok().build());
        ResultActions resultActions = mockMvc.perform(
                put("/api/links/1/set-favorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true")
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void setFavoriteOfNotExist() throws Exception {
        when(linkService.setFavorite(1L, true)).thenReturn(ResponseEntity.notFound().build());
        ResultActions resultActions = mockMvc.perform(
                put("/api/links/1/set-favorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true")
        );

        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}