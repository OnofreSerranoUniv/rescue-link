package com.proyectos2rescuelink.proyectos2_rescuelink.service;

import com.proyectos2rescuelink.proyectos2_rescuelink.model.Alert;
import com.proyectos2rescuelink.proyectos2_rescuelink.repository.AlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {
    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Transactional
    public Alert createAlert(String title, String description, String location, String alertType) {
        Alert alert = new Alert();
        alert.setTitle(title);
        alert.setDescription(description);
        alert.setLocation(location);
        alert.setTimestamp(LocalDateTime.now());
        alert.setAlertType(alertType);
        alert.setActive(true);

        return alertRepository.save(alert);
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    public List<Alert> getActiveAlerts() {
        return alertRepository.findByActiveTrue();
    }

    public Optional<Alert> getAlertById(Long id) {
        return alertRepository.findById(id);
    }

    @Transactional
    public Alert updateAlertStatus(Long id, boolean active) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));

        alert.setActive(active);
        return alertRepository.save(alert);
    }
}
