package com.example.concertticket.repository;

import com.example.concertticket.entity.Concert;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Concert c WHERE c.id = :id")
    Optional<Concert> findByIdForUpdate(@Param("id") Long id);

    List<Concert> findByAvailableTicketsGreaterThan(int count);
}