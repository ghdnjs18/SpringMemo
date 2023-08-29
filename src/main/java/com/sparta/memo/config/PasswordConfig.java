package com.sparta.memo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 수동으로 Bean Container에 저장한다.
public class PasswordConfig {

    @Bean // Bean으로 등록 한다
    public PasswordEncoder passwordEncoder() {
        // 해시 매커니즘으로 암호화
        return new BCryptPasswordEncoder();
    }
}