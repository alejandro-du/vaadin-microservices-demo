package com.example.admin;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.impl.crud.GridBasedCrudComponent;

import java.util.Collection;

@SpringUI
@Theme(ValoTheme.THEME_NAME)
public class VaadinUI extends UI implements GridBasedCrudComponent.GridCrudListener<Company> {

    private final CompanyService companyService;

    @Autowired
    public VaadinUI(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    protected void init(VaadinRequest request) {
        GridBasedCrudComponent companiesCrud = new GridBasedCrudComponent<>(Company.class, this);
        companiesCrud.showAllOptions();
        companiesCrud.setNewFormVisiblePropertyIds("name");
        companiesCrud.setEditFormDisabledPropertyIds("id");

        HorizontalLayout mainLayout = new HorizontalLayout(companiesCrud);
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        setContent(mainLayout);
    }

    @Override
    public Collection<Company> findAll() {
        return companyService.list().getContent();
    }

    @Override
    public void add(Company company) {
        companyService.add(company);
    }

    @Override
    public void update(Company company) {
        companyService.update(company.getId(), company);
    }

    @Override
    public void delete(Company company) {
        companyService.delete(company.getId());
    }

}
