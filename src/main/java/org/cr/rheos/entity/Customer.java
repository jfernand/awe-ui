package org.cr.rheos.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Audited
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long customerId;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String birthday;
    private String phone;
    @Version
    private Timestamp version;

    @Override
    public int hashCode() {
        return customerId.hashCode();
    }

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return customerId.equals(customer.customerId);
    }

    public boolean isNew() {
        return customerId == null;
    }
}