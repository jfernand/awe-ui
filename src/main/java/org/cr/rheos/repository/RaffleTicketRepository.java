package org.cr.rheos.repository;

import org.cr.rheos.entity.RaffleTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaffleTicketRepository extends JpaRepository<RaffleTicket, UUID> {
}
