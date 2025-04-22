package com.example.softwarePatternsCA4.strategy;

import com.example.softwarePatternsCA4.entity.Book;

import java.util.List;

public interface BookSortStrategy {
    List<Book> sort(List<Book> books);
}
