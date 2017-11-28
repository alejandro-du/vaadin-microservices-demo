package com.example.admin;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.hateoas.Resources;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.layout.impl.VerticalCrudLayout;

import java.util.Collection;
import java.util.Collections;

@SpringUI(path = "/")
public class VaadinUI extends UI implements CrudListener<Company> {

    private GridCrud<Company> crud = new GridCrud<>(Company.class, new VerticalCrudLayout());

    @Override
    protected void init(VaadinRequest request) {
        getReconnectDialogConfiguration().setDialogText("Please wait...");
        getReconnectDialogConfiguration().setReconnectInterval(1000);

        Label title = new Label("Companies");
        title.addStyleName(ValoTheme.LABEL_H2);

        crud.getGrid().setColumns("name", "twitterUsername");
        crud.getCrudFormFactory().setVisibleProperties("name", "twitterUsername");
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.setClickRowToUpdate(true);
        crud.setUpdateOperationVisible(false);
        crud.setCrudListener(this);

        VerticalLayout mainLayout = new VerticalLayout(title, crud);
        mainLayout.setHeightUndefined();
        mainLayout.setMargin(false);
        setContent(mainLayout);
    }

    @Override
    public Collection<Company> findAll() {
        Resources<Company> resources = Services.getCompanyService().findAll();

        Collection<Company> companies = Collections.emptyList();

        if (resources != null) {
            companies = resources.getContent();
            if (!companies.isEmpty()) {
                crud.getGrid().setHeightByRows(companies.size());
            }
        } else {
            Notification.show("An error occurred. Please try again later.", Notification.Type.ERROR_MESSAGE);
        }

        return companies;
    }

    @Override
    public Company add(Company company) {
        return Services.getCompanyService().add(company);
    }

    @Override
    public Company update(Company company) {
        return Services.getCompanyService().update(company.getId(), company);
    }

    @Override
    public void delete(Company company) {
        Services.getCompanyService().delete(company.getId());
    }

}
