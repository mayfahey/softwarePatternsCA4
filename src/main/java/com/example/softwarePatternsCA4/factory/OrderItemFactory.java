package com.example.softwarePatternsCA4.factory;

import com.example.softwarePatternsCA4.entity.OrderItem;
import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.Order;

public interface OrderItemFactory {
	OrderItem create(Book book, int quantity, Order order);
}
