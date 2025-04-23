package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.decorator.BasePriceCalculator;
import com.example.softwarePatternsCA4.decorator.LoyaltyDiscountDecorator;
import com.example.softwarePatternsCA4.decorator.PercentageDiscountDecorator;
import com.example.softwarePatternsCA4.decorator.PriceCalculator;
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

import java.math.BigDecimal;
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
    private final OrderEventPublisher orderEventPublisher = OrderEventPublisher.getInstance(); //only one global instance
    private final List<OrderObserver> observers;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        ShoppingCartService cartService,
                        BookRepository bookRepository,
                        OrderItemFactory orderItemFactory,
                        @Lazy List<OrderObserver> observers) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
        this.bookRepository = bookRepository;
        this.orderItemFactory = orderItemFactory;
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

        // Decorator Pattern - apply dynamic discounts
        PriceCalculator calculator = new BasePriceCalculator(cart);
        calculator = new PercentageDiscountDecorator(calculator, BigDecimal.valueOf(0.10)); // 10% off for promo
        calculator = new LoyaltyDiscountDecorator(calculator, customer); // loyalty points based discount

        // Create new Order
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(calculator.calculateTotal()); // gets total including discount
        order.setPaymentMethod(paymentMethod);
        order.setShippingAddress(shippingAddress);
        order.setCustomer(customer);
        order = orderRepository.save(order);

        // Convert CartItems to OrderItems using factory pattern
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


