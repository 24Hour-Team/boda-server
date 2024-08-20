package com.example.boda_server.domain.bookmark.repository;

import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import com.example.boda_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
    Long countByUser(User user);
    List<BookmarkFolder> findByUser(User user);
}
