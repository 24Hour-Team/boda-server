package com.example.boda_server.domain.bookmark.dto.response;

import com.example.boda_server.domain.bookmark.entity.Bookmark;
import com.example.boda_server.domain.recommendation.entity.Spot;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkResponse {
    private Long id;
    private Long spotId;
    private String name;
    private String xCoord;
    private String yCoord;
    private String address;

    @Builder
    public BookmarkResponse(Bookmark bookmark) {
        this.id = bookmark.getId();
        this.spotId = bookmark.getSpot().getId();
        this.name = bookmark.getSpot().getName();
        this.xCoord = bookmark.getSpot().getXCoord();
        this.yCoord = bookmark.getSpot().getYCoord();
        this.address = bookmark.getSpot().getAddress();
    }
}
