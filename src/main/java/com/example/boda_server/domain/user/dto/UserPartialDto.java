package com.example.boda_server.domain.user.dto;

import com.example.boda_server.domain.user.entity.AgeRange;
import com.example.boda_server.domain.user.entity.Gender;
import lombok.Data;
import lombok.Getter;

/**
 * 회원가입시 추가정보 입력
 */
@Data
@Getter
public class UserPartialDto {
    // 닉네임, 성별, 연령대, //역할
    private String nickname;
    private Gender gender;
    private AgeRange ageRange;
}
