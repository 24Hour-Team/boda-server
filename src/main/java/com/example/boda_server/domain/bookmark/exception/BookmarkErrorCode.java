package com.example.boda_server.domain.bookmark.exception;

import com.example.boda_server.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements ErrorCode {
    BOOKMARK_NOT_FOUND(HttpStatus.BAD_REQUEST, "북마크를 찾을 수 없습니다."),
    BOOKMARK_FOLDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "북마크 폴더를 찾을 수 없습니다."),
    CANNOT_CREATE_BOOKMARK_FOLDER(HttpStatus.BAD_REQUEST, "북마크 폴더를 생성할 수 없습니다."),
    CANNOT_DELETE_BOOKMARK_FOLDER(HttpStatus.BAD_REQUEST, "북마크 폴더를 삭제할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}