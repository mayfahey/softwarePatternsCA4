package com.example.softwarePatternsCA4.repository;

import com.example.softwarePatternsCA4.entity.Review;
import com.example.softwarePatternsCA4.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBook(Book book);
}
