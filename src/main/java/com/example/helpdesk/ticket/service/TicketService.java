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

import com.example.helpdesk.category.model.Category;
import com.example.helpdesk.category.service.CategoryService;
import com.example.helpdesk.ticket.event.TicketCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;

import com.example.helpdesk.ticket.dto.TicketStatusRequest;

import com.example.helpdesk.user.model.AppUser;
import com.example.helpdesk.user.repository.AppUserRepository;
import org.springframework.security.core.Authentication;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final CategoryService categoryService;

    private final ApplicationEventPublisher eventPublisher;

    private final AppUserRepository appUserRepository;

    public TicketService(TicketRepository ticketRepository,
                         CategoryService categoryService,
                         ApplicationEventPublisher eventPublisher,
                         AppUserRepository appUserRepository) {
        this.ticketRepository = ticketRepository;
        this.categoryService = categoryService;
        this.eventPublisher = eventPublisher;
        this.appUserRepository = appUserRepository;
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

    public TicketResponse createTicket(TicketRequest request, Authentication authentication) {
        Category category = categoryService.findCategoryById(request.getCategoryId());

        AppUser user = appUserRepository.findByUsername(authentication.getName())
                .orElseThrow();

        Ticket ticket = TicketMapper.toEntity(request);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCategory(category);
        ticket.setAuthorEmail(user.getEmail());

        Ticket savedTicket = ticketRepository.save(ticket);

        eventPublisher.publishEvent(new TicketCreatedEvent(savedTicket));

        return TicketMapper.toResponse(savedTicket);
    }

    public TicketResponse updateTicket(Long id, TicketRequest request) {
        Ticket existingTicket = findTicketById(id);
        Category category = categoryService.findCategoryById(request.getCategoryId());

        existingTicket.setTitle(request.getTitle());
        existingTicket.setDescription(request.getDescription());
        existingTicket.setCategory(category);

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

    public TicketResponse updateTicketStatus(Long id, TicketStatusRequest request) {
        Ticket existingTicket = findTicketById(id);

        existingTicket.setStatus(request.getStatus());

        Ticket savedTicket = ticketRepository.save(existingTicket);
        return TicketMapper.toResponse(savedTicket);
    }

    public List<TicketResponse> getMyTickets(Authentication authentication) {
        AppUser user = appUserRepository.findByUsername(authentication.getName())
                .orElseThrow();

        return ticketRepository.findByAuthorEmail(user.getEmail())
                .stream()
                .map(TicketMapper::toResponse)
                .toList();
    }
}