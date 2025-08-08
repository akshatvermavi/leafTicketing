package com.akshat.leafTicketingBackend.controller;

import com.akshat.leafTicketingBackend.model.Role;
import com.akshat.leafTicketingBackend.model.Status;
import com.akshat.leafTicketingBackend.model.Ticket;
import com.akshat.leafTicketingBackend.model.User;
import com.akshat.leafTicketingBackend.service.AdminService;
import com.akshat.leafTicketingBackend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/{userId}/role")
    public ResponseEntity<?> assignRole(@PathVariable Long userId, @RequestParam Role role) {
        Optional<User> user = adminService.assignRole(userId, role);
        return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable Long userId) {
        adminService.removeUser(userId);
        return ResponseEntity.ok().build();
    }

    // Admin ticket override endpoints
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/tickets/{ticketId}/force-assign")
    public ResponseEntity<?> forceAssignTicket(@PathVariable Long ticketId, @RequestParam Long assigneeId) {
        Optional<Ticket> ticket = ticketService.assignTicket(ticketId, assigneeId);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/tickets/{ticketId}/force-status")
    public ResponseEntity<?> forceChangeStatus(@PathVariable Long ticketId, @RequestParam Status status) {
        Optional<Ticket> ticket = ticketService.changeStatus(ticketId, status);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 