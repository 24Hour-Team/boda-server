package com.example.boda_server.domain.bookmark.entity;

import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.global.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkFolder extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "bookmark_folder_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "bookmarkFolder")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Builder
    public BookmarkFolder(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
