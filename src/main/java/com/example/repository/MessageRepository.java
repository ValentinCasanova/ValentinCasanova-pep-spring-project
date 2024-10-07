package com.example.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    // Find all messages by postedBy
    @Query("SELECT m FROM Message m WHERE m.postedBy = ?1")
    List<Message> getMessagesByAccountId(Integer accountId);
}
