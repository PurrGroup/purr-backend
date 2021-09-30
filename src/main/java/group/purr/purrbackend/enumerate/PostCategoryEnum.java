package group.purr.purrbackend.enumerate;

import lombok.Getter;

@Getter
public enum PostCategoryEnum {

    ARTICLE(0),

    PAGE(1),

    COMMENT(2);


    /**
     * 页面类型码
     */
    private final Integer postCategory;


    PostCategoryEnum(Integer postCategory) {
        this.postCategory = postCategory;
    }
}
