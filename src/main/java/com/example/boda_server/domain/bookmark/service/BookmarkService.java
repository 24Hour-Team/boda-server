package com.example.boda_server.domain.bookmark.service;

import com.example.boda_server.domain.bookmark.dto.request.BookmarkFolderCreateRequest;
import com.example.boda_server.domain.bookmark.dto.response.BookmarkFolderResponse;
import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import com.example.boda_server.domain.bookmark.exception.BookmarkErrorCode;
import com.example.boda_server.domain.bookmark.exception.BookmarkException;
import com.example.boda_server.domain.bookmark.repository.BookmarkFolderRepository;
import com.example.boda_server.domain.bookmark.repository.BookmarkRespository;
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

    private final BookmarkRespository bookmarkRespository;
    private final BookmarkFolderRepository bookmarkFolderRepository;
    private final UserRepository userRepository;

    /*
    북마크 폴더 생성 로직(유저당 최대 10개 제한)
     */
    public BookmarkFolderResponse createBookmarkFolder(BookmarkFolderCreateRequest request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
        //북마크 폴더가 10개 이상인 경우
        if(bookmarkFolderRepository.countByUser(user) >= 10){
            throw new BookmarkException(BookmarkErrorCode.CANNOT_CREATE_BOOKMARK_FOLDER);
        }

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
        if (!bookmarkFolder.getUser().getEmail().equals(email)){
            throw new BookmarkException(BookmarkErrorCode.CANNOT_DELETE_BOOKMARK_FOLDER);
        }
        bookmarkFolderRepository.delete(bookmarkFolder);
    }
}
