package com.example.boda_server.domain.user.service;

import com.example.boda_server.domain.user.dto.request.UserPartialRequest;
import com.example.boda_server.domain.user.dto.response.UserResponse;
import com.example.boda_server.domain.user.entity.AgeRange;
import com.example.boda_server.domain.user.entity.Gender;
import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.exception.UserErrorCode;
import com.example.boda_server.domain.user.exception.UserException;
import com.example.boda_server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final String email = "test@email.com";
    private User user;


    @BeforeEach
    void setUp() {
        user = com.example.boda_server.domain.user.entity.User.builder()
                .email("test@email.com")
                .nickname("nickname")
                .gender(Gender.FEMALE)
                .ageRange(AgeRange.TWENTIES)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    }

    @Test
    public void 회원_조회_성공() {
        UserResponse result = userService.getUser(email);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getNickname()).isEqualTo("nickname");
        assertThat(result.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(result.getAgeRange()).isEqualTo(AgeRange.TWENTIES);

        verify(userRepository, times(1)).findByEmail(email);
    }


    @Test
    public void 회원_조회_실패() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> userService.getUser(email));
        assertEquals(UserErrorCode.USER_NOT_FOUND, exception.getErrorCode());

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void 회원정보_수정_성공(){
        UserPartialRequest request = new UserPartialRequest("newNickname", Gender.MALE, AgeRange.THIRTIES);

        User updatedUser = User.builder()
                .nickname("newNickname")
                .gender(Gender.MALE)
                .ageRange(AgeRange.THIRTIES)
                .build();

        when(userRepository.save(user)).thenReturn(updatedUser);

        UserResponse result = userService.updateUser(email, request);


        assertThat(result.getNickname()).isEqualTo("newNickname");
        assertThat(result.getAgeRange()).isEqualTo(AgeRange.THIRTIES);
        assertThat(result.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    public void 회원정보_수정_실패() {
        UserPartialRequest request = new UserPartialRequest("newNickname", Gender.MALE, AgeRange.THIRTIES);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> userService.updateUser(email, request));
        assertEquals(UserErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

}
