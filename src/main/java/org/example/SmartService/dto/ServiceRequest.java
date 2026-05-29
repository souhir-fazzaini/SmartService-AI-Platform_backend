package org.example.SmartService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceRequest {

    private Integer Id;

    private String name;

    private String description;

    private Double price;

    private Double promoPrice;


    private Integer durationMinutes; // Durée en minutes

    // ========== 4. CATÉGORISATION ==========

    private String category;

    private String subCategory;

    // ========== 5. INFORMATIONS VISUELLES ==========

    private String imageUrl;

    private List<String> galleryImages; // Images supplémentaires

    // ========== 6. INFORMATIONS PRESTATAIRE ==========

    private Long providerId;

    private String providerName;

    private String providerEmail;

    private String providerPhone;

    // ========== 7. ADRESSE ==========

    private String address;

    private String city;

    private String postalCode;

    private Double latitude; // Coordonnées GPS
    private Double longitude;

    // ========== 8. DISPONIBILITÉ ==========

    private Boolean isAvailable = true;

    private Integer maxCapacity; // Nombre maximum de personnes

    // ========== 9. TAGS ET MÉTA-DONNÉES ==========

    private List<String> tags; // Tags pour la recherche

    private String featured; // "YES" ou "NO"

    private Integer deliveryDays; // Délai de livraison en jours

    // ========== 10. CONSTRUCTEURS ==========


}
