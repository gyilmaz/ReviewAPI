package com.reviews.repository.jpa;


import com.reviews.model.Comment;
import com.reviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByReview(Review review);

}
