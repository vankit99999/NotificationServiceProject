package com.meesho.notificationservice.repositories.searchrepository;

import com.meesho.notificationservice.models.SearchEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchRepository extends ElasticsearchRepository<SearchEntity,String> {
//    List<SearchEntity> findByPhoneNumber(String phoneNumber, Pageable pageable);
//    List<SearchEntity> findByTextContainingAndLastUpdatedAtBetween(String text, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}
