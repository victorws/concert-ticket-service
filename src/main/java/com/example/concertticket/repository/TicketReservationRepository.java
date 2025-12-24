package com.example.concertticket.repository;

import com.example.concertticket.entity.TicketReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketReservationRepository
    extends JpaRepository<TicketReservation, Long> {
}