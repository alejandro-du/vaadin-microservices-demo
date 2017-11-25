package com.example.newsapplication;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.vaadin.addon.twitter.Timeline;

/**
 * @author Alejandro Duarte.
 */
@SpringUI()
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        Timeline timeline = Timeline.profile("vaadin");
        setContent(timeline);
    }

}
