package com.study.toyboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Spring Data Rest가 자동으로 생성해주기에, 이 테스트 통합 테스트는 사실상 불필요. 그냥 스터디용도로 진행했기에 Disable")
@DisplayName("Spring Data Rest 테스트")
@Transactional  // NOTE: @SpringBootTest는 Integration Test라서 실제 DB에 영향을 준다. 그렇기에 @Transactional 추가 (Test 에서 동작하는 Transactional은 Default Rollback)
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("게시글 리스트 조회 테스트")
    @Test
    void given_whenRequestArticles_thenReturnArticles() throws Exception {
        // given

        // when & then
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json ")));
    }
}
