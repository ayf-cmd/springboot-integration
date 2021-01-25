package com.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

@EnableElasticsearchAuditing
@EnableElasticsearchRepositories
@EnableReactiveElasticsearchRepositories
@SpringBootApplication
public class EsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class);
    }
}