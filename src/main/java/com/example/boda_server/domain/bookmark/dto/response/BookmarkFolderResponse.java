package com.example.boda_server.domain.bookmark.dto.response;

import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkFolderResponse {
    private Long id;
    private String name;

    @Builder
    public BookmarkFolderResponse(BookmarkFolder bookmarkFolder) {
        this.id = bookmarkFolder.getId();
        this.name = bookmarkFolder.getName();
    }
}
