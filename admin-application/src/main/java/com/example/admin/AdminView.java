package com.example.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.hateoas.Resources;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.layout.impl.VerticalCrudLayout;

import java.util.Collection;
import java.util.Collections;

@Route("")
public class AdminView extends VerticalLayout {

    private GridCrud<Company> crud = new GridCrud<>(Company.class, new VerticalCrudLayout());

    public AdminView() {
        UI.getCurrent().getReconnectDialogConfiguration().setDialogText("Please wait...");
        UI.getCurrent().getReconnectDialogConfiguration().setReconnectInterval(1000);

        H2 title = new H2("Companies");

        crud.getGrid().setColumns("name", "twitterUsername");
        crud.getCrudFormFactory().setVisibleProperties("name", "twitterUsername");
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.setClickRowToUpdate(true);
        crud.setUpdateOperationVisible(false);
        crud.setFindAllOperation(this::findAll);
        crud.setAddOperation(company -> Services.getCompanyService().add(company));
        crud.setUpdateOperation(company -> Services.getCompanyService().update(company.getId(), company));
        crud.setDeleteOperation(company -> Services.getCompanyService().delete(company.getId()));

        add(title, crud);
        setMargin(false);
        setHeight(null);
    }

    private Collection<Company> findAll() {
        Resources<Company> resources = Services.getCompanyService().findAll();

        Collection<Company> companies = Collections.emptyList();

        if (resources != null) {
            companies = resources.getContent();
            if (!companies.isEmpty()) {
                crud.getGrid().setHeightByRows(true);
            }
        } else {
            Notification.show("An error occurred. Please try again later.");
        }

        return companies;
    }

}
