package org.cr.rheos.repository;

import org.cr.rheos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
    UUID findById(UUID id);
}
