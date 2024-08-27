package com.example.boda_server.domain.bookmark.dto.response;

import com.example.boda_server.domain.bookmark.entity.Bookmark;
import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkDetailResponse {
    private Long id;
    private String name;
    private LocalDateTime createdDateTime;
    private List<BookmarkResponse> bookmarks;

    @Builder
    public BookmarkDetailResponse(BookmarkFolder bookmarkFolder, List<Bookmark> bookmarks) {
        this.id = bookmarkFolder.getId();
        this.name = bookmarkFolder.getName();
        this.createdDateTime = bookmarkFolder.getCreatedDateTime();
        this.bookmarks = bookmarks.stream().map(BookmarkResponse::new).toList();
    }
}
