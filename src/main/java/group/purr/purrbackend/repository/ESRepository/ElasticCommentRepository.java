package group.purr.purrbackend.repository.ESRepository;

import group.purr.purrbackend.entity.ESEntity.ElasticComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticCommentRepository extends ElasticsearchRepository<ElasticComment, String> {
}
