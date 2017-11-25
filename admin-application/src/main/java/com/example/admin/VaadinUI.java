package com.example.admin;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.layout.impl.VerticalCrudLayout;

@SpringUI
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        getReconnectDialogConfiguration().setDialogText("Please wait...");
        getReconnectDialogConfiguration().setReconnectInterval(1000);

        Label title = new Label("Companies");
        title.addStyleName(ValoTheme.LABEL_H2);

        GridCrud<Company> crud = new GridCrud<>(Company.class, new VerticalCrudLayout());
        crud.getGrid().setHeightByRows(5);
        crud.getGrid().setColumns("name", "twitterUsername");
        crud.getCrudFormFactory().setVisibleProperties("name", "twitterUsername");
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.setClickRowToUpdate(true);
        crud.setUpdateOperationVisible(false);
        crud.setOperations(
                () -> Services.getCompanyService().findAll().getContent(),
                company -> Services.getCompanyService().add(company),
                company -> Services.getCompanyService().update(company.getId(), company),
                company -> Services.getCompanyService().delete(company.getId())
        );

        VerticalLayout mainLayout = new VerticalLayout(title, crud);
        mainLayout.setHeightUndefined();
        mainLayout.setMargin(false);
        setContent(mainLayout);
    }

}
