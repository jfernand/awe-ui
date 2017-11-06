package org.cr.rheos.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import lombok.val;
import org.cr.rheos.hybridmenu.HybridMenu;
import org.cr.rheos.hybridmenu.builder.HybridMenuBuilder;
import org.cr.rheos.hybridmenu.builder.left.LeftMenuButtonBuilder;
import org.cr.rheos.hybridmenu.components.NotificationCenter;
import org.cr.rheos.hybridmenu.data.DesignItem;
import org.cr.rheos.hybridmenu.data.MenuConfig;
import org.cr.rheos.hybridmenu.data.enums.EMenuComponents;
import org.cr.rheos.hybridmenu.data.leftmenu.MenuButton;
import org.cr.rheos.ui.models.MainModel;
import org.cr.rheos.ui.views.impl.vaadin.MainViewImpl;
import org.springframework.beans.factory.annotation.Autowired;


@SpringUI
@Title("Admin")
@Theme("menu")
public class Admin extends UI {
    private final MainModel model;
    private NotificationCenter notiCenter;

    @Autowired
    public Admin( MainModel model) {
        this.model = model;
    }

    @Override
    protected void init(VaadinRequest request) {
        MenuConfig menuConfig = new MenuConfig();
        menuConfig.setDesignItem(DesignItem.getDarkDesign());
        notiCenter = new NotificationCenter(5000);

        val layout = new VerticalLayout();
//        layout.setSizeUndefined();
//        layout.setMargin(false);

        HybridMenu menu = HybridMenuBuilder.get()
            .setContent(layout)
            .setMenuComponent(EMenuComponents.LEFT_WITH_TOP)
            .setConfig(menuConfig)
            .withNotificationCenter(notiCenter)
            .build();

        val view = new MainViewImpl();
        UI.getCurrent().getNavigator().addView("main", view);

        MenuButton homeButton = LeftMenuButtonBuilder.get()
            .withCaption("Home")
            .withIcon(VaadinIcons.HOME)
            .withNavigateTo("main")
            .build();
        menu.addLeftMenuButton(homeButton);

        setContent(menu);
        VaadinSession.getCurrent().setAttribute(HybridMenu.class, menu);

        new MainPresenter(model, view);
    }
}
