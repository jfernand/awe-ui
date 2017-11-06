package org.cr.rheos.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class RaffleTicket {

    @Id
    private Long id;

//    private Customer customer;
}
