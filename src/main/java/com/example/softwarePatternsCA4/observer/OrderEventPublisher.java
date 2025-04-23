package com.example.softwarePatternsCA4.observer;

import com.example.softwarePatternsCA4.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderEventPublisher {

	// static instance for singleton
    private static OrderEventPublisher instance;

    private OrderEventPublisher() {}

    // Global access point
    public static synchronized OrderEventPublisher getInstance() {
        if (instance == null) {
            instance = new OrderEventPublisher();
        }
        return instance;
    }
    
    private final List<OrderObserver> observers = new ArrayList<>();

    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(Order order) {
        for (OrderObserver observer : observers) {
            observer.onOrderPlaced(order);
        }
    }
}
