package com.example.admin;

import com.vaadin.flow.component.ReconnectDialogConfiguration;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.hateoas.CollectionModel;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.layout.impl.VerticalCrudLayout;

import java.util.Collection;
import java.util.Collections;

@Route("")
public class AdminView extends VerticalLayout implements CrudListener<Company> {

    private GridCrud<Company> crud = new GridCrud<>(Company.class, new VerticalCrudLayout());

    public AdminView() {
        ReconnectDialogConfiguration configuration = UI.getCurrent().getReconnectDialogConfiguration();
        configuration.setDialogText("Please wait...");
        configuration.setReconnectInterval(1000);

        H2 title = new H2("Companies");

        crud.getGrid().setColumns("name", "twitterUsername");
        crud.getCrudFormFactory().setVisibleProperties("name", "twitterUsername");
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.setClickRowToUpdate(true);
        crud.setUpdateOperationVisible(false);
        crud.setFindAllOperation(this::findAll);
        crud.setCrudListener(this);

        add(title, crud);
        setMargin(false);
        setHeight(null);
    }

    @Override
    public Collection<Company> findAll() {
        CollectionModel<Company> resources = Services.getCompanyService().findAll();
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
