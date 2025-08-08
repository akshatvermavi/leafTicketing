package com.akshat.leafTicketingBackend.repository;

import com.akshat.leafTicketingBackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
} 