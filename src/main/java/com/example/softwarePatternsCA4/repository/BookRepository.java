package com.example.softwarePatternsCA4.repository;

import com.example.softwarePatternsCA4.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByCategoryContainingIgnoreCase(String category);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByPublisherContainingIgnoreCase(String publisher);
}
