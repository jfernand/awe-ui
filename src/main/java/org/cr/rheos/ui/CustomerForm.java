package org.cr.rheos.ui;

import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.cr.rheos.entity.Customer;
import org.cr.rheos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

@SpringComponent
@UIScope
@Getter
@Setter
@Slf4j
public class CustomerForm extends FormLayout {
    private Customer customer;
    private final Binder<Customer> binder = new Binder<>(Customer.class);
    private CustomerRepository repo;

    private final TextField uuid = new TextField("Id");
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField email = new TextField("Email");
    private final DateField birthday = new DateField("Birthday");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Admin ui;

    @Autowired
    public CustomerForm(CustomerRepository repo) {
        this.repo = repo;
        binder.forField(birthday).withConverter(new Converter<LocalDate, String>() {
            @Override
            public Result<String> convertToModel(LocalDate value, ValueContext context) {
                if (value == null) {
                    return Result.ok("");
                }
                return Result.ok(value.toString());
            }

            @Override
            public LocalDate convertToPresentation(String value, ValueContext context) {
                if (value == null) {
                    return null;
                }
                return LocalDate.parse(value);
            }
        }).bind(Customer::getBirthday, Customer::setBirthday);
        binder.forField(uuid).withConverter(new Converter<String, UUID>() {
            @Override
            public Result<UUID> convertToModel(String value, ValueContext context) {
                return Result.ok(UUID.fromString(value));
            }

            @Override
            public String convertToPresentation(UUID value, ValueContext context) {
                return value.toString();
            }
        }).bind(Customer::getId, Customer::setId);
        binder.bindInstanceFields(this);

        val buttons = new HorizontalLayout(save, delete);
        addComponents(uuid,firstName, lastName, email, birthday, buttons);
//        setSpacing(true);
        setSizeUndefined();
        setVisible(true);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> {
            log.debug("Saving {}", customer);
            repo.save(customer);}
            );
        delete.addClickListener(e -> repo.delete(customer));
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.setBean(customer);
        delete.setVisible(customer.isNew());
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
