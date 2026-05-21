package org.example.userservice.repositories;

import org.example.userservice.entity.ServiceEntity;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    // ==================== 1. RECHERCHES SIMPLES ====================

    List<ServiceEntity> findByCategory(String category);
    List<ServiceEntity> findByCategoryOrderByPriceAsc(String category);
    List<ServiceEntity> findByPriceLessThanEqual(Double maxPrice);
    List<ServiceEntity> findByPriceGreaterThanEqual(Double minPrice);
    List<ServiceEntity> findByPriceBetween(Double minPrice, Double maxPrice);
    List<ServiceEntity> findByNameContainingIgnoreCase(String keyword);
    List<ServiceEntity> findByDescriptionContainingIgnoreCase(String keyword);
    List<ServiceEntity> findByName(String name);
    List<ServiceEntity> findTop10ByOrderByCreatedAtDesc();

    // ==================== 2. MÉTHODES DE COMPTAGE ====================

    Long countByCategory(String category);
    Long countByPriceGreaterThanEqual(Double minPrice);

    // ==================== 3. MÉTHODES D'EXISTENCE ====================

    boolean existsByName(String name);

    // ==================== 4. MÉTHODES DE SUPPRESSION ====================

    @Transactional
    void deleteByCategory(String category);

    // ==================== 5. REQUÊTES JPQL ====================

    @Query("SELECT s FROM ServiceEntity s ORDER BY s.price DESC")
    List<ServiceEntity> findMostExpensiveServices();

    @Query("SELECT s FROM ServiceEntity s ORDER BY s.price ASC")
    List<ServiceEntity> findCheapestServices();

    @Query("SELECT s FROM ServiceEntity s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ServiceEntity> searchInNameOrDescription(@Param("keyword") String keyword);

    @Query("SELECT s FROM ServiceEntity s WHERE s.id NOT IN (SELECT DISTINCT r.serviceId FROM Review r)")
    List<ServiceEntity> findServicesWithoutReviews();

    @Query("SELECT s, COUNT(r) as reviewCount FROM ServiceEntity s " +
            "LEFT JOIN Review r ON s.id = r.serviceId " +
            "GROUP BY s.id ORDER BY reviewCount DESC")
    List<Object[]> findServicesWithReviewCount();

    @Query("SELECT s, AVG(r.rating) as avgRating FROM ServiceEntity s " +
            "LEFT JOIN Review r ON s.id = r.serviceId " +
            "GROUP BY s.id ORDER BY avgRating DESC")
    List<Object[]> findTopRatedServices();

    @Query("SELECT s, AVG(r.rating) FROM ServiceEntity s " +
            "LEFT JOIN Review r ON s.id = r.serviceId " +
            "WHERE s.category = :category GROUP BY s.id")
    List<Object[]> findServicesByCategoryWithRatings(@Param("category") String category);

    @Query("SELECT MIN(s.price) FROM ServiceEntity s WHERE s.category = :category")
    Double getMinPriceByCategory(@Param("category") String category);

    @Query("SELECT MAX(s.price) FROM ServiceEntity s WHERE s.category = :category")
    Double getMaxPriceByCategory(@Param("category") String category);

    @Query("SELECT AVG(s.price) FROM ServiceEntity s WHERE s.category = :category")
    Double getAveragePriceByCategory(@Param("category") String category);

    // ==================== 6. REQUÊTES DE MISE À JOUR (CORRIGÉES) ====================

    @Modifying
    @Transactional
    @Query("UPDATE ServiceEntity s SET s.price = :newPrice, s.updatedAt = CURRENT_TIMESTAMP WHERE s.id = :serviceId")
    int updateServicePrice(@Param("serviceId") Long serviceId, @Param("newPrice") Double newPrice);

    // ==================== 7. REQUÊTES NATIVES SQL ====================

    @Query(value = "SELECT * FROM services WHERE category = :category", nativeQuery = true)
    List<ServiceEntity> findServicesByCategoryNative(@Param("category") String category);

    @Query(value = "SELECT category, COUNT(*), AVG(price) FROM services GROUP BY category", nativeQuery = true)
    List<Object[]> getServiceStatisticsByCategory();

    @Query(value = "SELECT * FROM services WHERE name LIKE %:keyword%", nativeQuery = true)
    List<ServiceEntity> searchByNameNative(@Param("keyword") String keyword);

    @Query(value = "SELECT is_available, COUNT(*) FROM services GROUP BY is_available", nativeQuery = true)
    List<Object[]> getServiceCountByAvailability();
    @Query("SELECT s FROM ServiceEntity s WHERE s.isAvailable = true")
    List<ServiceEntity> findByIsAvailableTrue();

    List<ServiceEntity> findByProviderId(@Nullable Long providerId);
}
