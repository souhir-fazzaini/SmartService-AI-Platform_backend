package org.example.userservice.services;

import org.example.userservice.dto.ServiceRequest;
import org.example.userservice.dto.ServiceResponse;
import org.example.userservice.entity.ServiceEntity;
import org.example.userservice.repositories.ServiceRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceService {

    private final ServiceRepository serviceRepository;

    // Constructeur - Injection du repository
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
        System.out.println("✅ ServiceService chargé avec succès !");
    }

    /**
     * 1. Créer un nouveau service
     */
    public ServiceResponse createService(ServiceRequest request) {
        // Validation des données
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new RuntimeException("Le nom du service est obligatoire");
        }

        if (request.getPrice() == null || request.getPrice() <= 0) {
            throw new RuntimeException("Le prix doit être supérieur à 0");
        }

        // Création de l'entité
        ServiceEntity service = new ServiceEntity();
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setCategory(request.getCategory());
        service.setImageUrl(request.getImageUrl());
        service.setProviderId(Double.valueOf(request.getProviderId()));
        service.setProviderName(request.getProviderName());

        // ✅ Disponibilité (true par défaut)
        service.setIsAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true);


        service.setCreatedAt(new Date());
        service.setUpdatedAt(new Date());

        // Sauvegarde
        ServiceEntity saved = serviceRepository.save(service);
        System.out.println("✅ Service créé : " + saved.getName() + " (ID: " + saved.getId() + ")");

        return convertToResponse(saved);
    }

    /**
     * 2. Obtenir tous les services
     */
    public List<ServiceResponse> getAllServices() {
        List<ServiceEntity> services = serviceRepository.findAll();
        return services.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 3. Obtenir un service par son ID
     */
    public ServiceResponse getServiceById(Long id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service non trouvé avec l'ID: " + id));
        return convertToResponse(service);
    }

    /**
     * 4. Obtenir les services par catégorie
     */
    public List<ServiceResponse> getServicesByCategory(String category) {
        List<ServiceEntity> services = serviceRepository.findByCategory(category);
        return services.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 5. Obtenir les services par prestataire
     */

    /**
     * 6. Obtenir les services disponibles
     */

    /**
     * 7. Rechercher des services par mot-clé
     */
    public List<ServiceResponse> searchServices(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return getAllServices();
        }
        List<ServiceEntity> services = serviceRepository.findByNameContainingIgnoreCase(keyword);
        return services.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 8. Obtenir les services par prix maximum
     */
    public List<ServiceResponse> getServicesByMaxPrice(Double maxPrice) {
        List<ServiceEntity> services = serviceRepository.findByPriceLessThanEqual(maxPrice);
        return services.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 9. Mettre à jour un service
     */
    public ServiceResponse updateService(Long id, ServiceRequest request) {
        // Vérifier que le service existe
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service non trouvé avec l'ID: " + id));

        // Mettre à jour les champs
        if (request.getName() != null && !request.getName().isEmpty()) {
            service.setName(request.getName());
        }
        if (request.getDescription() != null) {
            service.setDescription(request.getDescription());
        }
        if (request.getPrice() != null && request.getPrice() > 0) {
            service.setPrice(request.getPrice());
        }

        if (request.getCategory() != null) {
            service.setCategory(request.getCategory());
        }
        if (request.getImageUrl() != null) {
            service.setImageUrl(request.getImageUrl());
        }

        service.setUpdatedAt(new Date());

        // Sauvegarde
        ServiceEntity updated = serviceRepository.save(service);
        System.out.println("✏️ Service mis à jour : " + updated.getName() + " (ID: " + updated.getId() + ")");

        return convertToResponse(updated);
    }

    /**
     * 10. Activer/Désactiver un service
     */
    public ServiceResponse toggleAvailability(Long id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service non trouvé avec l'ID: " + id));



        service.setUpdatedAt(new Date());

        ServiceEntity updated = serviceRepository.save(service);


        return convertToResponse(updated);
    }

    /**
     * 11. Mettre à jour la note moyenne d'un service
     */
    public void updateAverageRating(Long serviceId, Double newAverageRating) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service non trouvé"));

        // Note: Si vous avez un champ averageRating, décommentez ces lignes
        // service.setAverageRating(newAverageRating);
        service.setUpdatedAt(new Date());
        serviceRepository.save(service);
    }

    /**
     * 12. Compter le nombre de services par catégorie
     */
    public Long countServicesByCategory(String category) {
        return serviceRepository.countByCategory(category);
    }

    /**
     * 13. Obtenir les services populaires (top 5)
     */


    /**
     * 14. Supprimer un service
     */
    public void deleteService(Long id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service non trouvé avec l'ID: " + id));

        serviceRepository.deleteById(id);
        System.out.println("🗑️ Service supprimé : " + service.getName() + " (ID: " + id + ")");
    }

    /**
     * 15. Vérifier si un service existe
     */
    public boolean serviceExists(Long id) {
        return serviceRepository.existsById(id);
    }

    /**
     * 16. Obtenir tous les services avec leurs notes moyennes
     * (Utilisé en combinaison avec ReviewService)
     */
    public List<ServiceResponse> getAllServicesWithRatings() {
        List<ServiceEntity> services = serviceRepository.findAll();
        return services.stream()
                .map(service -> {
                    ServiceResponse response = convertToResponse(service);
                    // Ici vous pouvez ajouter la note moyenne depuis ReviewService
                    return response;
                })
                .collect(Collectors.toList());
    }

    /**
     * Convertir une entité ServiceEntity en DTO ServiceResponse
     */
    private ServiceResponse convertToResponse(ServiceEntity service) {
        ServiceResponse response = new ServiceResponse();
        response.setId(service.getId());
        response.setName(service.getName());
        response.setIsAvailable(service.getIsAvailable());
        response.setDescription(service.getDescription());
        response.setPrice(service.getPrice());
        response.setCategory(service.getCategory());
        response.setImageUrl(service.getImageUrl());
        response.setProviderId(service.getProviderId());
        response.setProviderName(service.getProviderName());

        response.setCreatedAt(service.getCreatedAt());

        // Valeurs par défaut
        response.setAverageRating(0.0);
        response.setTotalReviews(0);

        return response;
    }

    public List<ServiceResponse> getAvailableServices() {
        // Récupérer les services disponibles depuis le repository
        List<ServiceEntity> services = serviceRepository.findByIsAvailableTrue();

        // Convertir chaque entité en DTO de réponse
        return services.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public @Nullable List<ServiceResponse> getServicesByProvider(@Nullable Long providerId) {

        // Validation de l'entrée
        if (providerId == null) {
            System.out.println("⚠️ getServicesByProvider: providerId est null");
            return Collections.emptyList();
        }

        if (providerId <= 0) {
            System.out.println("⚠️ getServicesByProvider: providerId invalide: " + providerId);
            return Collections.emptyList();
        }

        try {
            // Recherche des services
            List<ServiceEntity> services = serviceRepository.findByProviderId(providerId);

            if (services == null || services.isEmpty()) {
                System.out.println("📋 Aucun service trouvé pour le prestataire ID: " + providerId);
                return Collections.emptyList();
            }

            System.out.println("✅ " + services.size() + " service(s) trouvé(s) pour le prestataire ID: " + providerId);

            // Conversion et retour
            return services.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la recherche des services pour providerId: " + providerId);
            e.printStackTrace();
            return Collections.emptyList();
        }

    }
}
