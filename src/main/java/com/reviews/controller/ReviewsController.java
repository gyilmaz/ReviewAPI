package com.reviews.controller;

import com.reviews.model.Product;
import com.reviews.model.Review;
import com.reviews.repository.jpa.ProductRepository;
import com.reviews.repository.jpa.ReviewRepository;
import com.reviews.repository.mongo.ReviewMongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Spring REST com.reviews.controller for working with review entity.
 */
@RestController
public class ReviewsController {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMongoRepository reviewMongoRepository;

    public ReviewsController(ReviewRepository reviewRepository, ProductRepository productRepository, ReviewMongoRepository reviewMongoRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.reviewMongoRepository = reviewMongoRepository;
    }

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @PostMapping(value = "/reviews/products/{productId}")
    public ResponseEntity<?> createReviewForProduct(@Valid @PathVariable("productId") Long productId, @RequestBody Review review) {
        Optional<Product> optional = productRepository.findById(productId);
        if (optional.isPresent()) {
            review.setProduct(optional.get());
            reviewRepository.save(review);
           return ResponseEntity.ok(reviewMongoRepository.save(review));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @GetMapping(value = "/reviews/products/{productId}")
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Long productId) {
        Optional<Product> optional = productRepository.findById(productId);
        if (optional.isPresent()) {
            return ResponseEntity.ok(reviewMongoRepository.findAll().stream()
                    .filter(each-> each.getProduct().getId()==productId)
                    .collect(Collectors.toList()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}