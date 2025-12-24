package com.example.concertticket.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "concert", 
    indexes = {
        @Index(
            name = "idx_concert_booking_time", 
            columnList = "booking_start, booking_end"
        )
    }
)
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int totalTickets;

    private int availableTickets;

    private LocalDateTime bookingStart;

    private LocalDateTime bookingEnd;

    @Version
    private Long version; // for optimistic locking

    protected Concert() {
    }

    public Concert(String name, int totalTickets,
            LocalDateTime bookingStart,
            LocalDateTime bookingEnd) {

        if (totalTickets <= 0) {
            throw new IllegalArgumentException("Total tickets must be greater than zero");
        }
        
        this.name = name;
        this.totalTickets = totalTickets;
        this.availableTickets = totalTickets;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    public boolean isBookingOpen(LocalDateTime now) {
        return now.isAfter(bookingStart) && now.isBefore(bookingEnd);
    }

    public void reserveTicket() {
        if (availableTickets <= 0) {
            throw new IllegalStateException("Tickets sold out");
        }
        this.availableTickets--;
    }

    // getters only (immutability-friendly)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public LocalDateTime getBookingStart() {
        return bookingStart;
    }

    public LocalDateTime getBookingEnd() {
        return bookingEnd;
    }
}