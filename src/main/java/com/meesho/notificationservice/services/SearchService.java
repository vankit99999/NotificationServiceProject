package com.meesho.notificationservice.services;

//import com.meesho.notificationservice.models.Message1;
import com.meesho.notificationservice.models.SearchEntity;
import com.meesho.notificationservice.repositories.searchrepository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class SearchService {
//    private final SearchRepository searchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public SearchService(ElasticsearchOperations elasticsearchOperations) {
//        this.searchRepository = searchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

//    public List<SearchEntity> searchByPhoneNumber(String phoneNumber) {
//        return searchRepository.findByPhoneNumber(phoneNumber, PageRequest.of(0,5));
//    }
//
//    public List<SearchEntity> searchByTextContaining(String text, LocalDateTime startTime, LocalDateTime endTime) {
//        return searchRepository.findByTextContainingAndLastUpdatedAtBetween(text, startTime, endTime, PageRequest.of(0,5));
//    }
//
//    public SearchEntity createSearchEntityIndex(SearchEntity searchEntity) {
//        return searchRepository.save(searchEntity);
//    }
    public List<SearchEntity> findByPhoneNumberAndTime(String phoneNumber, LocalDateTime startTime, LocalDateTime endTime,int page,int size) {
        Criteria timeCriteria = new Criteria("lastUpdatedAt")
                .greaterThanEqual(startTime)
                .lessThanEqual(endTime);
        Criteria nameCriteria = new Criteria("phoneNumber").contains(phoneNumber);
        nameCriteria = nameCriteria.and(timeCriteria);
        Query searchQuery = new CriteriaQuery(nameCriteria).setPageable(PageRequest.of(page,size));

        SearchHits<SearchEntity> searchEntitySearchHits = elasticsearchOperations.search(searchQuery,
                SearchEntity.class,
                IndexCoordinates.of("messagesindex5"));
        List<SearchEntity> searchEntities= new ArrayList<SearchEntity>();
        searchEntitySearchHits.forEach(searchHit->{
            searchEntities.add(searchHit.getContent());
        });
        return searchEntities;
    }
    public List<SearchEntity> findByTextAndTime(String text, LocalDateTime startTime, LocalDateTime endTime,int page,int size) {
        Criteria timeCriteria = new Criteria("lastUpdatedAt")
                .greaterThanEqual(startTime)
                .lessThanEqual(endTime);
        Criteria nameCriteria = new Criteria("text").contains(text);
        nameCriteria = nameCriteria.and(timeCriteria);
        Query searchQuery = new CriteriaQuery(nameCriteria).setPageable(PageRequest.of(page,size));

        SearchHits<SearchEntity> searchEntitySearchHits = elasticsearchOperations.search(searchQuery,
                        SearchEntity.class,
                        IndexCoordinates.of("messagesindex5"));
        List<SearchEntity> searchEntities= new ArrayList<SearchEntity>();
        searchEntitySearchHits.forEach(searchHit->{
            searchEntities.add(searchHit.getContent());
        });
        return searchEntities;
    }
    public String createSearchIndex(SearchEntity searchEntity) {

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(searchEntity.getId())
                .withObject(searchEntity).build();

        String documentId = elasticsearchOperations
                .index(indexQuery, IndexCoordinates.of("messagesindex5"));
        System.out.println(documentId);
        return documentId;
    }
}
