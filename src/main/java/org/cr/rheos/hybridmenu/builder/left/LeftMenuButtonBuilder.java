package org.cr.rheos.hybridmenu.builder.left;

import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button.ClickListener;

import org.cr.rheos.hybridmenu.HybridMenu;
import org.cr.rheos.hybridmenu.data.MenuConfig;
import org.cr.rheos.hybridmenu.data.leftmenu.MenuButton;
import org.cr.rheos.hybridmenu.data.leftmenu.MenuSubMenu;

public class LeftMenuButtonBuilder {
	private MenuButton menuButton;

    private LeftMenuButtonBuilder(MenuButton menuButton) {
        this.menuButton = menuButton;
    }

    public static LeftMenuButtonBuilder get() {
        return new LeftMenuButtonBuilder(new MenuButton());
    }
    
    public LeftMenuButtonBuilder withIcon(Resource icon) {
    	menuButton.setIcon(icon);
    	return this;
    }
    
    public LeftMenuButtonBuilder withCaption(String caption) {
    	menuButton.setCaption(caption);
    	return this;
    }
    
    public LeftMenuButtonBuilder withUseOwnListener(boolean useOwnListener) {
    	menuButton.setUseOwnListener(useOwnListener);
    	return this;
    }
    
    public LeftMenuButtonBuilder withClickListener(ClickListener clickListener) {
    	menuButton.addClickListener(clickListener);
    	return this;
    }
    
    public LeftMenuButtonBuilder withStyleName(String style) {
    	menuButton.addStyleName(style);
    	return this;
    }
    
    public LeftMenuButtonBuilder withNavigateTo(Class<? extends View> _class) {
    	menuButton.navigateTo(_class);
    	return this;
	}
	
	public LeftMenuButtonBuilder withNavigateTo(String navigateToName) {
		menuButton.navigateTo(navigateToName);
    	return this;
	}
	
	public LeftMenuButtonBuilder withConfig(MenuConfig menuConfig) {
		menuButton.setConfig(menuConfig);
    	return this;
	}
    
    public MenuButton build() {
    	menuButton.build();
    	return menuButton;
    }
    
    public MenuButton build(MenuSubMenu menuSubMenu) {
    	menuButton.build();
    	menuSubMenu.addLeftMenuButton(menuButton);
    	return menuButton;
    }
    
    public MenuButton build(HybridMenu hybridMenu) {
    	menuButton.build();
    	hybridMenu.addLeftMenuButton(menuButton);
    	return menuButton;
    }
}