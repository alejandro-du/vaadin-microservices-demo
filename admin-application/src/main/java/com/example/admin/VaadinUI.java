package com.example.admin;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridBasedCrudComponent;

@SpringUI
@Theme(ValoTheme.THEME_NAME)
public class VaadinUI extends UI {

    private final CompanyService companyService;

    @Autowired
    public VaadinUI(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    protected void init(VaadinRequest request) {
        GridBasedCrudComponent<Company> crud = new GridBasedCrudComponent<>(Company.class);
        crud.getGrid().setColumns("name", "phone", "email", "address");
        crud.getCrudFormFactory().setVisiblePropertyIds("name", "phone", "email", "address");
        crud.setOperations(
                () -> companyService.findAll().getContent(),
                company -> companyService.add(company),
                company -> companyService.update(company.getId(), company),
                company -> companyService.delete(company.getId())
        );

        setContent(crud);
    }

}
