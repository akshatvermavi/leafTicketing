package com.akshat.leafTicketingBackend.controller;

import com.akshat.leafTicketingBackend.model.Status;
import com.akshat.leafTicketingBackend.model.Ticket;
import com.akshat.leafTicketingBackend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasAnyRole('USER','ADMIN','AGENT')")
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket, @RequestParam Long ownerId) {
        return ResponseEntity.ok(ticketService.createTicket(ticket, ownerId));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','AGENT')")
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets(@RequestParam(value = "owner", required = false) Boolean owner,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        if (Boolean.TRUE.equals(owner) && userDetails != null) {
            return ResponseEntity.ok(ticketService.getTicketsByOwner(userDetails.getUsername()));
        }
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','AGENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','AGENT')")
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        ticket.setId(id);
        return ResponseEntity.ok(ticketService.updateTicket(ticket));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','AGENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignTicket(@PathVariable Long id, @RequestParam Long assigneeId) {
        Optional<Ticket> ticket = ticketService.assignTicket(id, assigneeId);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    @PostMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestParam Status status) {
        Optional<Ticket> ticket = ticketService.changeStatus(id, status);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 