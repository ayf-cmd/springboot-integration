package com.es.dao;

import com.es.entity.UserEntity;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<UserEntity, Long> {

    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    List<UserEntity> findByNameLike(String name);

}