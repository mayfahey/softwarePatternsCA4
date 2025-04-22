package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.repository.BookRepository;
import com.example.softwarePatternsCA4.strategy.BookSortContext;
import com.example.softwarePatternsCA4.strategy.SortByAuthorAscending;
import com.example.softwarePatternsCA4.strategy.SortByPriceDescending;
import com.example.softwarePatternsCA4.strategy.SortByTitleAscending;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    // Constructor injection
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Create or update a book
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get a book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Delete a book by ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Search methods
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByCategory(String category) {
        return bookRepository.findByCategoryContainingIgnoreCase(category);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> searchByPublisher(String publisher) {
        return bookRepository.findByPublisherContainingIgnoreCase(publisher);
    }

    // Strategy Pattern to sort books based on input
    public List<Book> getSortedBooks(String sortType) {
        List<Book> books = bookRepository.findAll();
        BookSortContext context = new BookSortContext();

        switch (sortType.toLowerCase()) {
            case "titleasc" -> context.setStrategy(new SortByTitleAscending());
            case "authorasc" -> context.setStrategy(new SortByAuthorAscending());
            case "pricedesc" -> context.setStrategy(new SortByPriceDescending());
            default -> throw new IllegalArgumentException("Invalid sort type: " + sortType);
        }

        return context.sortBooks(books);
    }
}
