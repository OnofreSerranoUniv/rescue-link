package com.proyectos2rescuelink.proyectos2_rescuelink.service;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private static final String USER_SESSION_KEY = "loggedUser";

    public void loginUser(String username) {
        VaadinSession.getCurrent().setAttribute(USER_SESSION_KEY, username);
    }

    public void logoutUser() {
        VaadinSession.getCurrent().setAttribute(USER_SESSION_KEY, null);
        VaadinSession.getCurrent().close();
    }

    public String getLoggedUser() {
        return (String) VaadinSession.getCurrent().getAttribute(USER_SESSION_KEY);
    }

    public boolean isUserLoggedIn() {
        return getLoggedUser() != null;
    }
}
