package com.example.boda_server.domain.bookmark.repository;

import com.example.boda_server.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRespository extends JpaRepository<Bookmark, Long> {
}
