package org.cr.rheos.hybridmenu;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.cr.rheos.hybridmenu.components.NotificationCenter;
import org.cr.rheos.hybridmenu.data.DesignItem;
import org.cr.rheos.hybridmenu.data.MenuConfig;
import org.cr.rheos.hybridmenu.data.MenuItem;
import org.cr.rheos.hybridmenu.data.MenuTopItem;
import org.cr.rheos.hybridmenu.data.enums.EMenuComponents;
import org.cr.rheos.hybridmenu.data.enums.EMenuDesign;
import org.cr.rheos.hybridmenu.data.leftmenu.MenuButton;
import org.cr.rheos.hybridmenu.data.leftmenu.MenuSubMenu;
import org.cr.rheos.hybridmenu.page.DefaultPage;
import org.cr.rheos.hybridmenu.utils.ViewChangeManager;

import java.util.ArrayList;
import java.util.List;

public class HybridMenu extends VerticalLayout {
	private static final long serialVersionUID = -4055770717384786366L;
	
	public static final String CLASS_NAME = "hybridMenu";

	private ViewChangeManager viewChangeManager = new ViewChangeManager();
	private MenuConfig config = null;
	private boolean buildRunning = false;
	private boolean initNavigator = true;
	private boolean initViewChangeManager = true;

	/* Settings */
	private EMenuComponents menuComponents = null;

	/* Components */
	private Layout naviRootContent = null;
	private HorizontalLayout topMenu = null;
	private HorizontalLayout leftMenuContent = null;
	private VerticalLayout leftMenu = null;
	private NotificationCenter notificationCenter = null;
	private Label css = new Label("", ContentMode.HTML);

	private List<MenuItem> menuItemList = new ArrayList<>();

	public HybridMenu() {
		super();
		setSizeFull();
		setStyleName(CLASS_NAME);
		setMargin(false);
		setSpacing(false);
	}

	public void build() {
		if (!buildRunning) {
			if (config == null) {
				config = new MenuConfig();
			}
			if (menuComponents == null) {
				menuComponents = EMenuComponents.LEFT_WITH_TOP;
			}
			if (naviRootContent == null) {
				naviRootContent = new VerticalLayout();
			}
			
			naviRootContent.setWidth("100%");
			naviRootContent.setStyleName("contentBox");
			if (initNavigator) {
				new Navigator(UI.getCurrent(), naviRootContent);
				UI.getCurrent().getNavigator().setErrorView(DefaultPage.class);
			}
			if (initViewChangeManager) {
				UI.getCurrent().getNavigator().addViewChangeListener(new ViewChangeListener() {
					private static final long serialVersionUID = 1L;
					@Override
					public boolean beforeViewChange(ViewChangeEvent event) {
						viewChangeManager.manage(leftMenu, event);
						viewChangeManager.manage(topMenu, event);
						viewChangeManager.manage(menuItemList, event);
						return true;
					}
				});
			}
			switch (menuComponents) {
				case ONLY_TOP:
					buildTopMenu();
					topMenu.addComponent(css);
					if (notificationCenter != null) {
						topMenu.addComponent(notificationCenter);
					}
					break;
				case ONLY_LEFT:
					buildLeftMenu();
					leftMenu.addComponent(css);
					if (notificationCenter != null) {
						leftMenu.addComponent(notificationCenter);
					}
					break;
				case LEFT_WITH_TOP:
					buildTopMenu();
					buildLeftMenu();
					topMenu.addComponent(css);
					if (notificationCenter != null) {
						topMenu.addComponent(notificationCenter);
					}
					break;
				default:
					break;
			}
			buildRunning = true;
			css.setStyleName("hideDesignSettings");
			switchTheme(config.getDesignItem());
		}
	}

	private void buildLeftMenu() {
		leftMenu = new VerticalLayout();
		leftMenu.setWidth("250px");
		leftMenu.setHeight("100%");
		leftMenu.setSpacing(false);
		leftMenu.setStyleName("leftMenu");
//		leftMenu.setMargin(false); //jfs
		leftMenuContent = new HorizontalLayout();
		leftMenuContent.setSizeFull();
		leftMenuContent.setSpacing(false);
		leftMenuContent.setStyleName("centralContent");
		leftMenuContent.addComponents(leftMenu, naviRootContent);
		leftMenuContent.setExpandRatio(naviRootContent, 1);
		leftMenuContent.setComponentAlignment(leftMenu, Alignment.MIDDLE_LEFT);
		leftMenuContent.setComponentAlignment(naviRootContent, Alignment.TOP_CENTER);
		addComponent(leftMenuContent);
		setExpandRatio(leftMenuContent, 1);

	}

	private void buildTopMenu() {
		topMenu = new HorizontalLayout();
		topMenu.setHeight("50px");
		topMenu.setWidth("100%");
		topMenu.setSpacing(false);
		topMenu.setStyleName("topMenu");
		addComponent(topMenu);
		if (!menuComponents.equals(EMenuComponents.LEFT_WITH_TOP)) {
			leftMenuContent = new HorizontalLayout();
			leftMenuContent.setStyleName("centralContent");
			leftMenuContent.addComponents(naviRootContent);
			leftMenuContent.setExpandRatio(naviRootContent, 1);
			leftMenuContent.setComponentAlignment(naviRootContent, Alignment.TOP_CENTER);
	
			addComponent(leftMenuContent);
			setExpandRatio(leftMenuContent, 1);
		}

	}

	public void setLeftMenuVisible(boolean visible) {
		leftMenu.setVisible(visible);
	}

	public void setTopMenuVisible(boolean visible) {
		if (topMenu != null) {
			topMenu.setVisible(visible);
		}
	}

	public Layout getContent() {
		return naviRootContent;
	}

	public void setInitNavigator(boolean initNavigator) {
		if (!buildRunning) {
			this.initNavigator = initNavigator;
		}
	}

	public void setContent(Layout component) {
		if (!buildRunning) {
			naviRootContent = component;
		}
	}

	public void setConfig(MenuConfig config) {
		if (!buildRunning) {
			this.config = config;
		}
	}

	public void setMenuComponent(EMenuComponents menuComponents) {
		if (!buildRunning) {
			this.menuComponents = menuComponents;
		}
	}

	public void addLeftMenuButton(MenuButton menuButton) {
		leftMenu.addComponent(menuButton);
	}

	public void addLeftMenuSubMenu(MenuSubMenu menuSubMenu) {
		leftMenu.addComponent(menuSubMenu);
	}

	public void switchTheme(EMenuDesign menuDesign) {
		if (menuDesign != null) {
			removeStyleName(this.config.getMenuDesign().getName());
			this.config.setMenuDesign(menuDesign);
			css.setValue("");
			addStyleName(this.config.getMenuDesign().getName());
		}
	}
	
	public void switchTheme(DesignItem designItem) {
		if (designItem != null) {
			this.config.setDesignItem(designItem);
			if (this.config.getMenuDesign() != null) {
				removeStyleName(this.config.getMenuDesign().getName());
			}
			this.config.setMenuDesign(null);
			css.setValue("<style type=\"text/css\">" + designItem.convertToStyle() + "</style>");
		} else {
			css.setValue("");
		}
	}

	public void setLeftMenuMinimal(boolean minimal) {
		if (minimal) {
			if (!leftMenu.getStyleName().contains("minimal")) {
				leftMenu.addStyleName("minimal");
				leftMenu.setWidth("60px");
			}
		} else {
			if (leftMenu.getStyleName().contains("minimal")) {
				leftMenu.removeStyleName("minimal");
				leftMenu.setWidth("250px");
			}
		}
	}

	public boolean isLeftMenuMinimal() {
		return leftMenu.getStyleName().contains("minimal");
	}

	public MenuConfig getConfig() {
		return config;
	}

	public boolean addMenuItem(MenuItem menuItem) {
		if (menuItem != null) {
			if (menuItem instanceof MenuTopItem && menuComponents.equals(EMenuComponents.ONLY_TOP) && topMenu != null) {
				addTopMenuItem(menuItem);
				return true;
			}
			if (menuItem instanceof MenuTopItem && menuComponents.equals(EMenuComponents.LEFT_WITH_TOP) && topMenu != null) {
				addTopMenuItem(menuItem);
				return true;
			} else {
				if (leftMenu != null) {
					leftMenu.addComponent(menuItem.getComponent());
					menuItemList.add(menuItem);
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	private void addTopMenuItem(MenuItem menuItem) {
		topMenu.addComponent(menuItem.getComponent());
		topMenu.setComponentAlignment(menuItem.getComponent(), ((MenuTopItem) menuItem).getAlignment());
		menuItemList.add(menuItem);
	}
	public void setNotificationCenter(NotificationCenter notificationCenter) {
		this.notificationCenter = notificationCenter;
	}
	public NotificationCenter getNotificationCenter() {
		return notificationCenter;
	}

	public EMenuComponents getMenuComponents() {
		return menuComponents;
	}

	public void setMenuComponents(EMenuComponents menuComponents) {
		this.menuComponents = menuComponents;
	}
}