package org.example.userservice.repositories;

import org.example.userservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    // 1. Trouver tous les avis d'un service (par ordre décroissant de date)
    List<Review> findByServiceIdOrderByCreatedAtDesc(Long serviceId);

    // 2. Trouver tous les avis d'un utilisateur
    List<Review> findByUserId(Long userId);

    // 3. Trouver les avis avec une note minimum pour un service
    List<Review> findByServiceIdAndRatingGreaterThanEqual(Long serviceId, Integer rating);

    // 4. Trouver les avis avec une note exacte
    List<Review> findByServiceIdAndRating(Long serviceId, Integer rating);

    // 5. Calculer la note moyenne d'un service
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.serviceId = :serviceId")
    Double getAverageRatingByServiceId(@Param("serviceId") Long serviceId);

    // 6. Compter le nombre d'avis pour un service
    Long countByServiceId(Long serviceId);

    // 7. Compter le nombre d'avis d'un utilisateur
    Long countByUserId(Long userId);

    // 8. Trouver les avis entre deux notes
    List<Review> findByServiceIdAndRatingBetween(Long serviceId, Integer minRating, Integer maxRating);

    // 9. Supprimer tous les avis d'un service
    void deleteByServiceId(Long serviceId);

    // 10. Supprimer tous les avis d'un utilisateur
    void deleteByUserId(Long userId);

    // 11. Vérifier si un utilisateur a déjà noté un service
    boolean existsByUserIdAndServiceId(Long userId, Long serviceId);

    // 12. Trouver l'avis d'un utilisateur pour un service spécifique
    Review findByUserIdAndServiceId(Long userId, Long serviceId);

    // 13. Obtenir les 5 derniers avis d'un service
    List<Review> findTop5ByServiceIdOrderByCreatedAtDesc(Long serviceId);

    // 14. Obtenir la note la plus élevée pour un service
    @Query("SELECT MAX(r.rating) FROM Review r WHERE r.serviceId = :serviceId")
    Integer getMaxRatingByServiceId(@Param("serviceId") Long serviceId);

    // 15. Obtenir la note la plus basse pour un service
    @Query("SELECT MIN(r.rating) FROM Review r WHERE r.serviceId = :serviceId")
    Integer getMinRatingByServiceId(@Param("serviceId") Long serviceId);

    // 16. Compter le nombre d'avis par note
    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.serviceId = :serviceId GROUP BY r.rating")
    List<Object[]> countReviewsByRating(@Param("serviceId") Long serviceId);
}
