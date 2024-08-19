package com.example.boda_server.domain.bookmark.exception;

import com.example.boda_server.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements ErrorCode {
    BOOKMARK_NOT_FOUND(HttpStatus.BAD_REQUEST, "북마크를 찾을 수 없습니다."),
    BOOKMARK_CREATION_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "북마크를 생성할 수 없습니다. 폴더에 이미 20개의 북마크가 있습니다."),
    BOOKMARK_ALREADY_EXISTS_IN_FOLDER(HttpStatus.BAD_REQUEST, "해당 북마크가 이미 폴더에 있습니다."),
    BOOKMARK_FOLDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "북마크 폴더를 찾을 수 없습니다."),
    BOOKMARK_FOLDER_CREATION_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "북마크 폴더를 생성할 수 없습니다. 유저당 최대 10개의 폴더만 생성할 수 있습니다."),
    UNAUTHORIZED_BOOKMARK_FOLDER_ACCESS(HttpStatus.FORBIDDEN, "해당 북마크 폴더에 접근할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}