package com.study.toyboard.repository;

import com.study.toyboard.config.JpaConfig;
import com.study.toyboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class ArticleRepositoryTest {

    /*
      JUnit5 이상은 test code에서도 생성자 주입 가능 (ref: https://velog.io/@nathan29849/JUnit-Test-%EA%B5%AC%EC%A1%B0)

      @DataJpaTest > @ExtendWith(SpringExtension.class) : JUnit4에서는 RunWith 였는데 JUni5 에서는 ExtendWith 사용

      @DataJpaTest > @Transactional 메소드 단위로 트랜잭션이 걸려있다. (기본값 롤백)
      그렇기에 update 시 save()만 호출하면 적용이 되지 않는다.
      결론은 saveAndFlush 사용해야 한다. 하지만 이것도 실제 업데이트 되는 것은 아니다.

     */

    private ArticleRepository articleRepository;
    private ArticleCommentRepository articleCommentRepository;

    public ArticleRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("Select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {

        // given
        // NOTE: data.sql 에 test dumy data 추가 (https://www.mockaroo.com)

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles)
                .isNotNull()
                .hasSize(100);
    }

    @DisplayName("Insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {

        // given
        long previousCount = articleRepository.count();

        // when
        articleRepository.save(Article.of("Insert Test Title", "Insert Test Content", "#InsertTestHashtag"));

        // then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("Update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {

        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#UpdatedHahshtag";
        article.setHashtag(updatedHashtag);

        // when
//        Article savedArticle = articleRepository.save(article);
        Article savedArticle = articleRepository.saveAndFlush(article);

        // then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("Delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {

        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();

        // when
        articleRepository.delete(article);

        // then
        assertThat(articleRepository.count())
                .isEqualTo(previousArticleCount - 1);

        assertThat(articleCommentRepository.count())
                .isEqualTo(previousArticleCommentCount - deletedCommentSize);
    }

}