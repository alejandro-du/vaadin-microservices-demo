package com.example.websiteapplication;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;
import org.springframework.core.env.Environment;

/**
 * @author Alejandro Duarte.
 */
@Route("")
public class WebsiteView extends SplitLayout {

    public WebsiteView(Environment environment) {
        UI.getCurrent().getReconnectDialogConfiguration().setDialogText("Please wait...");
        UI.getCurrent().getReconnectDialogConfiguration().setReconnectInterval(1000);

        String adminUrl = environment.getProperty("admin-application.url");
        String newsUrl = environment.getProperty("news-application.url");
        int uiSplitPosition = environment.getProperty("ui.split.position", Integer.class, 30);

        addToPrimary(new IFrame(adminUrl));
        addToSecondary(new IFrame(newsUrl));
        setSplitterPosition(uiSplitPosition);
        setSizeFull();
    }

}
