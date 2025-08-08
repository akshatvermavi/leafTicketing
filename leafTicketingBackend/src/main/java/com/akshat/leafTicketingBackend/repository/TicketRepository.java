package com.akshat.leafTicketingBackend.repository;

import com.akshat.leafTicketingBackend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
} 