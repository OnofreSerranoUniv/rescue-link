package com.proyectos2rescuelink.proyectos2_rescuelink.views;

import com.proyectos2rescuelink.proyectos2_rescuelink.model.Volunteer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Route(value = "volunteers", layout = MainLayout.class)
public class VolunteersView extends VerticalLayout {
    private final Grid<Volunteer> volunteerGrid = new Grid<>(Volunteer.class);
    private final TextField userIdField = new TextField("User ID");
    private final TextField fullNameField = new TextField("Nombre Completo");
    private final TextField phoneNumberField = new TextField("Teléfono");
    private final TextField locationField = new TextField("Ubicación");
    private final TextField skillsField = new TextField("Habilidades");

    public VolunteersView() {
        setSizeFull();

        Button loadVolunteersButton = new Button("Cargar Voluntarios", event -> loadVolunteers());
        Button registerVolunteerButton = new Button("Registrar Voluntario", event -> registerVolunteer());

        volunteerGrid.setColumns("id", "user.username", "fullName", "phoneNumber", "location", "skills", "available");

        add(loadVolunteersButton, volunteerGrid, userIdField, fullNameField, phoneNumberField, locationField, skillsField, registerVolunteerButton);
    }

    private void loadVolunteers() {
        String url = "http://localhost:8081/volunteers";
        RestTemplate restTemplate = new RestTemplate();

        try {
            Volunteer[] volunteers = restTemplate.getForObject(url, Volunteer[].class);
            if (volunteers != null) {
                List<Volunteer> volunteerList = Arrays.asList(volunteers);
                volunteerGrid.setItems(volunteerList);
            } else {
                Notification.show("No hay voluntarios registrados.");
            }
        } catch (Exception e) {
            Notification.show("Error al cargar voluntarios.");
        }
    }

    private void registerVolunteer() {
        String url = "http://localhost:8081/volunteers/register";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> request = new HashMap<>();
        request.put("userId", userIdField.getValue());
        request.put("fullName", fullNameField.getValue());
        request.put("phoneNumber", phoneNumberField.getValue());
        request.put("location", locationField.getValue());
        request.put("skills", skillsField.getValue());

        try {
            Map response = restTemplate.postForObject(url, request, Map.class);
            Notification.show("Voluntario registrado con éxito: " + response.get("volunteerId"));
            loadVolunteers(); // Refrescar la lista
        } catch (Exception e) {
            Notification.show("Error al registrar voluntario.");
        }
    }
}
