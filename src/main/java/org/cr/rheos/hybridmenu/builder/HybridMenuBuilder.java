package org.cr.rheos.hybridmenu.builder;

import com.vaadin.ui.Layout;

import org.cr.rheos.hybridmenu.HybridMenu;
import org.cr.rheos.hybridmenu.components.NotificationCenter;
import org.cr.rheos.hybridmenu.data.MenuConfig;
import org.cr.rheos.hybridmenu.data.enums.EMenuComponents;

public class HybridMenuBuilder {
	private HybridMenu hybridMenu;

    private HybridMenuBuilder(HybridMenu hybridMenu) {
        this.hybridMenu = hybridMenu;
    }

    public static HybridMenuBuilder get() {
        return new HybridMenuBuilder(new HybridMenu());
    }
    
    public HybridMenuBuilder setContent(Layout component) {
    	hybridMenu.setContent(component);
    	return this;
    }
    
    public HybridMenuBuilder setMenuComponent(EMenuComponents menuComponents) {
    	hybridMenu.setMenuComponent(menuComponents);
    	return this;
    }
    
    public HybridMenuBuilder setConfig(MenuConfig config) {
    	hybridMenu.setConfig(config);
    	return this;
    }
    
    public HybridMenuBuilder withNotificationCenter(NotificationCenter notificationCenter) {
    	hybridMenu.setNotificationCenter(notificationCenter);
    	return this;
    }
    
    public HybridMenu build() {
    	hybridMenu.build();
    	return hybridMenu;
    }
}