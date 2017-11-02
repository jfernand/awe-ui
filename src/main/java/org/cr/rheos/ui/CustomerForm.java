package org.cr.rheos.ui;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.cr.rheos.entity.Customer;
import org.cr.rheos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@Getter
@Setter
public class CustomerForm extends FormLayout {
    private Customer customer;
    private final Binder<Customer> binder = new Binder<>(Customer.class);
    private CustomerRepository repo;

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField email = new TextField("Email");
    private final DateField birthdate = new DateField("Birthday");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Admin ui;

    @Autowired
    public CustomerForm(CustomerRepository repo) {
        this.repo = repo;
        binder.bindInstanceFields(this);

        val buttons = new HorizontalLayout(save, delete);
        addComponents(firstName, lastName, email, birthdate, buttons);
//        setSpacing(true);
        setSizeUndefined();
        setVisible(true);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> repo.save(customer));
        delete.addClickListener(e -> repo.delete(customer));
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.setBean(customer);
        delete.setVisible(customer.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }
}
