package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, String> {
    Optional<Author> findAuthorByOptionKey(String optionKey);
}
