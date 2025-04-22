package com.example.softwarePatternsCA4.strategy;

import com.example.softwarePatternsCA4.entity.Book;

import java.util.Comparator;
import java.util.List;

public class SortByTitleAscending implements BookSortStrategy {

    @Override
    public List<Book> sort(List<Book> books) {
        books.sort(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
        return books;
    }
}
