package com.es;

import com.es.dao.UserRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EsClient {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;



}
