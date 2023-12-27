package com.example.linksservice.repositories;

import com.example.linksservice.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    boolean existsByUserIdAndUrl(UUID userId, String url);

    List<Link> findAllByUserId(UUID userId);

    Optional<Link> findByIdAndUserId(Long id, UUID userId);

    void deleteByIdAndUserId(Long id, UUID userId);
}
