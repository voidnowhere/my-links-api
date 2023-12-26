package com.example.linksservice.repositories;

import com.example.linksservice.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

    boolean existsByUrl(String url);
}
