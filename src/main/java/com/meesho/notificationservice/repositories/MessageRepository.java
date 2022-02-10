package com.meesho.notificationservice.repositories;

import com.meesho.notificationservice.models.SMS.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
}
