package com.example.newsapplication;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.hateoas.Resources;
import org.vaadin.addon.twitter.Timeline;

/**
 * @author Alejandro Duarte.
 */
@Route("")
public class NewsView extends HorizontalLayout {

    public NewsView() {
        UI.getCurrent().getReconnectDialogConfiguration().setDialogText("Please wait...");
        UI.getCurrent().getReconnectDialogConfiguration().setReconnectInterval(1000);

        setWidth("100%");
        setSpacing(false);

        Resources<Company> companies = Services.getCompanyService().findAll();
        companies.getContent()
                .stream()
                .map(company -> company.getTwitterUsername())
                .map(twitterUsername -> Timeline.profile(twitterUsername))
                .forEach(this::add);
    }

}
