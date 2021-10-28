package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.BlogMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogMetaRepository extends JpaRepository<BlogMeta, String> {

    Optional<BlogMeta> findBlogMetaByOptionKey(String optionKey);

    //查询表中记录数
    Long countAllByOptionKey(String option);

}
