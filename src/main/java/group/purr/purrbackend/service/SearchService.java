package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.SearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;

public interface SearchService {

    Long getTotalPublic();

    Long getTotalNotDeleted();

    List<SearchDTO> searchPublicByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchNotDeletedByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchArticlePublicByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchArticleNotDeletedByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchPagePublicByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchPageNotDeletedByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchCommentByKeyword(String keyword, Pageable pageable);

    List<SearchDTO> searchMomentByKeyword(String keyword, Pageable pageable);

}
