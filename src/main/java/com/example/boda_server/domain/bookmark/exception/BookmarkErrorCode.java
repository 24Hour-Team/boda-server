package com.example.boda_server.domain.bookmark.exception;

import com.example.boda_server.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements ErrorCode {
    BOOKMARK_NOT_FOUND(HttpStatus.BAD_REQUEST, "북마크를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}