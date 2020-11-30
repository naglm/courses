package com.example.courses.repository;

import com.example.courses.documents.Article;
import com.example.courses.model.Author;
import com.example.courses.repository.es.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void testStoreAndLoad() {

        Article article = new Article(null, "Spring Data Elasticsearch", null);
        article.setAuthors(asList(new Author("John Smith"), new Author("John Doe")));
        articleRepository.save(article);

        Page<Article> result1 = articleRepository.findByAuthorsName("Flinstone", Pageable.unpaged());
        Optional<Article> article1 = result1.stream().findFirst();
        assertTrue(article1.isPresent());
        List<Author> authors = article1.get().getAuthors().stream().filter(author -> author.getName().contains("Flinstone")).collect(Collectors.toList());
        assertTrue(authors.size() >= 1);


    }
}
