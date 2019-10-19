package com.reviews.repository.mongo;


import com.reviews.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewMongoRepository extends MongoRepository<Review, Long> {
}
