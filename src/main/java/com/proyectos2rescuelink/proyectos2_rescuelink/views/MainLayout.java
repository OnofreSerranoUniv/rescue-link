package com.proyectos2rescuelink.proyectos2_rescuelink.views;

import com.proyectos2rescuelink.proyectos2_rescuelink.service.SessionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {
    private final SessionService sessionService;

    public MainLayout(SessionService sessionService) {
        this.sessionService = sessionService;

        H1 title = new H1("RescueLink");
        title.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Inicio", HomeView.class, VaadinIcon.HOME.create()));
        nav.addItem(new SideNavItem("Voluntarios", VolunteersView.class, VaadinIcon.USERS.create()));
        nav.addItem(new SideNavItem("Alertas", AlertsView.class, VaadinIcon.BELL.create()));

        Button logoutButton = new Button("Cerrar Sesión", event -> logout());

        VerticalLayout sidebar = new VerticalLayout(title, nav, logoutButton);
        sidebar.setWidth("250px");
        sidebar.setPadding(true);
        sidebar.setSpacing(true);

        addToDrawer(sidebar);
    }

    private void logout() {
        sessionService.logoutUser();
        getUI().ifPresent(ui -> ui.navigate(""));
    }
}
