package com.example.boda_server.domain.bookmark.repository;

import com.example.boda_server.domain.bookmark.entity.Bookmark;
import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;

import java.util.List;

public interface BookmarkRepositoryCustom {
    List<Bookmark> findBookmarksByFolderWithSpot(BookmarkFolder bookmarkFolder);
}
