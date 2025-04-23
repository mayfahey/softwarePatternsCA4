package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.Review;
import com.example.softwarePatternsCA4.service.BookService;
import com.example.softwarePatternsCA4.service.CustomerProfileService;
import com.example.softwarePatternsCA4.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;
    private final CustomerProfileService customerProfileService;

    @Autowired
    public ReviewController(ReviewService reviewService,
                            BookService bookService,
                            CustomerProfileService customerProfileService) {
        this.reviewService = reviewService;
        this.bookService = bookService;
        this.customerProfileService = customerProfileService;
    }

    // Add a new review
    @PostMapping
    public ResponseEntity<?> addReview(@RequestParam Long bookId,
                                       @RequestParam Long customerId,
                                       @RequestParam int rating,
                                       @RequestParam String comment) {
        Optional<Book> bookOpt = bookService.getBookById(bookId);
        Optional<CustomerProfile> customerOpt = customerProfileService.getProfileById(customerId);

        if (bookOpt.isEmpty() || customerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid book or customer ID");
        }

        Review saved = reviewService.addReview(rating, comment, bookOpt.get(), customerOpt.get());
        return ResponseEntity.ok(saved);
    }

    // Get all reviews for a book
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getReviewsByBook(@PathVariable Long bookId) {
        Optional<Book> bookOpt = bookService.getBookById(bookId);
        return bookOpt
                .map(book -> ResponseEntity.ok(reviewService.getReviewsForBook(book)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
