package com.example.helpdesk.ticket.event;

import com.example.helpdesk.ticket.model.Ticket;

public class TicketCreatedEvent {

    private final Long ticketId;
    private final String title;
    private final String authorEmail;
    private final String categoryName;

    public TicketCreatedEvent(Ticket ticket) {
        this.ticketId = ticket.getId();
        this.title = ticket.getTitle();
        this.authorEmail = ticket.getAuthorEmail();
        this.categoryName = ticket.getCategory() != null
                ? ticket.getCategory().getName()
                : null;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getCategoryName() {
        return categoryName;
    }
}