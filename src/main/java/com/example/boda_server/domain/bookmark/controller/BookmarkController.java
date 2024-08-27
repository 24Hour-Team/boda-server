package com.example.boda_server.domain.bookmark.controller;

import com.example.boda_server.domain.auth.dto.CustomOAuth2User;
import com.example.boda_server.domain.bookmark.dto.request.BookmarkFolderCreateRequest;
import com.example.boda_server.domain.bookmark.dto.response.BookmarkDetailResponse;
import com.example.boda_server.domain.bookmark.dto.response.BookmarkFolderResponse;
import com.example.boda_server.domain.bookmark.dto.response.BookmarkResponse;
import com.example.boda_server.domain.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
추후에 email 받아오는 방식 수정
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/folder")
    public ResponseEntity<BookmarkFolderResponse> createBookmarkFolder(
            @RequestBody BookmarkFolderCreateRequest bookmarkFolderCreateRequest,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
            ){
        return ResponseEntity.ok()
                .body(bookmarkService.createBookmarkFolder(bookmarkFolderCreateRequest, customOAuth2User.getUser().getEmail()));
    }

    @GetMapping("/folder/list")
    public ResponseEntity<List<BookmarkFolderResponse>> getBookmarkFolders(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ){
        return ResponseEntity.ok()
                .body(bookmarkService.getBookmarkFolders(customOAuth2User.getUser().getEmail()));
    }

    @DeleteMapping("/folder/{bookmarkFolderId}")
    public ResponseEntity<Void> deleteBookmarkFolder(
            @PathVariable("bookmarkFolderId") Long bookmarkFolderId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ){
        bookmarkService.deleteBookmarkFolder(bookmarkFolderId, customOAuth2User.getUser().getEmail());
        return ResponseEntity.noContent().build(); //상태 코드 204 반환
    }

    @GetMapping("/{bookmarkFolderId}/{spotId}")
    public ResponseEntity<BookmarkResponse> createBookmark(
            @PathVariable("bookmarkFolderId") Long bookmarkFolderId,
            @PathVariable("spotId") Long spotId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ){
        return ResponseEntity.ok()
                .body(bookmarkService.createBookmark(bookmarkFolderId, spotId, customOAuth2User.getUser().getEmail()));
    }

    @GetMapping("/{bookmarkFolderId}")
    public ResponseEntity<BookmarkDetailResponse> getBookmarks(
            @PathVariable("bookmarkFolderId") Long bookmarkFolderId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ){
        return ResponseEntity.ok()
                .body(bookmarkService.getBookmarks(bookmarkFolderId, customOAuth2User.getUser().getEmail()));
    }

    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<Void> deleteBookmark(
            @PathVariable("bookmarkId") Long bookmarkId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ){
        bookmarkService.deleteBookmark(bookmarkId, customOAuth2User.getUser().getEmail());
        return ResponseEntity.noContent().build(); //상태 코드 204 반환
    }
}
