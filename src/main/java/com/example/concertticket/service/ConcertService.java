package com.example.concertticket.service;

import com.example.concertticket.dto.*;
import com.example.concertticket.entity.Concert;
import com.example.concertticket.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public List<ConcertResponse> getAvailableConcerts() {
        return concertRepository.findByAvailableTicketsGreaterThan(0)
                .stream()
                .map(c -> new ConcertResponse(
                        c.getId(),
                        c.getName(),
                        c.getAvailableTickets(),
                        c.getBookingStart(),
                        c.getBookingEnd()))
                .toList();
    }

    public Concert createConcert(CreateConcertRequest req) {
        Concert concert = new Concert(
                req.name,
                req.totalTickets,
                req.bookingStart,
                req.bookingEnd);
        return concertRepository.save(concert);
    }
}