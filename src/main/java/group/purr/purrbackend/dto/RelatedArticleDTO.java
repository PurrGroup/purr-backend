package group.purr.purrbackend.dto;

import lombok.Data;

@Data
public class RelatedArticleDTO {
    ArticleDTO prev;
    ArticleDTO next;
}
