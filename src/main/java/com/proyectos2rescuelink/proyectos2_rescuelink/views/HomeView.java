package com.proyectos2rescuelink.proyectos2_rescuelink.views;

import com.proyectos2rescuelink.proyectos2_rescuelink.service.SessionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

@Route(value = "home", layout = MainLayout.class)
@PageTitle("Inicio | RescueLink")
public class HomeView extends VerticalLayout implements BeforeEnterObserver {
    private final SessionService sessionService;

    public HomeView(SessionService sessionService) {
        this.sessionService = sessionService;

        H1 title = new H1("RescueLink - Panel de Control");

        Button volunteersButton = new Button("Gestión de Voluntarios", event -> getUI().ifPresent(ui -> ui.navigate("volunteers")));
        Button alertsButton = new Button("Gestión de Alertas", event -> getUI().ifPresent(ui -> ui.navigate("alerts")));
        Button logoutButton = new Button("Cerrar Sesión", event -> logout());

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        add(title, volunteersButton, alertsButton, logoutButton);
    }

    private void logout() {
        sessionService.logoutUser();
        getUI().ifPresent(ui -> ui.navigate(""));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!sessionService.isUserLoggedIn()) {
            event.forwardTo("");
        }
    }
}
