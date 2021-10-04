package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
    Author findAuthorByOptionKey(String optionKey);
}
