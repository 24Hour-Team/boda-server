package com.example.boda_server.domain.bookmark.dto.response;

import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkFolderResponse {
    private Long id;
    private String name;
    private LocalDateTime createdDateTime;

    @Builder
    public BookmarkFolderResponse(BookmarkFolder bookmarkFolder) {
        this.id = bookmarkFolder.getId();
        this.name = bookmarkFolder.getName();
        this.createdDateTime = bookmarkFolder.getCreatedDateTime();
    }
}
