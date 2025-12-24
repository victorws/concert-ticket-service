package com.example.concertticket.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_reservation", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "concert_id", "user_email" })
})
public class TicketReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "reserved_at", nullable = false)
    private LocalDateTime reservedAt;

    // JPA requirement
    protected TicketReservation() {
    }

    public TicketReservation(Concert concert, String userEmail) {
        this.concert = concert;
        this.userEmail = userEmail;
        this.reservedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Concert getConcert() {
        return concert;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }
}