package org.example.SmartService.controllers;

import org.example.SmartService.dto.ServiceRequest;
import org.example.SmartService.dto.ServiceResponse;
import org.example.SmartService.services.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
        System.out.println("✅ ServiceController chargé !");
    }

    // ==================== 1. ROUTES SPÉCIFIQUES (SANS ID) ====================
    // ⚠️ Ces routes doivent être AVANT celles avec @PathVariable

    /**
     * Obtenir les services disponibles
     * GET /api/services/available
     */
    @GetMapping("/available")
    public ResponseEntity<List<ServiceResponse>> getAvailableServices() {
        return ResponseEntity.ok(serviceService.getAvailableServices());
    }



    /**
     * Rechercher des services par mot-clé
     * GET /api/services/search?keyword=xxx
     */
    @GetMapping("/search")
    public ResponseEntity<List<ServiceResponse>> searchServices(@RequestParam String keyword) {
        return ResponseEntity.ok(serviceService.searchServices(keyword));
    }

    /**
     * Obtenir les services par catégorie
     * GET /api/services/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ServiceResponse>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(serviceService.getServicesByCategory(category));
    }

    /**
     * Obtenir les services par prestataire
     * GET /api/services/provider/{providerId}
     */
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ServiceResponse>> getByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(serviceService.getServicesByProvider(providerId));
    }

    // ==================== 2. ROUTES AVEC ID (après les routes spécifiques) ====================

    /**
     * Obtenir tous les services
     * GET /api/services
     */
    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    /**
     * Obtenir un service par ID
     * GET /api/services/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    /**
     * Créer un service
     * POST /api/services
     */
    @PostMapping
    public ResponseEntity<ServiceResponse> createService(@RequestBody ServiceRequest request) {
        ServiceResponse response = serviceService.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Mettre à jour un service
     * PUT /api/services/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable Long id,
                                                         @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(serviceService.updateService(id, request));
    }

    /**
     * Changer disponibilité
     * PATCH /api/services/{id}/toggle
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ServiceResponse> toggleAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.toggleAvailability(id));
    }

    /**
     * Supprimer un service
     * DELETE /api/services/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
