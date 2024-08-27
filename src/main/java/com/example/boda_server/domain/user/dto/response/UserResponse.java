package com.example.boda_server.domain.user.dto.response;

import com.example.boda_server.domain.user.entity.AgeRange;
import com.example.boda_server.domain.user.entity.Gender;
import com.example.boda_server.domain.user.entity.Role;
import com.example.boda_server.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserResponse {
    private String nickname;
    private String email;
    private String profileImageUrl;
    private AgeRange ageRange;
    private Gender gender;
    private Role role;

    @Builder
    public  UserResponse(User user){
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
        this.ageRange = user.getAgeRange();
        this.gender = user.getGender();
        this.role = user.getRole();
    }
}
