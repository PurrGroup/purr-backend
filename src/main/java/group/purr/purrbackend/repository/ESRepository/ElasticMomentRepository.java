package group.purr.purrbackend.repository.ESRepository;

import group.purr.purrbackend.entity.ESEntity.ElasticMoment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticMomentRepository extends ElasticsearchRepository<ElasticMoment, String> {
}
