package org.cr.rheos.hybridmenu.utils;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import org.cr.rheos.hybridmenu.data.MenuItem;
import org.cr.rheos.hybridmenu.data.leftmenu.MenuButton;
import org.cr.rheos.hybridmenu.data.leftmenu.MenuSubMenu;

import java.util.List;

public class ViewChangeManager {
	public boolean manage(AbstractOrderedLayout menuContent, ViewChangeEvent event) {
		boolean foundActiveButton = false;
		if (menuContent != null) {
			for (int i = 0; i < menuContent.getComponentCount(); i++) {
				Component component = menuContent.getComponent(i);
				if (component instanceof MenuButton) {
					MenuButton menuButton = (MenuButton) component;
					boolean setButtonActive = false;
					if (menuButton.getNavigateTo() != null) {
						if (menuButton.getNavigateTo().equals(event.getNewView().getClass())) {
							if (menuButton.getNavigateToName() != null && !event.getViewName().isEmpty()) {
								if (menuButton.getNavigateToName().equals(event.getViewName())) {
									setButtonActive = true;
								} else if (menuButton.getNavigateToName().equals(event.getViewName() + "/" + event.getParameters())) {
									setButtonActive = true;
								}
							} else {
								setButtonActive = true;
							}
						}
					}
					if (!setButtonActive && menuButton.getNavigateToName() != null) {
						if (menuButton.getNavigateToName().equals(event.getViewName())) {
							setButtonActive = true;
						}
					}
					if (setButtonActive) {
						foundActiveButton = true;
						if (!menuButton.isActive()) {
							menuButton.setActive(true);
						}
					} else {
						if (menuButton.isActive()) {
							menuButton.setActive(false);
						}
					}
					
				} else if (component instanceof MenuSubMenu) {
					MenuSubMenu menuSubMenu = (MenuSubMenu) component;
					if (manage(menuSubMenu.getSubMenuContent(), event)) {
						foundActiveButton = true;
						if (!menuSubMenu.isOpen()) {
							menuSubMenu.setOpen(true);
						}
					} else {
						if (menuSubMenu.isOpen()) {
							menuSubMenu.setOpen(false);
						}
					}
				}
			}
		}
		return foundActiveButton;
	}
	
	public boolean manage(List<MenuItem> menuItemList, ViewChangeEvent event) {
		boolean foundActiveButton = false;
		for (MenuItem menuItem : menuItemList) {
			
		}
		/*
		if (menuContent != null) {
			for (int i = 0; i < menuContent.getComponentCount(); i++) {
				Component component = menuContent.getComponent(i);
				if (component instanceof MenuButton) {
					MenuButton menuButton = (MenuButton) component;
					boolean setButtonActive = false;
					if (menuButton.getNavigateTo() != null) {
						if (menuButton.getNavigateTo().equals(event.getNewView().getClass())) {
							if (menuButton.getNavigateToName() != null && !event.getViewName().isEmpty()) {
								if (menuButton.getNavigateToName().equals(event.getViewName())) {
									setButtonActive = true;
								} else if (menuButton.getNavigateToName().equals(event.getViewName() + "/" + event.getParameters())) {
									setButtonActive = true;
								}
							} else {
								setButtonActive = true;
							}
						}
					}
					if (!setButtonActive && menuButton.getNavigateToName() != null) {
						if (menuButton.getNavigateToName().equals(event.getViewName())) {
							setButtonActive = true;
						}
					}
					if (setButtonActive) {
						foundActiveButton = true;
						if (!menuButton.isActive()) {
							menuButton.setActive(true);
						}
					} else {
						if (menuButton.isActive()) {
							menuButton.setActive(false);
						}
					}
					
				} else if (component instanceof MenuSubMenu) {
					MenuSubMenu menuSubMenu = (MenuSubMenu) component;
					if (manage(menuSubMenu.getSubMenuContent(), event)) {
						foundActiveButton = true;
						if (!menuSubMenu.isOpen()) {
							menuSubMenu.setOpen(true);
						}
					} else {
						if (menuSubMenu.isOpen()) {
							menuSubMenu.setOpen(false);
						}
					}
				}
			}
		}
		*/
		return foundActiveButton;
	} 
}