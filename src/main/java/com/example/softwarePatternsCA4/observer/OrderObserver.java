package com.example.softwarePatternsCA4.observer;

import com.example.softwarePatternsCA4.entity.Order;

public interface OrderObserver {
    void onOrderPlaced(Order order);
}
