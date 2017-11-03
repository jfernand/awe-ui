package org.cr.rheos.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import lombok.val;
import org.cr.rheos.entity.Customer;
import org.cr.rheos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;


@SpringUI
@Title("Admin")
@Theme("valo")
public class Admin extends UI {
    private Grid<Customer> grid;
    private CustomerRepository repo;
    private CustomerForm form;

    @Autowired
    public Admin(CustomerRepository repo, CustomerForm form) {
        this.repo = repo;
        this.grid = new Grid<>(Customer.class);
        this.form = form;
    }

    @Override
    protected void init(VaadinRequest request) {
        val main = new HorizontalLayout();
        setContent(main);
        main.addComponents(grid, form);

        main.setSizeFull();
        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "email", "birthday");
        grid.setHeight(300, Unit.PIXELS);

        form.setVisible(false);
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                form.setVisible(false);
            } else {
                form.setCustomer(event.getValue());
            }
        });

        form.setChangeHandler(() -> {
//            form.setVisible(false);
            listCustomers();
        });

        main.setExpandRatio(grid, 1);
        listCustomers();
    }

    private void listCustomers() {
        grid.setItems(repo.findAll());
    }
}
