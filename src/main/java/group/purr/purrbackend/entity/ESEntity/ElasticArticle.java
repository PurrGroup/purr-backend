package group.purr.purrbackend.entity.ESEntity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "article")
@Data
public class ElasticArticle {

    @Id
    private Long ID;

    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String content;

    @Field(name = "create_time")
    private String createTime;

    @Field(name = "link_name")
    private String linkName;

    private Integer status;
}
