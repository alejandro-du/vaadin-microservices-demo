package com.example.websiteapplication;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;

/**
 * @author Alejandro Duarte.
 */
@SpringUI
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        BrowserFrame news = new BrowserFrame("News", new ExternalResource("http://localhost:8080/news"));
        news.setSizeFull();

        BrowserFrame admin = new BrowserFrame("Admin", new ExternalResource("http://localhost:8080/admin"));
        admin.setSizeFull();

        HorizontalSplitPanel mainLayout = new HorizontalSplitPanel(admin, news);
        mainLayout.setSplitPosition(30);
        mainLayout.setSizeFull();

        setContent(mainLayout);
    }

}
