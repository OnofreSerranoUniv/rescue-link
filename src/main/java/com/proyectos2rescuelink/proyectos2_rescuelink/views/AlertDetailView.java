package com.proyectos2rescuelink.proyectos2_rescuelink.views;

import com.proyectos2rescuelink.proyectos2_rescuelink.model.Alert;
import com.proyectos2rescuelink.proyectos2_rescuelink.service.AlertService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route("alert/:alertId")
@PageTitle("Detalles de la Alerta | RescueLink")
@PermitAll
public class AlertDetailView extends VerticalLayout implements BeforeEnterObserver {

    private final AlertService alertService;
    private Alert alerta;

    @Autowired
    public AlertDetailView(AlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String alertId = event.getRouteParameters().get("alertId").orElse(null);
        if (alertId != null) {
            alerta = alertService.getAlertById(Long.parseLong(alertId)).orElse(null);
            if (alerta != null) {
                mostrarDetalles();
            } else {
                add(new H3("Alerta no encontrada"));
            }
        }
    }

    private void mostrarDetalles() {
        removeAll(); // Limpiar la vista antes de agregar contenido nuevo

        H3 title = new H3(alerta.getTitle());
        title.getStyle().set("color", "#333");

        // Imagen representativa
        Image image = new Image("https://via.placeholder.com/400", "Imagen de alerta");
        image.setWidth("100%");
        image.getStyle().set("border-radius", "5px");

        Paragraph description = new Paragraph(alerta.getDescription());
        description.getStyle().set("color", "#555");

        Button backButton = new Button("Volver", event -> {
            getUI().ifPresent(ui -> ui.navigate("alerts"));
        });

        add(title, image, description, backButton);
    }
}
