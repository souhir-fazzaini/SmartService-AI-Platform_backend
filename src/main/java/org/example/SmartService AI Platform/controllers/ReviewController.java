package org.example.userservice.controllers;

import org.example.userservice.dto.ReviewRequest;
import org.example.userservice.services.ReviewService;
import com.example.serviceplatform.dto.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.addReview(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<ReviewResponse>> getByService(@PathVariable Long serviceId) {
        return ResponseEntity.ok(reviewService.getReviewsByService(serviceId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @GetMapping("/service/{serviceId}/average")
    public ResponseEntity<Double> getAverage(@PathVariable Long serviceId) {
        return ResponseEntity.ok(reviewService.getAverageRating(serviceId));
    }

    @GetMapping("/service/{serviceId}/count")
    public ResponseEntity<Long> getCount(@PathVariable Long serviceId) {
        return ResponseEntity.ok(reviewService.getReviewCount(serviceId));
    }

    @GetMapping("/user/{userId}/service/{serviceId}/exists")
    public ResponseEntity<Boolean> hasReviewed(@PathVariable Long userId, @PathVariable Long serviceId) {
        return ResponseEntity.ok(reviewService.hasUserReviewed(userId, serviceId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
