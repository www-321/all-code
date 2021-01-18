package com.example.estemplate;

import com.example.estemplate.entity.IndexUser;
import com.example.estemplate.reposity.IndexRepository;
import org.apache.lucene.search.TermQuery;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootTest
class EsTemplateApplicationTests {
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    IndexRepository indexRepository;


    @Autowired
    RestTemplate restTemplate;


    @Test
    void contextLoads() {
    }

    @Test
    public void t1() throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        Condition condition = lock.newCondition();
        condition.await();

        lock.unlock();
    }




    @Test
    public void index() {
        IndexUser indexUser = new IndexUser();
        indexUser.setAge(20);
        indexUser.setId("121");
        indexUser.setName("武全");

        indexRepository.save(indexUser);
    }

    @Test
    public void query() {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("ip","220.3.2.3");
        BoolQueryBuilder must = QueryBuilders.boolQuery().must(queryBuilder);
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(must)
                .build();
        System.out.println(elasticsearchRestTemplate.search(query, IndexUser.class).getSearchHits());

    }

    @Test
    public void agg() {
        TermsAggregationBuilder aggs = AggregationBuilders.terms("aggs").field("name.keyword");
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .addAggregation(aggs)
                .withPageable(PageRequest.of(1, 10))
                .build();
    }


    @Test
    public void rest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        HttpEntity httpEntity = new HttpEntity(map, headers);
        System.out.println(restTemplate.exchange("http://www.baidu.com", HttpMethod.POST, httpEntity, String.class).getBody());
    }


}
