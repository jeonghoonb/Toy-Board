package com.study.toyboard.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.study.toyboard.domain.Article;
import com.study.toyboard.domain.QArticle;
import com.study.toyboard.repository.querydsl.ArticleRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
    @Repository 어노테이션 붙이지 않는다.
    JpaRepository 구현체인 SimpleJpaRepository에 붙어 있다.

    그리고 일반적으로 StereoType 어노테이션은 Interface가 아니라 구현 Class에 붙인다.
 */
@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        ArticleRepositoryCustom,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle>
{

    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String content, Pageable pageable);
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);
    Page<Article> findByHashtag(String hashtag, Pageable pageable);

    void deleteByIdAndUserAccount_UserId(Long articleId, String userid);

    // NOTE: java 8 이후 Interface에 default method 가능, 별도 Repository 구현체 만들지 않고 Spring Data JPA를 이용
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true);   // listing 하지 않은 컬럼에 대해 검색 결과 제외
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase);  // like '${param}'  -> % 개발자가 추가해야
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);   // like '%${param}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);   // like '%${param}%'
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);   // like '%${param}%'
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
