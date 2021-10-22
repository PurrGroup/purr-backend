package group.purr.purrbackend.repository.ESRepository;

import group.purr.purrbackend.entity.ESEntity.ElasticPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticPageRepository extends ElasticsearchRepository<ElasticPage, String> {
}
