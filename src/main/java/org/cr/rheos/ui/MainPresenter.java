package org.cr.rheos.ui;

import lombok.extern.slf4j.Slf4j;
import org.cr.rheos.ui.events.RowSelected;
import org.cr.rheos.ui.models.MainModel;
import org.cr.rheos.ui.views.MainView;

@Slf4j
public class MainPresenter implements MainView.MainViewListener {
    private final MainView view;
    private final MainModel model;

    public MainPresenter(MainModel model, MainView view) {
        this.model = model;
        this.view = view;
        view.addListener(this);
        displayCustomers();
    }

    @Override
    public void rowSelected(RowSelected e) {
        log.trace("{}", e);
    }

    private void displayCustomers() {
        view.setRows(model.listCustomers());
    }
}
