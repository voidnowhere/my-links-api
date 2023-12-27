package com.example.linksservice.services;

import com.example.linksservice.dtos.LinkDTO;
import com.example.linksservice.entities.Link;
import com.example.linksservice.repositories.LinkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository repository;

    public ResponseEntity<List<LinkDTO>> getAllByUserId(UUID userId) {
        List<Link> links = repository.findAllByUserId(userId);

        if (links.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(links.stream()
                .map(l -> new LinkDTO(l.getId(), l.getUrl(), l.isFavorite())).toList()
        );
    }


    public ResponseEntity<String> setFavorite(Long linkId, UUID userId, boolean favorite) {
        Optional<Link> optionalLink = repository.findByIdAndUserId(linkId, userId);

        if (optionalLink.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Link link = optionalLink.get();
        link.setFavorite(favorite);

        repository.save(link);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> setLink(Link link) {
        if (repository.existsByUserIdAndUrl(link.getUserId(), link.getUrl())) {
            return ResponseEntity.badRequest().body("link already exist");
        }
        repository.save(link);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public ResponseEntity<String> deleteLink(Long linkId, UUID userId) {
        repository.deleteByIdAndUserId(linkId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
