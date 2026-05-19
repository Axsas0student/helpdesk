package com.example.helpdesk.ticket.repository;

import com.example.helpdesk.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}