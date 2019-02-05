package com.example.websiteapplication;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;

@Tag("iframe")
public class IFrame extends Component implements HasSize {

    public IFrame(String url) {
        getElement().setAttribute("src", url);
        getElement().setAttribute("frameBorder", "0");
        setSizeFull();
    }

}
