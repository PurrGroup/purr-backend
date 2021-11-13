package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Link;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, String> {
    List<Link> findAllByCategoryOrderByUpdateTimeDesc(Integer category);
}
