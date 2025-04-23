package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.Review;
import com.example.softwarePatternsCA4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(int rating, String comment, Book book, CustomerProfile customer) {
        Review review = new Review(rating, comment, book, customer);
        review.setReviewDate(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForBook(Book book) {
        return reviewRepository.findByBook(book);
    }
}
