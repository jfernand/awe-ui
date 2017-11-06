package org.cr.rheos.ui.views.impl.vaadin;

import com.vaadin.data.HasValue;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.cr.rheos.entity.Customer;
import org.cr.rheos.ui.events.RowSelected;
import org.cr.rheos.ui.views.MainView;

import java.util.ArrayList;
import java.util.List;

@SpringUI
@Slf4j
public class MainViewImpl extends VerticalLayout implements View, MainView {

    private Grid<Customer> grid = new Grid<>(Customer.class);

    public MainViewImpl() {
        setSizeFull();
        setMargin(false);
        addComponent(grid);
        grid.setColumns("customerId", "firstName", "lastName", "email", "phone", "birthday");
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(this::rowSelected);
        grid.setStyleName("hybridMenu.design_dark");
//        grid.addContextClickListener(
//            event-> Notification.show(
//                render(event)
//            )
//        );
    }

    /* Only the presenter registers one listener... */
    private List<MainViewListener> listeners = new ArrayList<>();

    @Override
    public void addListener(MainViewListener listener) {
        listeners.add(listener);
    }

    public void rowSelected(HasValue.ValueChangeEvent<Customer> event) {
        listeners
            .forEach(
                listener -> listener.rowSelected(
                    new RowSelected<Customer>(event.getValue())
                )
            );
    }

    @Override
    public void setRows(List<Customer> customers) {
        log.trace("{}", customers);
        grid.setItems(customers);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Welcome to the Animal Farm");
    }

    private String render (ContextClickEvent event){
        if (event instanceof Grid.GridContextClickEvent) {
            return ((Grid.GridContextClickEvent)event).getItem().toString();
        }
        return "";
    }
}
