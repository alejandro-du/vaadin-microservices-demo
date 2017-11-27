package com.example.newsapplication;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.springframework.hateoas.Resources;
import org.vaadin.addon.twitter.Timeline;

/**
 * @author Alejandro Duarte.
 */
@SpringUI(path = "/twitter")
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        getReconnectDialogConfiguration().setDialogText("Please wait...");
        getReconnectDialogConfiguration().setReconnectInterval(1000);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.setSpacing(false);
        setContent(horizontalLayout);

        Resources<Company> companies = Services.getCompanyService().findAll();
        companies.getContent().stream()
                .map(company -> company.getTwitterUsername())
                .map(twitterUsername -> Timeline.profile(twitterUsername))
                .forEach(horizontalLayout::addComponent);
    }

}
