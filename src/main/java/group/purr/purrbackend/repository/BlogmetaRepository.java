package group.purr.purrbackend.repository;

import com.sun.org.apache.xpath.internal.operations.Bool;
import group.purr.purrbackend.entity.Blogmeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogmetaRepository extends JpaRepository<Blogmeta, String> {

    //查询表中记录数
    Long countAllByOptionName(String option);

}
