package org.example.userservice.services;

import org.example.userservice.entity.Review;
import org.example.userservice.repositories.ReviewRepository;
import org.example.userservice.dto.ReviewRequest;
import com.example.serviceplatform.dto.ReviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // Injection du repository
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        System.out.println("✅ ReviewService avec base de données !");
    }

    // Ajouter un avis
    public ReviewResponse addReview(ReviewRequest request) {
        Review review = new Review();
        review.setUserId(request.getUserId());
        review.setServiceId(request.getServiceId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);

        return convertToResponse(savedReview);
    }

    // Obtenir tous les avis d'un service
    public List<ReviewResponse> getReviewsByService(Long serviceId) {
        List<Review> reviews = reviewRepository.findByServiceIdOrderByCreatedAtDesc(serviceId);
        return reviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Obtenir tous les avis d'un utilisateur
    public List<ReviewResponse> getReviewsByUser(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Obtenir la note moyenne
    public Double getAverageRating(Long serviceId) {
        Double avg = reviewRepository.getAverageRatingByServiceId(serviceId);
        return avg != null ? avg : 0.0;
    }

    // Supprimer un avis
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    // Vérifier si l'utilisateur a déjà noté
    public boolean hasUserReviewed(Long userId, Long serviceId) {
        return reviewRepository.existsByUserIdAndServiceId(userId, serviceId);
    }

    // Obtenir le nombre d'avis
    public Long getReviewCount(Long serviceId) {
        return reviewRepository.countByServiceId(serviceId);
    }

    // Convertir Entity en Response
    private ReviewResponse convertToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setServiceId(review.getServiceId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        return response;
    }
}
