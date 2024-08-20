package com.example.boda_server.domain.bookmark.repository;

import com.example.boda_server.domain.bookmark.entity.Bookmark;
import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import com.example.boda_server.domain.recommendation.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {
    Long countByBookmarkFolder(BookmarkFolder bookmarkFolder);
    Optional<Bookmark> findByBookmarkFolderAndSpot(BookmarkFolder bookmarkFolder, Spot spot);
}
