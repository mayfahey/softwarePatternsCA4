package com.example.softwarePatternsCA4.strategy;

import com.example.softwarePatternsCA4.entity.Book;

import java.util.List;

public class BookSortContext {

    private BookSortStrategy strategy;

    public void setStrategy(BookSortStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Book> sortBooks(List<Book> books) {
        if (strategy == null) {
            throw new IllegalStateException("No sorting strategy set");
        }
        return strategy.sort(books);
    }
}
