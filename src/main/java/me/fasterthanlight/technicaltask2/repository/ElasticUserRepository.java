package me.fasterthanlight.technicaltask2.repository;

import me.fasterthanlight.technicaltask2.domain.enitity.ElasticUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticUserRepository extends ElasticsearchRepository<ElasticUser, String> {
}
