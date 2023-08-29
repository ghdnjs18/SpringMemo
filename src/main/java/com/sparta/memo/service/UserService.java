package com.sparta.memo.service;

import com.sparta.memo.dto.SignupRequestDto;
import com.sparta.memo.entity.User;
import com.sparta.memo.repository.UserRepository;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        if (username.matches("[a-z0-9]{4,10}")) {
            // 회원 중복 확인
            Optional<User> checkUsername = userRepository.findByUsername(username);
            if (checkUsername.isPresent()) { // 포함 하고 있는지 아닌지
                throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
            }
        } else {
            throw new IllegalArgumentException("username형식이 맞지않습니다.");
        }

        if (!requestDto.getPassword().matches("[a-zA-Z0-9]{8,15}")) {
            throw new IllegalArgumentException("password형식이 맞지않습니다.");
        }

        // 사용자 등록
        User user = new User(username, password);
        userRepository.save(user);
    }
}