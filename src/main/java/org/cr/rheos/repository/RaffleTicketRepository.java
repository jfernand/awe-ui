package org.cr.rheos.repository;

import org.cr.rheos.entity.RaffleTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaffleTicketRepository extends JpaRepository<RaffleTicket, Long> {
}
