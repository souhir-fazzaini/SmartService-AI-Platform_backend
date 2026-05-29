package org.example.SmartService.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "services")
@Data
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    private Double price;

    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "isAvailable")
    private Boolean isAvailable;
    @Column(name = "providerId")
    private Double providerId;
    @Column(name = "providerName")
    private String providerName;

    // Constructeurs
    public ServiceEntity() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isAvailable = true;
    }
}

