package com.example.boda_server.domain.bookmark.exception;

import com.example.boda_server.global.exception.BusinessException;
import lombok.Getter;

@Getter
public class BookmarkException extends BusinessException {

    private final BookmarkErrorCode bookmarkErrorCode;

    public BookmarkException(BookmarkErrorCode bookmarkErrorCode) {
        super(bookmarkErrorCode);
        this.bookmarkErrorCode = bookmarkErrorCode;
    }
}
