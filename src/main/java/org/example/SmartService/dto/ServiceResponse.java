package org.example.SmartService.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ServiceResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
    private String category;
    private String imageUrl;
    private Double providerId;
    private String providerName;
    private Boolean isAvailable;
    private Date createdAt;
    private Double averageRating;
    private Integer totalReviews;

    // Constructeurs


    // Getters et Setters

}
