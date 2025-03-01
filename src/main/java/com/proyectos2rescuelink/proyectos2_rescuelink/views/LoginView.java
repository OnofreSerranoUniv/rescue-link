package com.proyectos2rescuelink.proyectos2_rescuelink.views;

import com.proyectos2rescuelink.proyectos2_rescuelink.service.SessionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Route("")
@PageTitle("Login | RescueLink")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private final SessionService sessionService;
    private final TextField usernameField = new TextField("Usuario");
    private final PasswordField passwordField = new PasswordField("Contraseña");
    private final Button loginButton = new Button("Iniciar Sesión", event -> login());
    private final Button registerButton = new Button("Registrarse", event -> register());

    public LoginView(SessionService sessionService) {
        this.sessionService = sessionService;

        H1 title = new H1("RescueLink - Login");

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        add(title, usernameField, passwordField, loginButton, registerButton);
    }

    private void login() {
        String url = "http://localhost:8081/auth/login";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> request = new HashMap<>();
        request.put("username", usernameField.getValue());
        request.put("password", passwordField.getValue());

        try {
            Map response = restTemplate.postForObject(url, request, Map.class);
            if (response == null || response.get("user") == null) {
                Notification.show("Error: Usuario o contraseña incorrectos", 3000, Notification.Position.MIDDLE);
                return;
            }
            sessionService.loginUser((String) response.get("user"));
            getUI().ifPresent(ui -> ui.navigate("home"));
        } catch (Exception e) {
            Notification.show("Error al conectar con el servidor.", 3000, Notification.Position.MIDDLE);
        }
    }

    private void register() {
        String url = "http://localhost:8081/auth/register";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> request = new HashMap<>();
        request.put("username", usernameField.getValue());
        request.put("password", passwordField.getValue());

        if (usernameField.getValue().isEmpty() || passwordField.getValue().isEmpty()) {
            Notification.show("Error: Usuario y contraseña son obligatorios", 3000, Notification.Position.MIDDLE);
            return;
        }

        try {
            Map response = restTemplate.postForObject(url, request, Map.class);
            Notification.show("Usuario registrado correctamente.", 3000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            Notification.show("Error: El usuario ya existe o hubo un problema.", 3000, Notification.Position.MIDDLE);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (sessionService.isUserLoggedIn()) {
            event.forwardTo("home");
        }
    }
}
