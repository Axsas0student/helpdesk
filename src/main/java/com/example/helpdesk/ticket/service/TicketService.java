package com.example.helpdesk.ticket.service;

import com.example.helpdesk.ticket.dto.TicketRequest;
import com.example.helpdesk.ticket.dto.TicketResponse;
import com.example.helpdesk.ticket.mapper.TicketMapper;
import com.example.helpdesk.ticket.model.Ticket;
import com.example.helpdesk.ticket.model.TicketStatus;
import com.example.helpdesk.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;
import com.example.helpdesk.exception.TicketNotFoundException;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(TicketMapper::toResponse)
                .toList();
    }

    public TicketResponse getTicketById(Long id) {
        Ticket ticket = findTicketById(id);
        return TicketMapper.toResponse(ticket);
    }

    public TicketResponse createTicket(TicketRequest request) {
        Ticket ticket = TicketMapper.toEntity(request);
        ticket.setStatus(TicketStatus.OPEN);

        Ticket savedTicket = ticketRepository.save(ticket);
        return TicketMapper.toResponse(savedTicket);
    }

    public TicketResponse updateTicket(Long id, TicketRequest request) {
        Ticket existingTicket = findTicketById(id);

        existingTicket.setTitle(request.getTitle());
        existingTicket.setDescription(request.getDescription());
        existingTicket.setAuthorEmail(request.getAuthorEmail());

        if (request.getStatus() != null) {
            existingTicket.setStatus(request.getStatus());
        }

        Ticket savedTicket = ticketRepository.save(existingTicket);
        return TicketMapper.toResponse(savedTicket);
    }

    public void deleteTicket(Long id) {
        Ticket existingTicket = findTicketById(id);
        ticketRepository.delete(existingTicket);
    }

    private Ticket findTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }
}