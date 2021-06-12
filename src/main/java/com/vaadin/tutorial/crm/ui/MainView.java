package com.vaadin.tutorial.crm.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.entity.Contact;
import com.vaadin.tutorial.crm.backend.service.ContactService;
import com.vaadin.tutorial.crm.ui.forms.ContactForm;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {
    private final ContactService contactService;
    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private final TextField filterText = new TextField();
    private ContactForm contactForm;

    public MainView(ContactService contactService) {
        this.contactService = contactService;

        addClassName("list-view");
        setSizeFull();
        configureFilter(); // the filter is before the table
        configureGrid();

        // create form and div
        contactForm = new ContactForm();
        Div contentDiv = new Div(grid, contactForm);
        contentDiv.addClassName("content");
        contentDiv.setSizeFull();

        add(filterText, contentDiv);
        updateList();
    }

    public void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    public void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company"); // removes the default company column definition
        grid.setColumns("firstName", "lastName", "email", "status");
        // now add the company how it should appear
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");

        // set automatic sizing of columns on
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    public void updateList() {
        grid.setItems(contactService.findAll(filterText.getValue()));
    }
}