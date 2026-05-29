package org.example.SmartService.repositories;

import org.example.SmartService.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    // 1. Trouver tous les avis d'un service (par ordre décroissant de date)

    // 2. Trouver tous les avis d'un utilisateur
    List<Review> findByUserId(Long userId);


    // 5. Calculer la note moyenne d'un service

    // 6. Compter le nombre d'avis pour un service


    // 8. Trouver les avis entre deux notes

    // 9. Supprimer tous les avis d'un service

    // 10. Supprimer tous les avis d'un utilisateur
    void deleteByUserId(Long userId);


    List<Review> findByServiceId(Long serviceId);
}
