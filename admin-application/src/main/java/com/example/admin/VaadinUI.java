package com.example.admin;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@SpringUI
@Theme(ValoTheme.THEME_NAME)
public class VaadinUI extends UI {

    private final CompanyService companyService;

    private Grid grid = new Grid();

    @Autowired
    public VaadinUI(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    protected void init(VaadinRequest request) {
        Collection<Company> companies = companyService.list().getContent();
        grid.setContainerDataSource(new BeanItemContainer<>(Company.class, companies));
        grid.setColumns("name");
        setContent(grid);
    }

}
