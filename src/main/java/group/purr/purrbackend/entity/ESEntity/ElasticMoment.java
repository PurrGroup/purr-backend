package group.purr.purrbackend.entity.ESEntity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "moment")
@Data
public class ElasticMoment {

    @Id
    private Long ID;

    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String content;

    @Field(name = "create_time")
    private String createTime;
}
