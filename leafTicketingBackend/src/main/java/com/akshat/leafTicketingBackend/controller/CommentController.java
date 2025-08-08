package com.akshat.leafTicketingBackend.controller;

import com.akshat.leafTicketingBackend.model.Comment;
import com.akshat.leafTicketingBackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PreAuthorize("hasAnyRole('USER','ADMIN','AGENT')")
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestParam Long ticketId, @RequestParam String content, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.status(401).build();
        var userOpt = commentService.getUserRepository().findByUsername(userDetails.getUsername());
        if (userOpt.isEmpty()) return ResponseEntity.status(404).build();
        var user = userOpt.get();
        return ResponseEntity.ok(commentService.addComment(ticketId, user.getId(), content));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','AGENT')")
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<Comment>> getCommentsForTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(commentService.getCommentsForTicket(ticketId));
    }
} 