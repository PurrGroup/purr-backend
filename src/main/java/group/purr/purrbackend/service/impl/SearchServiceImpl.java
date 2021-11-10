package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.SearchDTO;
import group.purr.purrbackend.entity.ESEntity.*;
import group.purr.purrbackend.repository.ESRepository.*;
import group.purr.purrbackend.service.SearchService;
import group.purr.purrbackend.utils.ESUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    final ElasticsearchRestTemplate elasticsearchRestTemplate;

    final ModelMapper modelMapper;

    final ElasticArticleRepository elasticArticleRepository;

    final ElasticPageRepository elasticPageRepository;

    final ElasticMomentRepository elasticMomentRepository;

    final ElasticCommentRepository elasticCommentRepository;

    private Long articlePublicCount = 0L;
    private Long articleNotDeletedCount = 0L;

    private Long pagePublicCount = 0L;
    private Long pageNotDeletedCount = 0L;

    private Long commentCount = 0L;

    private Long momentCount = 0L;

    public SearchServiceImpl(ElasticsearchRestTemplate elasticsearchRestTemplate, ModelMapper modelMapper, ElasticArticleRepository elasticArticleRepository, ElasticPageRepository elasticPageRepository, ElasticMomentRepository elasticMomentRepository, ElasticCommentRepository elasticCommentRepository) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.modelMapper = modelMapper;
        this.elasticArticleRepository = elasticArticleRepository;
        this.elasticPageRepository = elasticPageRepository;
        this.elasticMomentRepository = elasticMomentRepository;
        this.elasticCommentRepository = elasticCommentRepository;
    }

    @Override
    public Long getTotalPublic() {
        return articlePublicCount + pagePublicCount + commentCount + momentCount;
    }

    @Override
    public Long getTotalNotDeleted() {
        return articleNotDeletedCount + pageNotDeletedCount + commentCount + momentCount;
    }

    @Override
    public List<SearchDTO> searchPublicByKeyword(String keyword, Pageable pageable) {
        List<SearchDTO> result = new ArrayList<>();

        List<SearchDTO> articles = searchArticlePublicByKeyword(keyword, pageable);
        List<SearchDTO> pages = searchPagePublicByKeyword(keyword, pageable);
        List<SearchDTO> comments = searchCommentByKeyword(keyword, pageable);
        List<SearchDTO> moments = searchMomentByKeyword(keyword, pageable);

        for (SearchDTO article : articles){
            result.add(article);
        }

        for (SearchDTO page : pages){
            result.add(page);
        }

        for (SearchDTO comment : comments){
            result.add(comment);
        }

        for (SearchDTO moment : moments){
            result.add(moment);
        }

        return result;
    }

    @Override
    public List<SearchDTO> searchNotDeletedByKeyword(String keyword, Pageable pageable) {
        List<SearchDTO> result = new ArrayList<>();

        List<SearchDTO> articles = searchArticleNotDeletedByKeyword(keyword, pageable);
        List<SearchDTO> pages = searchPageNotDeletedByKeyword(keyword, pageable);
        List<SearchDTO> comments = searchCommentByKeyword(keyword, pageable);
        List<SearchDTO> moments = searchMomentByKeyword(keyword, pageable);

        for (SearchDTO article : articles){
            result.add(article);
        }

        for (SearchDTO page : pages){
            result.add(page);
        }

        for (SearchDTO comment : comments){
            result.add(comment);
        }

        for (SearchDTO moment : moments){
            result.add(moment);
        }

        return result;
    }

    @Override
    public List<SearchDTO> searchArticleNotDeletedByKeyword(String keyword, Pageable pageable) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()
                                .mustNot(QueryBuilders.existsQuery("delete_time"))
                                .must(QueryBuilders.boolQuery()
                                        .should(QueryBuilders.matchQuery("name", keyword))
                                        .should(QueryBuilders.prefixQuery("name", keyword))
                                        .should(QueryBuilders.matchQuery("content", keyword))
                                        .should(QueryBuilders.prefixQuery("content", keyword))
                                )
                ).withPageable(pageable).build();

        SearchHits<ElasticArticle> searchHits = elasticsearchRestTemplate.search(query, ElasticArticle.class);
        List<ElasticArticle> articles = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        articleNotDeletedCount = articles.stream().count();
        List<SearchDTO> result = new ArrayList<>();

        for (ElasticArticle article : articles){
            SearchDTO searchDTO = ESUtil.formatArticleToSearch(article);
            result.add(searchDTO);
        }

        return result;
    }

    @Override
    public List<SearchDTO> searchArticlePublicByKeyword(String keyword, Pageable pageable) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()
                                .mustNot(QueryBuilders.existsQuery("delete_time"))
                                .must(QueryBuilders.matchQuery("status", 1))
                                .must(
                                        QueryBuilders.boolQuery()
                                                .should(QueryBuilders.matchQuery("name", keyword))
                                                .should(QueryBuilders.prefixQuery("name", keyword))
                                                .should(QueryBuilders.matchQuery("content", keyword))
                                                .should(QueryBuilders.prefixQuery("content", keyword))
                                )
                ).withPageable(pageable).build();

        SearchHits<ElasticArticle> searchHits = elasticsearchRestTemplate.search(query, ElasticArticle.class);
        List<ElasticArticle> articles = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        articlePublicCount = articles.stream().count();
        List<SearchDTO> result = new ArrayList<>();

        for (ElasticArticle article : articles){
            SearchDTO searchDTO = ESUtil.formatArticleToSearch(article);
            result.add(searchDTO);
        }

        return result;
    }

    @Override
    public List<SearchDTO> searchPagePublicByKeyword(String keyword, Pageable pageable) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()
                                .mustNot(QueryBuilders.existsQuery("delete_time"))
                                .must(QueryBuilders.matchQuery("status", 1))
                                .must(QueryBuilders.boolQuery()
                                        .should(QueryBuilders.matchQuery("name", keyword))
                                        .should(QueryBuilders.prefixQuery("name", keyword))
                                        .should(QueryBuilders.matchQuery("content", keyword))
                                        .should(QueryBuilders.prefixQuery("content", keyword))
                                )
                ).withPageable(pageable).build();

        SearchHits<ElasticPage> searchHits = elasticsearchRestTemplate.search(query, ElasticPage.class);
        List<ElasticPage> pages = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        pagePublicCount = pages.stream().count();
        List<SearchDTO> result = new ArrayList<>();

        for (ElasticPage page : pages){
            SearchDTO searchDTO = ESUtil.formatPageToSearch(page);
            result.add(searchDTO);
        }

        return result;
    }

    @Override
    public List<SearchDTO> searchPageNotDeletedByKeyword(String keyword, Pageable pageable) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()
                                .mustNot(QueryBuilders.existsQuery("delete_time"))
                                .must(QueryBuilders.boolQuery()
                                        .should(QueryBuilders.matchQuery("name", keyword))
                                        .should(QueryBuilders.prefixQuery("name", keyword))
                                        .should(QueryBuilders.matchQuery("content", keyword))
                                        .should(QueryBuilders.prefixQuery("content", keyword))
                                )
                ).withPageable(pageable).build();

        SearchHits<ElasticPage> searchHits = elasticsearchRestTemplate.search(query, ElasticPage.class);
        List<ElasticPage> pages = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        pageNotDeletedCount = pages.stream().count();
        List<SearchDTO> result = new ArrayList<>();

        for (ElasticPage page : pages){
            SearchDTO searchDTO = ESUtil.formatPageToSearch(page);
            result.add(searchDTO);
        }

        return result;
    }

    @Override
    public List<SearchDTO> searchCommentByKeyword(String keyword, Pageable pageable) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()
                                .should(QueryBuilders.matchQuery("content", keyword))
                                .should(QueryBuilders.prefixQuery("content", keyword))
                ).withPageable(pageable).build();

        SearchHits<ElasticComment> searchHits = elasticsearchRestTemplate.search(query, ElasticComment.class);
        List<ElasticComment> comments = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        commentCount = comments.stream().count();
        List<SearchDTO> result = new ArrayList<>();

        for (ElasticComment comment : comments){
            SearchDTO searchDTO = ESUtil.formatCommentToSearch(comment);
            result.add(searchDTO);
        }

        return result;
    }

    @Override
    public List<SearchDTO> searchMomentByKeyword(String keyword, Pageable pageable) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()
                                .should(QueryBuilders.matchQuery("content", keyword))
                                .should(QueryBuilders.prefixQuery("content", keyword))
                ).withPageable(pageable).build();

        SearchHits<ElasticMoment> searchHits = elasticsearchRestTemplate.search(query, ElasticMoment.class);
        List<ElasticMoment> moments = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        momentCount = moments.stream().count();
        List<SearchDTO> result = new ArrayList<>();

        for (ElasticMoment moment : moments){
            SearchDTO searchDTO = ESUtil.formatMomentToSearch(moment);
            result.add(searchDTO);
        }

        return result;
    }
}
