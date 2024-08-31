package com.example.boda_server.domain.user.entity;

import com.example.boda_server.domain.bookmark.entity.BookmarkFolder;
import com.example.boda_server.domain.recommendation.entity.TourInformation;
import com.example.boda_server.domain.user.dto.request.UserPartialRequest;
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
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String email;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private AgeRange ageRange; //연령대

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<TourInformation> tourInformations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BookmarkFolder> bookmarkFolders = new ArrayList<>();

    @Builder
    public User(String nickname, String email, String profileImageUrl, AgeRange ageRange, Gender gender, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.ageRange = ageRange;
        this.gender = gender;
        this.role = role;
    }

    public void update(UserPartialRequest request) {
        if (request.getNickname() != null) {
            this.nickname = request.getNickname();
        }
        if (request.getGender() != null) {
            this.gender = request.getGender();
        }
        if (request.getAgeRange() != null) {
            this.ageRange = request.getAgeRange();
        }
    }
}
