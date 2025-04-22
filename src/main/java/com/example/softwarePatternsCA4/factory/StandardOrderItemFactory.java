package com.example.softwarePatternsCA4.factory;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.Order;
import com.example.softwarePatternsCA4.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StandardOrderItemFactory implements OrderItemFactory {
    @Override
    public OrderItem create(Book book, int quantity, Order order) {
        return new OrderItem(book, quantity, book.getPrice(), order);
    }
}

