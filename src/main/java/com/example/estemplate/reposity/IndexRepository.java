package com.example.estemplate.reposity;

import com.example.estemplate.entity.IndexUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface IndexRepository extends ElasticsearchRepository<IndexUser, String> {
}
