package com.example.linksservice.services;

import com.example.linksservice.entities.Link;
import com.example.linksservice.repositories.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository repository;

    public ResponseEntity<List<Link>> getAll() {
        List<Link> links = repository.findAll();

        if (links.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(links);
    }


    public ResponseEntity<String> setFavorite(Long linkId, boolean favorite) {
        Optional<Link> optionalLink = repository.findById(linkId);

        if (optionalLink.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Link link = optionalLink.get();
        link.setFavorite(favorite);

        repository.save(link);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> setLink(Link link) {

        if(repository.existsByUrl(link.getUrl())){
            return ResponseEntity.badRequest().body("link already exist");
        }
        repository.save(link);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<String> deleteLink(Long linkId){
        repository.deleteById(linkId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
