package group.purr.purrbackend.repository.ESRepository;

import group.purr.purrbackend.entity.ESEntity.ElasticArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticArticleRepository extends ElasticsearchRepository<ElasticArticle, String> {
}
