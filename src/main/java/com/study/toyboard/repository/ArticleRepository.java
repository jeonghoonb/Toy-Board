package com.study.toyboard.repository;

import com.study.toyboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
    @Repository 어노테이션 붙이지 않는다.
    JpaRepository 구현체인 SimpleJpaRepository에 붙어 있다.

    그리고 일반적으로 StereoType 어노테이션은 Interface가 아니라 구현 Class에 붙인다.
 */
@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
