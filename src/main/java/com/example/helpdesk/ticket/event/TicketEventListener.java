package com.example.helpdesk.ticket.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TicketEventListener {

    @EventListener
    public void handleTicketCreated(TicketCreatedEvent event) {
        System.out.println(
                "EVENT: Utworzono nowe zgłoszenie. ID: " + event.getTicketId()
                        + ", tytuł: " + event.getTitle()
                        + ", autor: " + event.getAuthorEmail()
                        + ", kategoria: " + event.getCategoryName()
        );
    }
}