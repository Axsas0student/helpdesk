package com.example.helpdesk.ticket.service;

import com.example.helpdesk.category.model.Category;
import com.example.helpdesk.category.service.CategoryService;
import com.example.helpdesk.exception.TicketNotFoundException;
import com.example.helpdesk.ticket.dto.TicketRequest;
import com.example.helpdesk.ticket.dto.TicketResponse;
import com.example.helpdesk.ticket.model.Ticket;
import com.example.helpdesk.ticket.model.TicketStatus;
import com.example.helpdesk.ticket.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void shouldCreateTicketWithOpenStatusAndCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Logowanie");
        category.setDescription("Problemy z logowaniem");

        TicketRequest request = new TicketRequest();
        request.setTitle("Problem z logowaniem");
        request.setDescription("Użytkownik nie może zalogować się do systemu.");
        request.setAuthorEmail("user@example.com");
        request.setCategoryId(1L);

        Ticket savedTicket = new Ticket();
        savedTicket.setId(1L);
        savedTicket.setTitle("Problem z logowaniem");
        savedTicket.setDescription("Użytkownik nie może zalogować się do systemu.");
        savedTicket.setAuthorEmail("user@example.com");
        savedTicket.setStatus(TicketStatus.OPEN);
        savedTicket.setCategory(category);
        savedTicket.setCreatedAt(LocalDateTime.now());
        savedTicket.setUpdatedAt(LocalDateTime.now());

        when(categoryService.findCategoryById(1L)).thenReturn(category);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        TicketResponse response = ticketService.createTicket(request);

        assertEquals(1L, response.getId());
        assertEquals("Problem z logowaniem", response.getTitle());
        assertEquals(TicketStatus.OPEN, response.getStatus());
        assertEquals("user@example.com", response.getAuthorEmail());
        assertNotNull(response.getCategory());
        assertEquals("Logowanie", response.getCategory().getName());

        verify(categoryService).findCategoryById(1L);
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void shouldGetTicketById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Sieć");
        category.setDescription("Problemy z siecią");

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("Brak internetu");
        ticket.setDescription("Użytkownik nie ma dostępu do internetu.");
        ticket.setAuthorEmail("user@example.com");
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCategory(category);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        TicketResponse response = ticketService.getTicketById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Brak internetu", response.getTitle());
        assertEquals(TicketStatus.OPEN, response.getStatus());
        assertEquals("Sieć", response.getCategory().getName());

        verify(ticketRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenTicketNotFound() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class,
                () -> ticketService.getTicketById(999L));

        verify(ticketRepository).findById(999L);
    }

    @Test
    void shouldGetAllTickets() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Sprzęt");
        category.setDescription("Problemy ze sprzętem");

        Ticket firstTicket = new Ticket();
        firstTicket.setId(1L);
        firstTicket.setTitle("Problem z laptopem");
        firstTicket.setDescription("Laptop nie uruchamia się.");
        firstTicket.setAuthorEmail("user1@example.com");
        firstTicket.setStatus(TicketStatus.OPEN);
        firstTicket.setCategory(category);
        firstTicket.setCreatedAt(LocalDateTime.now());
        firstTicket.setUpdatedAt(LocalDateTime.now());

        Ticket secondTicket = new Ticket();
        secondTicket.setId(2L);
        secondTicket.setTitle("Problem z monitorem");
        secondTicket.setDescription("Monitor nie wyświetla obrazu.");
        secondTicket.setAuthorEmail("user2@example.com");
        secondTicket.setStatus(TicketStatus.IN_PROGRESS);
        secondTicket.setCategory(category);
        secondTicket.setCreatedAt(LocalDateTime.now());
        secondTicket.setUpdatedAt(LocalDateTime.now());

        when(ticketRepository.findAll()).thenReturn(List.of(firstTicket, secondTicket));

        List<TicketResponse> responses = ticketService.getAllTickets();

        assertEquals(2, responses.size());
        assertEquals("Problem z laptopem", responses.get(0).getTitle());
        assertEquals("Problem z monitorem", responses.get(1).getTitle());

        verify(ticketRepository).findAll();
    }

    @Test
    void shouldUpdateTicket() {
        Category oldCategory = new Category();
        oldCategory.setId(1L);
        oldCategory.setName("Logowanie");
        oldCategory.setDescription("Problemy z logowaniem");

        Category newCategory = new Category();
        newCategory.setId(2L);
        newCategory.setName("Sprzęt");
        newCategory.setDescription("Problemy ze sprzętem");

        Ticket existingTicket = new Ticket();
        existingTicket.setId(1L);
        existingTicket.setTitle("Stary tytuł");
        existingTicket.setDescription("Stary opis zgłoszenia.");
        existingTicket.setAuthorEmail("old@example.com");
        existingTicket.setStatus(TicketStatus.OPEN);
        existingTicket.setCategory(oldCategory);
        existingTicket.setCreatedAt(LocalDateTime.now());
        existingTicket.setUpdatedAt(LocalDateTime.now());

        TicketRequest request = new TicketRequest();
        request.setTitle("Nowy tytuł");
        request.setDescription("Nowy opis zgłoszenia.");
        request.setAuthorEmail("new@example.com");
        request.setStatus(TicketStatus.IN_PROGRESS);
        request.setCategoryId(2L);

        Ticket savedTicket = new Ticket();
        savedTicket.setId(1L);
        savedTicket.setTitle("Nowy tytuł");
        savedTicket.setDescription("Nowy opis zgłoszenia.");
        savedTicket.setAuthorEmail("new@example.com");
        savedTicket.setStatus(TicketStatus.IN_PROGRESS);
        savedTicket.setCategory(newCategory);
        savedTicket.setCreatedAt(existingTicket.getCreatedAt());
        savedTicket.setUpdatedAt(LocalDateTime.now());

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(existingTicket));
        when(categoryService.findCategoryById(2L)).thenReturn(newCategory);
        when(ticketRepository.save(existingTicket)).thenReturn(savedTicket);

        TicketResponse response = ticketService.updateTicket(1L, request);

        assertEquals("Nowy tytuł", response.getTitle());
        assertEquals("Nowy opis zgłoszenia.", response.getDescription());
        assertEquals("new@example.com", response.getAuthorEmail());
        assertEquals(TicketStatus.IN_PROGRESS, response.getStatus());
        assertEquals("Sprzęt", response.getCategory().getName());

        verify(ticketRepository).findById(1L);
        verify(categoryService).findCategoryById(2L);
        verify(ticketRepository).save(existingTicket);
    }

    @Test
    void shouldDeleteTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("Problem z logowaniem");
        ticket.setDescription("Opis zgłoszenia.");
        ticket.setAuthorEmail("user@example.com");
        ticket.setStatus(TicketStatus.OPEN);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        ticketService.deleteTicket(1L);

        verify(ticketRepository).findById(1L);
        verify(ticketRepository).delete(ticket);
    }
}