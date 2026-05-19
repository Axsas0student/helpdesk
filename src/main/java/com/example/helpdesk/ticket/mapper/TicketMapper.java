package com.example.helpdesk.ticket.mapper;

import com.example.helpdesk.ticket.model.Ticket;
import com.example.helpdesk.ticket.dto.TicketRequest;
import com.example.helpdesk.ticket.dto.TicketResponse;

public class TicketMapper {

    public static Ticket toEntity(TicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setAuthorEmail(request.getAuthorEmail());
        ticket.setStatus(request.getStatus());
        return ticket;
    }

    public static TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getAuthorEmail(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }
}