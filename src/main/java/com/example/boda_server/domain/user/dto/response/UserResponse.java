package com.example.boda_server.domain.user.dto.response;

import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import com.example.boda_server.domain.recommendation.entity.TourInformation;
import com.example.boda_server.domain.user.entity.AgeRange;
import com.example.boda_server.domain.user.entity.Gender;
import com.example.boda_server.domain.user.entity.Role;
import com.example.boda_server.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class UserResponse {
    private String nickname;
    private String email;
    private String profileImageUrl;
    private AgeRange ageRange;
    private Gender gender;
    private Role role;
    private List<TourInformation> tourInformations;
    private List<BookmarkFolder> bookmarkFolders;

    @Builder
    public  UserResponse(User user){
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
        this.ageRange = user.getAgeRange();
        this.gender = user.getGender();
        this.role = user.getRole();
        this.tourInformations = user.getTourInformations() != null ? user.getTourInformations() : new ArrayList<>();
        this.bookmarkFolders = user.getBookmarkFolders() != null ? user.getBookmarkFolders() : new ArrayList<>();
    }
}
