package com.example.concertticket.service;

import com.example.concertticket.entity.Concert;
import com.example.concertticket.entity.TicketReservation;
import com.example.concertticket.repository.ConcertRepository;
import com.example.concertticket.repository.TicketReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final ConcertRepository concertRepository;
    private final TicketReservationRepository ticketReservationRepository;

    public BookingService(ConcertRepository concertRepository,
            TicketReservationRepository ticketReservationRepository) {
        this.concertRepository = concertRepository;
        this.ticketReservationRepository = ticketReservationRepository;
    }

    /**
     * Books ONE ticket for a concert.
     * Concurrency-safe using pessimistic database locking.
     */
    @Transactional
    public void bookTicket(Long concertId, String userEmail) {

        Concert concert = concertRepository.findByIdForUpdate(concertId)
                .orElseThrow(() -> new IllegalArgumentException("Concert not found"));

        if (!concert.isBookingOpen(LocalDateTime.now())) {
            throw new IllegalStateException("Booking is not allowed at this time");
        }

        // Decrease available tickets
        concert.reserveTicket();

        // Save ticket reservation (timestamp handled by entity)
        TicketReservation reservation = new TicketReservation(concert, userEmail);
        ticketReservationRepository.save(reservation);
    }
}