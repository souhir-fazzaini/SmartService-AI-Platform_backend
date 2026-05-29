package org.example.SmartService.controllers;

import org.example.SmartService.entity.Review;
import org.example.SmartService.repositories.ReviewRepository;
import org.example.SmartService.services.ReviewService;
import org.example.SmartService.dto.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.SmartService.service.AiService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final AiService aiService;


    public ReviewController(ReviewService reviewService,ReviewRepository reviewRepository, AiService aiService) {
        this.reviewService = reviewService;
        this.reviewRepository =reviewRepository;
        this.aiService = aiService;

    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @RequestBody Review review,
            @RequestParam Long serviceId,
            @RequestParam Long userId) { // 👈 ajouté

        review.setCreatedAt(new Date());
        review.setUpdatedAt(new Date());

        return ResponseEntity.ok(reviewService.addReview(review, serviceId, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }



    @GetMapping("/{serviceId}/summary")
    public ResponseEntity<String> getReviewSummary(@PathVariable Long serviceId) {
        List<Review> reviews = reviewRepository.findByServiceId(serviceId);

        if (reviews.isEmpty()) {
            return ResponseEntity.ok("Aucun avis pour ce service.");
        }

        String summary = aiService.summarizeReviews(reviews);
        return ResponseEntity.ok(summary);
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
