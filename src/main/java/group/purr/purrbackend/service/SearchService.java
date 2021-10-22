package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.SearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {
    List<SearchDTO> searchByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchArticleByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchPageByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchCommentByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchMomentByKeyword(String keyword, Pageable pageable);
}
