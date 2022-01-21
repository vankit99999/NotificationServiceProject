package com.meesho.notificationservice.repository;

import com.meesho.notificationservice.models.BlacklistedNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlacklistedRepository extends JpaRepository<BlacklistedNumber,Long> {

    @Query("SELECT s FROM BlacklistedNumber s WHERE s.phoneNumber = ?1")
    Optional<BlacklistedNumber> findPhoneNumber(String phoneNumber);

    @Query("SELECT s.phoneNumber FROM BlacklistedNumber s WHERE s.phoneNumber = ?1")
    Optional<String> findPhoneNumberAndReturnAsString(String phoneNumber);
}