package com.example.boda_server.domain.bookmark.entity;

import com.example.boda_server.domain.spot.entity.Spot;
import com.example.boda_server.global.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_folder_id")
    private BookmarkFolder bookmarkFolder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @Builder
    public Bookmark(BookmarkFolder bookmarkFolder, Spot spot) {
        this.bookmarkFolder = bookmarkFolder;
        this.spot = spot;
    }
}
