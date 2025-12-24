package com.example.concertticket.dto;

import java.time.LocalDateTime;

public class ConcertResponse {
    public Long id;
    public String name;
    public int availableTickets;
    public LocalDateTime bookingStart;
    public LocalDateTime bookingEnd;

    public ConcertResponse(
        Long id,
        String name,
        int availableTickets,
        LocalDateTime bookingStart,
        LocalDateTime bookingEnd) {
            this.id = id;
            this.name = name;
            this.availableTickets = availableTickets;
            this.bookingStart = bookingStart;
            this.bookingEnd = bookingEnd;
    }
}