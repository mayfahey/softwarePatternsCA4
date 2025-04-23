package com.example.softwarePatternsCA4.observer;

import com.example.softwarePatternsCA4.entity.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderEventPublisher {

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
