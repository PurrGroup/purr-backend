package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Blogmeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogmetaRepository extends JpaRepository<Blogmeta, String> {

    //查询表中记录数
    Long countAllByOptionName(String option);

}
