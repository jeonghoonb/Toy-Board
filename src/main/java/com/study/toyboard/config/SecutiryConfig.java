package com.study.toyboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecutiryConfig {

    // NOTE: https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter 참고
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())   // 임시로 모든 요청 허용 (로그인 페이지 by pass)
                .formLogin().and()
                .build();
    }
}
