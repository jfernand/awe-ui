package org.cr.rheos.ui.models;

import org.cr.rheos.entity.Customer;
import org.cr.rheos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainModel {
    private CustomerRepository repo;

    @Autowired
    public MainModel(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> listCustomers(){
        return repo.findAll();
    }

    public Page<Customer> getCustomerPage(int pageIndex, int size){
        Pageable page = new PageRequest(pageIndex, size);

        return repo.findAll(page);
    }
}
