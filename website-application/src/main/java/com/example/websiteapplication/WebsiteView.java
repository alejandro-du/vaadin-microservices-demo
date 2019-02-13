package com.example.websiteapplication;

import com.vaadin.flow.component.ReconnectDialogConfiguration;
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
        ReconnectDialogConfiguration configuration = UI.getCurrent().getReconnectDialogConfiguration();
        configuration.setDialogText("Please wait...");
        configuration.setReconnectInterval(1000);

        String adminUrl = environment.getProperty("admin-application.url");
        String newsUrl = environment.getProperty("news-application.url");
        int uiSplitPosition = environment.getProperty("ui.split.position", Integer.class, 30);

        addToPrimary(new IFrame(adminUrl));
        addToSecondary(new IFrame(newsUrl));
        setSplitterPosition(uiSplitPosition);
        setSizeFull();
    }

}
