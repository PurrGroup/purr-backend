package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.BlogMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogmetaRepository extends JpaRepository<BlogMeta, String> {

    //查询表中记录数
    Long countAllByOptionName(String option);

}
