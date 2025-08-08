package com.akshat.leafTicketingBackend.service;

import com.akshat.leafTicketingBackend.model.*;
import com.akshat.leafTicketingBackend.repository.TicketRepository;
import com.akshat.leafTicketingBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    public Ticket createTicket(Ticket ticket, Long ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow();
        ticket.setOwner(owner);
        ticket.setStatus(Status.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket updateTicket(Ticket ticket) {
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public Optional<Ticket> assignTicket(Long ticketId, Long assigneeId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        Optional<User> assigneeOpt = userRepository.findById(assigneeId);
        if (ticketOpt.isPresent() && assigneeOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setAssignee(assigneeOpt.get());
            ticket.setUpdatedAt(LocalDateTime.now());
            ticketRepository.save(ticket);
            return Optional.of(ticket);
        }
        return Optional.empty();
    }

    public Optional<Ticket> changeStatus(Long ticketId, Status status) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setStatus(status);
            ticket.setUpdatedAt(LocalDateTime.now());
            ticketRepository.save(ticket);
            return Optional.of(ticket);
        }
        return Optional.empty();
    }

    public List<Ticket> getTicketsByOwner(String username) {
        return ticketRepository.findAll().stream()
            .filter(ticket -> ticket.getOwner() != null && username.equals(ticket.getOwner().getUsername()))
            .toList();
    }
} 