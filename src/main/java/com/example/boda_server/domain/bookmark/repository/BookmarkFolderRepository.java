package com.example.boda_server.domain.bookmark.repository;

import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
}
