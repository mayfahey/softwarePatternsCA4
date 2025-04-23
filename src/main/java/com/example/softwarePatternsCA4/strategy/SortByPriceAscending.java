package com.example.softwarePatternsCA4.strategy;

import com.example.softwarePatternsCA4.entity.Book;

import java.util.Comparator;
import java.util.List;

public class SortByPriceAscending implements BookSortStrategy {
    @Override
    public List<Book> sort(List<Book> books) {
        books.sort(Comparator.comparing(Book::getPrice));
        return books;
    }
}
