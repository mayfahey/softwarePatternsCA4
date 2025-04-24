package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.Order;
import com.example.softwarePatternsCA4.service.CustomerProfileService;
import com.example.softwarePatternsCA4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerProfileService customerProfileService;

    @Autowired
    public OrderController(OrderService orderService, CustomerProfileService customerProfileService) {
        this.orderService = orderService;
        this.customerProfileService = customerProfileService;
    }

    // POST /api/orders/checkout?customerId=1
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam Long customerId,
                                      @RequestParam String paymentMethod,
                                      @RequestParam String shippingAddress) {
        Optional<CustomerProfile> customerOpt = customerProfileService.getProfileById(customerId);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Customer not found.");
        }

        try {
            Order order = orderService.checkout(customerOpt.get(), paymentMethod, shippingAddress);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/orders/customer?customerId=1
    @GetMapping("/customer")
    public ResponseEntity<?> getCustomerOrders(@RequestParam Long customerId) {
        Optional<CustomerProfile> customerOpt = customerProfileService.getProfileById(customerId);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Customer not found.");
        }

        List<Order> orders = orderService.getOrdersByCustomer(customerOpt.get());
        return ResponseEntity.ok(orders);
    }

    // GET /api/orders/1
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
