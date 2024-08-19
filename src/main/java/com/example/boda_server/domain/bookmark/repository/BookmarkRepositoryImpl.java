package com.example.boda_server.domain.bookmark.repository;

import com.example.boda_server.domain.bookmark.entity.Bookmark;
import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.boda_server.domain.bookmark.entity.QBookmark.bookmark;

public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public BookmarkRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Bookmark> findBookmarksByFolderWithSpot(BookmarkFolder bookmarkFolder) {
        return jpaQueryFactory
                .selectFrom(bookmark)
                .join(bookmark.spot).fetchJoin()
                .where(bookmark.bookmarkFolder.eq(bookmarkFolder))
                .fetch();
    }

}
