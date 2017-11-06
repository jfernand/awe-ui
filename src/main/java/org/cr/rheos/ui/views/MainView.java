package org.cr.rheos.ui.views;

import org.cr.rheos.entity.Customer;
import org.cr.rheos.ui.events.RowSelected;

import java.util.List;

public interface MainView {
    void setRows(List<Customer> customers);

    interface MainViewListener {
        void rowSelected(RowSelected e);
    }
    void addListener(MainViewListener listener);
}
