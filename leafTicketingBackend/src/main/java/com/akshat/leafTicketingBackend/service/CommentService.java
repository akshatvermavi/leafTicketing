package com.akshat.leafTicketingBackend.service;

import com.akshat.leafTicketingBackend.model.Comment;
import com.akshat.leafTicketingBackend.model.Ticket;
import com.akshat.leafTicketingBackend.model.User;
import com.akshat.leafTicketingBackend.repository.CommentRepository;
import com.akshat.leafTicketingBackend.repository.TicketRepository;
import com.akshat.leafTicketingBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    public Comment addComment(Long ticketId, Long userId, String content) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Comment comment = Comment.builder()
                .ticket(ticket)
                .user(user)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForTicket(Long ticketId) {
        return commentRepository.findAll().stream()
                .filter(c -> c.getTicket().getId().equals(ticketId))
                .toList();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
} 