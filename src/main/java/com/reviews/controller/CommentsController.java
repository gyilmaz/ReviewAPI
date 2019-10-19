package com.reviews.controller;

import com.reviews.model.Comment;
import com.reviews.model.Review;
import com.reviews.repository.jpa.CommentRepository;
import com.reviews.repository.jpa.ReviewRepository;
import com.reviews.repository.mongo.ReviewMongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST com.reviews.controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final ReviewMongoRepository reviewMongoRepository;


    public CommentsController(ReviewRepository reviewRepository, CommentRepository commentRepository, ReviewMongoRepository reviewMongoRepository) {
        this.reviewRepository = reviewRepository;
        this.commentRepository = commentRepository;
        this.reviewMongoRepository = reviewMongoRepository;
    }

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */


    @PostMapping(value = "/reviews/{reviewId}")
    public ResponseEntity<Comment> createCommentForReview(@Valid @PathVariable("reviewId") Long reviewId, @RequestBody Comment comment) {
        Optional<Review> optional = reviewRepository.findById(reviewId);
        if (optional.isPresent()) {
            comment.setReview(optional.get());
            Review review=reviewMongoRepository.findById(reviewId).get();
            review=review.addComment(comment);
            reviewMongoRepository.save(review);
            return ResponseEntity.ok(commentRepository.save(comment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/reviews/{reviewId}")
    public ResponseEntity<List<Comment>> listCommentsForReview(@PathVariable("reviewId") Long reviewId) {
        Optional<Review> optional = reviewRepository.findById(reviewId);
        if (optional.isPresent()) {
            return ResponseEntity.ok(commentRepository.findAllByReview(optional.get()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}