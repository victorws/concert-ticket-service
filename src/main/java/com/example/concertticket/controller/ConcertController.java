package com.example.concertticket.controller;

import com.example.concertticket.dto.CreateConcertRequest;
import com.example.concertticket.entity.Concert;
import com.example.concertticket.service.BookingService;
import com.example.concertticket.dto.BookTicketRequest;
import com.example.concertticket.dto.ConcertResponse;
import com.example.concertticket.service.ConcertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;
    private final BookingService bookingService;

    public ConcertController(ConcertService concertService,
            BookingService bookingService) {
        this.concertService = concertService;
        this.bookingService = bookingService;
    }

    // GET /api/concerts
    @GetMapping
    public List<ConcertResponse> getAvailableConcerts() {
        return concertService.getAvailableConcerts();
    }

    // POST /api/concerts/{id}/book
    @PostMapping("/{id}/book")
    public String bookTicket(@PathVariable Long id, @RequestBody BookTicketRequest request) {
        bookingService.bookTicket(id, request.getUserEmail());
        return "Ticket booked successfully";
    }

    // POST /api/concerts
    @PostMapping
    public Concert createConcert(@RequestBody CreateConcertRequest request) {
        return concertService.createConcert(request);
    }
}