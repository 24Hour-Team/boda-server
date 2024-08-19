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
import com.example.boda_server.domain.recommendation.exception.RecommendationErrorCode;
import com.example.boda_server.domain.recommendation.exception.RecommendationException;
import com.example.boda_server.domain.recommendation.repository.SpotRepository;
import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.exception.UserErrorCode;
import com.example.boda_server.domain.user.exception.UserException;
import com.example.boda_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkFolderRepository bookmarkFolderRepository;
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;

    /*
    북마크 폴더 생성 로직(유저당 최대 10개 제한)
     */
    public BookmarkFolderResponse createBookmarkFolder(BookmarkFolderCreateRequest request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        //북마크 폴더가 10개 이상인지 검사
        validateBookmarkFolderLimit(user);

        return BookmarkFolderResponse.builder()
                .bookmarkFolder(bookmarkFolderRepository.save(BookmarkFolder.builder()
                        .name(request.getName())
                        .user(user)
                        .build()))
                .build();
    }

    /*
    북마크 폴더 리스트 조회 로직
     */
    public List<BookmarkFolderResponse> getBookmarkFolders(String email) {
        return bookmarkFolderRepository.findByUser(userRepository.findByEmail(email).orElseThrow(
                        () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                )).stream()
                .map(BookmarkFolderResponse::new)
                .toList();
    }

    /*
    북마크 폴더 삭제 로직
     */
    public void deleteBookmarkFolder(Long bookmarkFolderId, String email) {
        BookmarkFolder bookmarkFolder = bookmarkFolderRepository.findById(bookmarkFolderId).orElseThrow(
                () -> new BookmarkException(BookmarkErrorCode.BOOKMARK_FOLDER_NOT_FOUND)
        );

        // 해당 북마크 폴더가 유저의 소유가 맞는지 검사
        validateUserAccess(bookmarkFolder, email);

        bookmarkFolderRepository.delete(bookmarkFolder);
    }

    /*
    북마크 생성 로직(폴더당 최대 20개 제한)
     */
    public BookmarkResponse createBookmark(Long bookmarkFolderId, Long spotId, String email) {
        BookmarkFolder bookmarkFolder = bookmarkFolderRepository.findById(bookmarkFolderId).orElseThrow(
                () -> new BookmarkException(BookmarkErrorCode.BOOKMARK_FOLDER_NOT_FOUND)
        );
        Spot spot = spotRepository.findById(spotId).orElseThrow(
                () -> new RecommendationException(RecommendationErrorCode.SPOT_NOT_FOUND)
        );

        // 해당 북마크 폴더가 유저의 소유가 맞는지 검사
        validateUserAccess(bookmarkFolder, email);

        // 해당 폴더에 북마크가 20개 이상인지 검사
        validateBookmarkLimit(bookmarkFolder);

        // 해당 폴더에 이미 해당 여행지 북마크가 있는지 검사
        validateDuplicateBookmark(bookmarkFolder, spot);

        return BookmarkResponse.builder()
                .bookmark(bookmarkFolder.addBookmark(bookmarkRepository.save(Bookmark.builder()
                        .bookmarkFolder(bookmarkFolder)
                        .spot(spot)
                        .build())))
                .build();
    }

    /*
    북마크 리스트 조회 로직
     */
    public List<BookmarkResponse> getBookmarks(Long bookmarkFolderId, String email) {
        BookmarkFolder bookmarkFolder = bookmarkFolderRepository.findById(bookmarkFolderId).orElseThrow(
                () -> new BookmarkException(BookmarkErrorCode.BOOKMARK_FOLDER_NOT_FOUND)
        );

        // 해당 북마크 폴더가 유저의 소유가 맞는지 검사
        validateUserAccess(bookmarkFolder, email);

        // 페치 조인을 사용하여 북마크 폴더에 포함된 북마크들과 관련된 여행지를 함께 조회
        return bookmarkRepository.findBookmarksByFolderWithSpot(bookmarkFolder).stream()
                .map(BookmarkResponse::new)
                .toList();
    }

    /*
    북마크 삭제 로직
     */
    public void deleteBookmark(Long bookmarkId, String email) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(
                () -> new BookmarkException(BookmarkErrorCode.BOOKMARK_NOT_FOUND)
        );

        // 해당 북마크 폴더가 유저의 소유가 맞는지 검사
        validateUserAccess(bookmark.getBookmarkFolder(), email);

        bookmarkRepository.delete(bookmark);
    }

    // 북마크 폴더 개수 제한 검증
    private void validateBookmarkFolderLimit(User user) {
        if (bookmarkFolderRepository.countByUser(user) >= 10) {
            throw new BookmarkException(BookmarkErrorCode.CANNOT_CREATE_BOOKMARK_FOLDER);
        }
    }

    // 사용자 접근 권한 검증
    private void validateUserAccess(BookmarkFolder bookmarkFolder, String email) {
        if (!bookmarkFolder.getUser().getEmail().equals(email)) {
            throw new BookmarkException(BookmarkErrorCode.CANNOT_ACCESS_BOOKMARK_FOLDER);
        }
    }

    // 북마크 개수 제한 검증
    private void validateBookmarkLimit(BookmarkFolder bookmarkFolder) {
        if (bookmarkRepository.countByBookmarkFolder(bookmarkFolder) >= 20) {
            throw new BookmarkException(BookmarkErrorCode.CANNOT_CREATE_BOOKMARK);
        }
    }

    // 중복된 북마크 검증
    private void validateDuplicateBookmark(BookmarkFolder bookmarkFolder, Spot spot) {
        if (bookmarkRepository.findByBookmarkFolderAndSpot(bookmarkFolder, spot).isPresent()) {
            throw new BookmarkException(BookmarkErrorCode.BOOKMARK_ALREADY_IN_FOLDER);
        }
    }
}
