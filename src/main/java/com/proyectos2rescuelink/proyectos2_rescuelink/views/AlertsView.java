package com.proyectos2rescuelink.proyectos2_rescuelink.views;

import com.proyectos2rescuelink.proyectos2_rescuelink.model.Alert;
import com.proyectos2rescuelink.proyectos2_rescuelink.service.AlertService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("alerts")
@PageTitle("Alertas | RescueLink")
@PermitAll
public class AlertsView extends VerticalLayout {

    private final AlertService alertService;

    @Autowired
    public AlertsView(AlertService alertService) {
        this.alertService = alertService;
        setAlignItems(Alignment.CENTER);
        add(new H3("Alertas Activas"));

        mostrarAlertas();
    }

    private void mostrarAlertas() {
        List<Alert> alertas = alertService.getAllAlerts();

        FlexLayout contenedor = new FlexLayout();
        contenedor.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        contenedor.setJustifyContentMode(JustifyContentMode.CENTER);

        contenedor.getStyle().set("gap", "20px"); // Espaciado entre tarjetas

        contenedor.getStyle().set("padding", "10px");

        for (Alert alerta : alertas) {
            Div card = crearTarjetaAlerta(alerta);
            contenedor.add(card);
        }

        add(contenedor);
    }



    private Div crearTarjetaAlerta(Alert alerta) {
        Div card = new Div();
        card.addClassName("alert-card");
        card.getStyle().set("border", "1px solid #ccc");
        card.getStyle().set("border-radius", "10px");
        card.getStyle().set("padding", "15px");
        card.getStyle().set("width", "250px");
        card.getStyle().set("text-align", "center");
        card.getStyle().set("cursor", "pointer");
        card.getStyle().set("background-color", "#f9f9f9");
        card.getStyle().set("box-shadow", "2px 2px 10px rgba(0,0,0,0.1)");

        H3 title = new H3(alerta.getTitle());
        title.getStyle().set("margin", "0");
        title.getStyle().set("color", "#333");

        // Imagen representativa (puedes cambiar la URL por una real)
        Image image = new Image("https://imagenes.elpais.com/resizer/v2/KZH7BHDO6RDDXDXEJMUDHN2VZY.jpg?auth=bace9bd273cf89f21bb3216371f97231943c2f787467a5b0b74a63d0c1b0a691&width=1960&height=1470&focal=3340%2C3100", "Imagen de alerta");
        image.setWidth("100%");
        image.getStyle().set("border-radius", "5px");

        Button verDetalles = new Button("Ver Detalles", event -> {
            UI.getCurrent().navigate("alert/" + alerta.getId());
        });

        card.add(title, image, verDetalles);
        return card;
    }
}
