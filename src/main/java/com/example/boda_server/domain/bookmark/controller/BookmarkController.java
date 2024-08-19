package com.example.boda_server.domain.bookmark.controller;

import com.example.boda_server.domain.bookmark.dto.request.BookmarkFolderCreateRequest;
import com.example.boda_server.domain.bookmark.dto.response.BookmarkFolderResponse;
import com.example.boda_server.domain.bookmark.dto.response.BookmarkResponse;
import com.example.boda_server.domain.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
추후에 email 받아오는 방식 수정
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/folder/create/{email}")
    public ResponseEntity<BookmarkFolderResponse> createBookmarkFolder(
            @RequestBody BookmarkFolderCreateRequest bookmarkFolderCreateRequest,
            @PathVariable("email") String email
            ){
        return ResponseEntity.ok()
                .body(bookmarkService.createBookmarkFolder(bookmarkFolderCreateRequest, email));
    }

    @GetMapping("/folder/{email}")
    public ResponseEntity<List<BookmarkFolderResponse>> getBookmarkFolders(
            @PathVariable("email") String email
    ){
        return ResponseEntity.ok()
                .body(bookmarkService.getBookmarkFolders(email));
    }

    @DeleteMapping("/folder/{bookmarkFolderId}/{email}")
    public ResponseEntity<Void> deleteBookmarkFolder(
            @PathVariable("bookmarkFolderId") Long bookmarkFolderId,
            @PathVariable("email") String email
    ){
        bookmarkService.deleteBookmarkFolder(bookmarkFolderId, email);
        return ResponseEntity.noContent().build(); //상태 코드 204 반환
    }

    @GetMapping("/create/{bookmarkFolderId}/{spotId}/{email}")
    public ResponseEntity<BookmarkResponse> createBookmark(
            @PathVariable("bookmarkFolderId") Long bookmarkFolderId,
            @PathVariable("spotId") Long spotId,
            @PathVariable("email") String email
    ){
        return ResponseEntity.ok()
                .body(bookmarkService.createBookmark(bookmarkFolderId, spotId, email));
    }

    @GetMapping("/{bookmarkFolderId}/{email}")
    public ResponseEntity<List<BookmarkResponse>> getBookmarks(
            @PathVariable("bookmarkFolderId") Long bookmarkFolderId,
            @PathVariable("email") String email
    ){
        return ResponseEntity.ok()
                .body(bookmarkService.getBookmarks(bookmarkFolderId, email));
    }

    @DeleteMapping("/{bookmarkId}/{email}")
    public ResponseEntity<Void> deleteBookmark(
            @PathVariable("bookmarkId") Long bookmarkId,
            @PathVariable("email") String email
    ){
        bookmarkService.deleteBookmark(bookmarkId, email);
        return ResponseEntity.noContent().build(); //상태 코드 204 반환
    }
}
