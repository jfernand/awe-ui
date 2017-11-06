package org.cr.rheos.hybridmenu.data.listeners;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import org.cr.rheos.hybridmenu.data.Notification;

import java.io.Serializable;

@FunctionalInterface
public interface INotificationClickListener extends Serializable {
	void click(LayoutClickEvent event, Notification notification);
}