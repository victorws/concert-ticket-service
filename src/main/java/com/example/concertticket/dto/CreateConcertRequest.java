package com.example.concertticket.dto;

import java.time.LocalDateTime;

public class CreateConcertRequest {
    public String name;
    public int totalTickets;
    public LocalDateTime bookingStart;
    public LocalDateTime bookingEnd;
}