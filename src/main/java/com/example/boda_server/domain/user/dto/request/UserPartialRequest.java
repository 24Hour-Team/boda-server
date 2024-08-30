package com.example.boda_server.domain.user.dto.request;

import com.example.boda_server.domain.user.entity.AgeRange;
import com.example.boda_server.domain.user.entity.Gender;
import lombok.Data;
import lombok.Getter;

/**
 * 회원가입시 추가정보 입력
 */
@Data
@Getter
public class UserPartialRequest {

    private String nickname;
    private Gender gender;
    private AgeRange ageRange;

    public UserPartialRequest(String nickname, Gender gender, AgeRange ageRange) {
        this.nickname = nickname;
        this.gender = gender;
        this.ageRange = ageRange;
    }
}
