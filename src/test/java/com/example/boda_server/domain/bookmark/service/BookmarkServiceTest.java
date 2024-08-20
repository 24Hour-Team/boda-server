package com.example.boda_server.domain.bookmark.service;

import com.example.boda_server.domain.bookmark.dto.request.BookmarkFolderCreateRequest;
import com.example.boda_server.domain.bookmark.dto.response.BookmarkFolderResponse;
import com.example.boda_server.domain.bookmark.dto.response.BookmarkResponse;
import com.example.boda_server.domain.bookmark.entity.Bookmark;
import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import com.example.boda_server.domain.bookmark.exception.BookmarkErrorCode;
import com.example.boda_server.domain.bookmark.exception.BookmarkException;
import com.example.boda_server.domain.bookmark.repository.BookmarkFolderRepository;
import com.example.boda_server.domain.bookmark.repository.BookmarkRepository;
import com.example.boda_server.domain.recommendation.entity.Spot;
import com.example.boda_server.domain.recommendation.repository.SpotRepository;
import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private BookmarkFolderRepository bookmarkFolderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpotRepository spotRepository;

    @InjectMocks
    private BookmarkService bookmarkService;

    private User user;
    private BookmarkFolder bookmarkFolder;
    private Spot spot;
    private Bookmark bookmark;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("test@example.com")
                .build();

        bookmarkFolder = BookmarkFolder.builder()
                .name("Test Folder")
                .user(user)
                .build();

        spot = Spot.builder().build();

        bookmark = Bookmark.builder()
                .bookmarkFolder(bookmarkFolder)
                .spot(spot)
                .build();
    }

    @Test
    @DisplayName("북마크 폴더 생성 성공")
    void createBookmarkFolder_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(bookmarkFolderRepository.countByUser(any(User.class))).thenReturn(5L);
        when(bookmarkFolderRepository.save(any(BookmarkFolder.class))).thenReturn(bookmarkFolder);

        BookmarkFolderResponse response = bookmarkService.createBookmarkFolder(new BookmarkFolderCreateRequest("New Folder"), user.getEmail());

        assertNotNull(response);
        assertEquals("Test Folder", response.getName());

        verify(bookmarkFolderRepository, times(1)).save(any(BookmarkFolder.class));
    }

    @Test
    @DisplayName("북마크 폴더 생성 실패 - 폴더 개수 초과")
    void createBookmarkFolder_LimitExceeded() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(bookmarkFolderRepository.countByUser(any(User.class))).thenReturn(10L);

        BookmarkException exception = assertThrows(BookmarkException.class, () ->
                bookmarkService.createBookmarkFolder(new BookmarkFolderCreateRequest("New Folder"), user.getEmail()));

        assertEquals(BookmarkErrorCode.BOOKMARK_FOLDER_CREATION_LIMIT_EXCEEDED, exception.getErrorCode());

        verify(bookmarkFolderRepository, never()).save(any(BookmarkFolder.class));
    }

    @Test
    @DisplayName("북마크 폴더 삭제 성공")
    void deleteBookmarkFolder_Success() {
        when(bookmarkFolderRepository.findById(anyLong())).thenReturn(Optional.of(bookmarkFolder));

        bookmarkService.deleteBookmarkFolder(1L, user.getEmail());

        verify(bookmarkFolderRepository, times(1)).delete(any(BookmarkFolder.class));
    }

    @Test
    @DisplayName("북마크 폴더 삭제 실패 - 폴더를 찾을 수 없음")
    void deleteBookmarkFolder_NotFound() {
        when(bookmarkFolderRepository.findById(anyLong())).thenReturn(Optional.empty());

        BookmarkException exception = assertThrows(BookmarkException.class, () ->
                bookmarkService.deleteBookmarkFolder(1L, user.getEmail()));

        assertEquals(BookmarkErrorCode.BOOKMARK_FOLDER_NOT_FOUND, exception.getErrorCode());

        verify(bookmarkFolderRepository, never()).delete(any(BookmarkFolder.class));
    }

    @Test
    @DisplayName("북마크 생성 성공")
    void createBookmark_Success() {
        when(bookmarkFolderRepository.findById(anyLong())).thenReturn(Optional.of(bookmarkFolder));
        when(spotRepository.findById(anyLong())).thenReturn(Optional.of(spot));
        when(bookmarkRepository.countByBookmarkFolder(any(BookmarkFolder.class))).thenReturn(10L);
        when(bookmarkRepository.findByBookmarkFolderAndSpot(any(BookmarkFolder.class), any(Spot.class))).thenReturn(Optional.empty());
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);

        BookmarkResponse response = bookmarkService.createBookmark(1L, 1L, user.getEmail());

        assertNotNull(response);
        verify(bookmarkRepository, times(1)).save(any(Bookmark.class));
    }

    @Test
    @DisplayName("북마크 생성 실패 - 폴더에 북마크 개수 초과")
    void createBookmark_LimitExceeded() {
        when(bookmarkFolderRepository.findById(anyLong())).thenReturn(Optional.of(bookmarkFolder));
        when(spotRepository.findById(anyLong())).thenReturn(Optional.of(spot));
        when(bookmarkRepository.countByBookmarkFolder(any(BookmarkFolder.class))).thenReturn(20L);

        BookmarkException exception = assertThrows(BookmarkException.class, () ->
                bookmarkService.createBookmark(1L, 1L, user.getEmail()));

        assertEquals(BookmarkErrorCode.BOOKMARK_CREATION_LIMIT_EXCEEDED, exception.getErrorCode());

        verify(bookmarkRepository, never()).save(any(Bookmark.class));
    }

    @Test
    @DisplayName("북마크 리스트 조회 성공")
    void getBookmarks_Success() {
        when(bookmarkFolderRepository.findById(anyLong())).thenReturn(Optional.of(bookmarkFolder));
        when(bookmarkRepository.findBookmarksByFolderWithSpot(any(BookmarkFolder.class))).thenReturn(List.of(bookmark));

        List<BookmarkResponse> responses = bookmarkService.getBookmarks(1L, user.getEmail());

        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
    }

    @Test
    @DisplayName("북마크 삭제 성공")
    void deleteBookmark_Success() {
        when(bookmarkRepository.findById(anyLong())).thenReturn(Optional.of(bookmark));

        bookmarkService.deleteBookmark(1L, user.getEmail());

        verify(bookmarkRepository, times(1)).delete(any(Bookmark.class));
    }

    @Test
    @DisplayName("북마크 삭제 실패 - 북마크를 찾을 수 없음")
    void deleteBookmark_NotFound() {
        when(bookmarkRepository.findById(anyLong())).thenReturn(Optional.empty());

        BookmarkException exception = assertThrows(BookmarkException.class, () ->
                bookmarkService.deleteBookmark(1L, user.getEmail()));

        assertEquals(BookmarkErrorCode.BOOKMARK_NOT_FOUND, exception.getErrorCode());

        verify(bookmarkRepository, never()).delete(any(Bookmark.class));
    }
}
