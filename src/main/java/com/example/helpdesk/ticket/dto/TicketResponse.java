package com.example.helpdesk.ticket.dto;

import com.example.helpdesk.ticket.model.TicketStatus;

import java.time.LocalDateTime;
import com.example.helpdesk.category.dto.CategoryResponse;

public class TicketResponse {

    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private String authorEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private CategoryResponse category;

    public TicketResponse(Long id, String title, String description, TicketStatus status,
                          String authorEmail, CategoryResponse category,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.authorEmail = authorEmail;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public CategoryResponse getCategory() {
        return category;
    }
}