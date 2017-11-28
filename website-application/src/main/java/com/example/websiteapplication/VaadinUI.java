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
@SpringUI(path = "/")
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        getReconnectDialogConfiguration().setDialogText("Please wait...");
        getReconnectDialogConfiguration().setReconnectInterval(1000);

        BrowserFrame news = new BrowserFrame("News", new ExternalResource("http://localhost:8080/news/twitter"));
        news.setSizeFull();

        BrowserFrame admin = new BrowserFrame("Admin", new ExternalResource("http://localhost:8080/admin/companies"));
        admin.setSizeFull();

        HorizontalSplitPanel mainLayout = new HorizontalSplitPanel(admin, news);
        mainLayout.setSplitPosition(30);
        mainLayout.setSizeFull();

        setContent(mainLayout);
    }

}
