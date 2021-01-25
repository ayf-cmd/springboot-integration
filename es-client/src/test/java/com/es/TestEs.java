package com.es;

import com.es.dao.UserRepository;
import com.es.entity.UserEntity;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEs {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 添加用户
    @Test
    public void addUser() {
        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UserEntity userEntity = UserEntity.builder()
                    .age(30 + i)
                    .hobby("my hobby is 玩电脑" + i)
                    .id(Long.valueOf(i))
                    .images("我的图片地址" + i)
                    .name("我的名字是安雲峰" + i)
                    .notStore("不存储数据" + i)
                    .userId("userid" + i)
                    .build();
            users.add(userEntity);
        }
        userRepository.saveAll(users);
    }

    @Test
    public void query() {
//        BoolQueryBuilder builder = QueryBuilders.boolQuery();
//        MatchQueryBuilder name = QueryBuilders.matchQuery("name", "名字");
//        TermQueryBuilder name1 = QueryBuilders.termQuery("name", "1");
//        MatchPhraseQueryBuilder name2 = QueryBuilders.matchPhraseQuery("name", "1");
//        FuzzyQueryBuilder name3 = QueryBuilders.fuzzyQuery("name", "名字");
//        RangeQueryBuilder age = QueryBuilders.rangeQuery("age").from(30).to(35);
//        builder.must(name1);
//        Iterable<UserEntity> search = userRepository.search(builder);
//        for (UserEntity userEntity : search) {
//            System.err.println(userEntity);
//        }

        Pageable pageable = PageRequest.of(0, 3);
        Page<UserEntity> pa = userRepository.findAll(pageable);
        System.err.println(pa.getTotalPages());
        System.err.println(pa.getSize());
        System.err.println(pa.getNumber());
        System.err.println(pa.getTotalElements());
        System.err.println(pa.getContent());

//        Page<UserEntity> userEntities = userRepository.searchSimilar(UserEntity.builder().name("名字").build(), new String[]{"id","name"}, pageable);
//        System.err.println(userEntities.toString());
    }

    @Test
    public void sort() {
        Sort sort = Sort.by("age").descending();
        Iterable<UserEntity> all = userRepository.findAll(sort);
        for (UserEntity userEntity : all) {
            System.err.println(userEntity);
        }
    }

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    public void rest() throws IOException {

        int i = 2;
        UserEntity userEntity = UserEntity.builder()
                .age(30 + i)
                .hobby("my hobby is 玩电脑" + i)
                .id(Long.valueOf(i))
                .images("我的图片地址" + i)
                .name("我的名字是安雲峰" + i)
                .notStore("不存储数据" + i)
                .userId("userid" + i)
                .build();
//        // 新增一条记录
//        IndexQuery indexQuery = new IndexQuery();
//        indexQuery.setId(String.valueOf(userEntity.getId()));
//        indexQuery.setSource(JSON.toJSONString(userEntity));
//        elasticsearchRestTemplate.index(indexQuery, IndexCoordinates.of("userrest"));
//        // 新增一条记录
//        elasticsearchRestTemplate.save(userEntity);

        // 修改记录
//        Map<String, Object> map = new HashMap<>();
//        map.put("age", "0");
//        UpdateQuery updateQuery = UpdateQuery.builder(String.valueOf(userEntity.getId())).withDocument(Document.from(map)).build();
//        elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("userrest"));


//        Map<String, Object> map = new HashMap<>();
//        map.put("age", 5);
//        UpdateRequest updateRequest = new UpdateRequest("user","-30");
//        updateRequest.doc(map);
//        org.elasticsearch.action.update.UpdateResponse update1 = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

    }

}
