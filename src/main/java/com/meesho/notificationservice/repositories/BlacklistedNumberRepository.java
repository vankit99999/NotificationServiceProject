package com.meesho.notificationservice.repositories;

import com.meesho.notificationservice.models.Blacklist.BlacklistedNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlacklistedNumberRepository extends JpaRepository<BlacklistedNumber, Long> {
    @Query("SELECT s.id FROM BlacklistedNumber s WHERE s.phoneNumber = ?1")
    Optional<Long> findPhoneNumberAndReturnID(String phoneNumber);
}