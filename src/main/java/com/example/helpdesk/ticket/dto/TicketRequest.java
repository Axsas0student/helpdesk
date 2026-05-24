package com.example.helpdesk.ticket.dto;

import com.example.helpdesk.ticket.model.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class TicketRequest {

    @NotBlank(message = "Tytuł zgłoszenia jest wymagany")
    @Size(min = 3, max = 100, message = "Tytuł musi mieć od 3 do 100 znaków")
    private String title;

    @NotBlank(message = "Opis zgłoszenia jest wymagany")
    @Size(min = 10, max = 2000, message = "Opis musi mieć od 10 do 2000 znaków")
    private String description;

    @NotNull(message = "Kategoria zgłoszenia jest wymagana")
    private Long categoryId;

    private TicketStatus status;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}