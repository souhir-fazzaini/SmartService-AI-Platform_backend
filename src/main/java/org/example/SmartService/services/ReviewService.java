package org.example.SmartService.services;

import org.example.SmartService.entity.Review;
import org.example.SmartService.entity.ServiceEntity;
import org.example.SmartService.entity.User;
import org.example.SmartService.repositories.ReviewRepository;
import org.example.SmartService.dto.ReviewRequest;
import org.example.SmartService.dto.ReviewResponse;
import org.example.SmartService.repositories.ServiceRepository;
import org.example.SmartService.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    // Injection du repository
    public ReviewService(ReviewRepository reviewRepository, ServiceRepository serviceRepository,UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.serviceRepository= serviceRepository;
        this.userRepository=userRepository;
        System.out.println("✅ ReviewService avec base de données !");
    }

    // Ajouter un avis
    public ReviewResponse addReview(Review request, Long serviceId, Long userId) {

        // 👇 charger le service
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service non trouvé"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User non trouvé"));

        Review review = new Review();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setService(service);          // 👈 ajouté
        review.setUser(user);
        review.setCreatedAt(new Date());     // 👈 ajouté
        review.setUpdatedAt(new Date());     // 👈 ajouté

        Review savedReview = reviewRepository.save(review);

        return convertToResponse(savedReview);
    }

    // Obtenir tous les avis d'un service


    // Obtenir tous les avis d'un utilisateur
    public List<ReviewResponse> getReviewsByUser(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Obtenir la note moyenne

    // Supprimer un avis
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }



    // Convertir Entity en Response
    private ReviewResponse convertToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        return response;
    }
}
