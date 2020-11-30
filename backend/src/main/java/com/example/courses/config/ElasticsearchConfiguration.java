package com.example.courses.config;

import com.example.courses.CoursesApplication;
import com.example.courses.documents.Article;
import com.example.courses.model.Author;
import com.example.courses.repository.es.ArticleRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import static java.util.Arrays.asList;

@Configuration
@Profile({"dev", "test"})
@EnableElasticsearchRepositories(basePackages = "com.example.courses.repository")
//@ComponentScan(basePackages = { "com.example.courses.service" })
public class ElasticsearchConfiguration {

    Logger logger = LoggerFactory.getLogger(ElasticsearchConfiguration.class);

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo("localhost:9201")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }

    @Bean
    public CommandLineRunner initArticles(ArticleRepository articleRepository) {
        return args -> {
            logger.debug("Creating article 1");
            Article article = new Article(null, "Spring Data Elasticsearch", null);
            article.setAuthors(asList(new Author("John Smith"), new Author("John Doe")));
            articleRepository.save(article);

            logger.debug("Creating article 2");
            Article article2 = new Article(null, "Rock & Roll With Elasticsearch", null);
            article2.setAuthors(asList(new Author("Fred Flinstone"), new Author("Barney Rubble")));
            articleRepository.save(article2);

            Page<Article> result =  articleRepository.findByAuthorsNameUsingCustomQuery("Fred Flinstone", Pageable.unpaged());
            result.stream().forEach(a -> {
                logger.debug("Article " + a.getId() + ": " + a.getTitle());
            });
        };
    }
}
