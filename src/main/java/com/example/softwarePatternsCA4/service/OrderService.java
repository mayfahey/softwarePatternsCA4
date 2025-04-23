package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.*;
import com.example.softwarePatternsCA4.factory.OrderItemFactory;
import com.example.softwarePatternsCA4.observer.OrderEventPublisher;
import com.example.softwarePatternsCA4.observer.OrderObserver;
import com.example.softwarePatternsCA4.repository.BookRepository;
import com.example.softwarePatternsCA4.repository.OrderItemRepository;
import com.example.softwarePatternsCA4.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartService cartService;
    private final BookRepository bookRepository;
    private final OrderItemFactory orderItemFactory;
    private final OrderEventPublisher orderEventPublisher;
    private final List<OrderObserver> observers;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        ShoppingCartService cartService,
                        BookRepository bookRepository,
                        OrderItemFactory orderItemFactory,
                        OrderEventPublisher orderEventPublisher,
                        @Lazy List<OrderObserver> observers) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
        this.bookRepository = bookRepository;
        this.orderItemFactory = orderItemFactory;
        this.orderEventPublisher = orderEventPublisher;
        this.observers = observers;

        // Register observers on startup
        observers.forEach(orderEventPublisher::registerObserver);
    }

    public Order checkout(CustomerProfile customer, String paymentMethod, String shippingAddress) {
        ShoppingCart cart = cartService.getOrCreateCart(customer);
        List<CartItem> cartItems = cartService.getItemsInCart(customer);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty.");
        }

        // Create new Order
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotal());
        order.setPaymentMethod(paymentMethod);
        order.setShippingAddress(shippingAddress);
        order.setCustomer(customer);
        order = orderRepository.save(order);

        // Convert CartItems to OrderItems using Factory Pattern
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cartItems) {
            OrderItem orderItem = orderItemFactory.create(item.getBook(), item.getQuantity(), order);
            orderItems.add(orderItem);

            // Update stock
            Book book = item.getBook();
            int newStock = book.getStockQuantity() - item.getQuantity();
            if (newStock < 0) {
                throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
            }
            book.setStockQuantity(newStock);
            bookRepository.save(book);
        }

        orderItemRepository.saveAll(orderItems);
        order.setItems(orderItems);

        // Clear cart
        cartService.clearCart(customer);

        // Notify observers (add loyalty points)
        orderEventPublisher.notifyObservers(order);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomer(CustomerProfile customer) {
        return orderRepository.findByCustomer(customer);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found."));
    }
}
