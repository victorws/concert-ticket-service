package com.example.concertticket.service;

import com.example.concertticket.entity.Concert;
import com.example.concertticket.repository.ConcertRepository;
import com.example.concertticket.repository.TicketReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookingServiceTest {

    @Autowired
    BookingService bookingService;

    @Autowired
    ConcertRepository concertRepository;

    @Autowired
    TicketReservationRepository reservationRepository;

    @Test
    void bookTicket_decreasesAvailableTickets_andCreatesReservation() {

        long reservationsBefore = reservationRepository.count();

        Concert concert = concertRepository.save(
                new Concert(
                        "Test Concert",
                        1,
                        LocalDateTime.now().minusMinutes(5),
                        LocalDateTime.now().plusMinutes(5)));

        bookingService.bookTicket(concert.getId(), "test@example.com");

        Concert updated = concertRepository.findById(concert.getId()).orElseThrow();
        assertEquals(0, updated.getAvailableTickets());

        long reservationsAfter = reservationRepository.count();
        assertEquals(reservationsBefore + 1, reservationsAfter);
    }

    @Test
    void bookTicket_fails_whenTicketsAreSoldOut() {

        Concert concert = concertRepository.save(
                new Concert(
                        "Sold Out Concert",
                        1,
                        LocalDateTime.now().minusMinutes(5),
                        LocalDateTime.now().plusMinutes(5)));

        // First booking succeeds (ticket becomes 0)
        bookingService.bookTicket(concert.getId(), "first@example.com");

        // Second booking should fail
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> bookingService.bookTicket(concert.getId(), "second@example.com"));

        assertEquals("Tickets sold out", exception.getMessage());
    }

}