package com.example.newsapplication;

import com.vaadin.flow.component.ReconnectDialogConfiguration;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.hateoas.CollectionModel;
import org.vaadin.addon.twitter.Timeline;

/**
 * @author Alejandro Duarte.
 */
@Route("")
public class NewsView extends HorizontalLayout {

    public NewsView() {
        ReconnectDialogConfiguration configuration = UI.getCurrent().getReconnectDialogConfiguration();
        configuration.setDialogText("Please wait...");
        configuration.setReconnectInterval(1000);

        setWidth("100%");
        setSpacing(false);

        CollectionModel<Company> companies = Services.getCompanyService().findAll();
        companies.getContent()
                .stream()
                .map(company -> company.getTwitterUsername())
                .map(twitterUsername -> Timeline.profile(twitterUsername))
                .forEach(this::add);
    }

}
