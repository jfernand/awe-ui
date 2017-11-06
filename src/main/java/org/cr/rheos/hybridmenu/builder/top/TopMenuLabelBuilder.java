package org.cr.rheos.hybridmenu.builder.top;

import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;

import org.cr.rheos.hybridmenu.HybridMenu;
import org.cr.rheos.hybridmenu.data.enums.EMenuStyle;
import org.cr.rheos.hybridmenu.data.top.TopMenuLabel;

public class TopMenuLabelBuilder {
	private TopMenuLabel topMenuLabel;

    private TopMenuLabelBuilder(TopMenuLabel topMenuLabel) {
        this.topMenuLabel = topMenuLabel;
    }

    public static TopMenuLabelBuilder get() {
        return new TopMenuLabelBuilder(new TopMenuLabel());
    }
    
    public TopMenuLabelBuilder setIcon(Resource icon) {
    	topMenuLabel.setIcon(icon);
    	return this;
    }
    
    public TopMenuLabelBuilder setCaption(String caption) {
    	topMenuLabel.setCaption(caption);
    	return this;
    }
    
    public TopMenuLabelBuilder addStyleName(String style) {
    	topMenuLabel.addStyleName(style);
    	return this;
    }
    
    public TopMenuLabelBuilder addStyleName(EMenuStyle style) {
    	topMenuLabel.addStyleName(style.getName());
    	return this;
    }
	
	public TopMenuLabelBuilder setAlignment(Alignment alignment) {
		topMenuLabel.setAlignment(alignment);
    	return this;
	}
    
    public TopMenuLabel build() {
    	topMenuLabel.build();
    	return topMenuLabel;
    }
    
    public TopMenuLabel build(HybridMenu hybridMenu) {
    	topMenuLabel.build();
    	hybridMenu.addMenuItem(topMenuLabel);
    	return topMenuLabel;
    }
}