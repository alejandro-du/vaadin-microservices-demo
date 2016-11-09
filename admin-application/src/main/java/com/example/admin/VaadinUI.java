package com.example.admin;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.impl.crud.GridBasedCrudComponent;

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
        crud.setVisiblePropertyIds("name", "phone", "email", "address");
        crud.setOperations(
                company -> companyService.add(company),
                company -> companyService.update(company.getId(), company),
                company -> companyService.delete(company.getId()),
                () -> companyService.findAll().getContent());

        HorizontalLayout mainLayout = new HorizontalLayout(crud);
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        setContent(mainLayout);
    }

}
