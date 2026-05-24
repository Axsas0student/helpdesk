package com.example.helpdesk.ticket.dto;

import com.example.helpdesk.ticket.model.TicketStatus;
import jakarta.validation.constraints.NotNull;

public class TicketStatusRequest {

    @NotNull(message = "Status zgłoszenia jest wymagany")
    private TicketStatus status;

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}