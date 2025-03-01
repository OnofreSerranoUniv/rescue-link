package com.proyectos2rescuelink.proyectos2_rescuelink.controller;

import com.proyectos2rescuelink.proyectos2_rescuelink.model.Alert;
import com.proyectos2rescuelink.proyectos2_rescuelink.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/alerts")
public class AlertController {
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAlert(@RequestBody Map<String, String> request) {
        try {
            String title = request.get("title");
            String description = request.get("description");
            String location = request.get("location");
            String alertType = request.get("alertType");

            Alert alert = alertService.createAlert(title, description, location, alertType);
            return ResponseEntity.ok(Map.of("message", "Alerta creada con éxito", "alertId", alert.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Alert>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Alert>> getActiveAlerts() {
        return ResponseEntity.ok(alertService.getActiveAlerts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlertById(@PathVariable Long id) {
        Optional<Alert> alert = alertService.getAlertById(id);
        return alert.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateAlertStatus(@PathVariable Long id, @RequestParam boolean active) {
        try {
            Alert alert = alertService.updateAlertStatus(id, active);
            return ResponseEntity.ok(Map.of("message", "Estado de la alerta actualizado", "active", alert.isActive()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
