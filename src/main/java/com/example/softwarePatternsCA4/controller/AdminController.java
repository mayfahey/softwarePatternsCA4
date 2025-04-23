package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.Order;
import com.example.softwarePatternsCA4.service.CustomerProfileService;
import com.example.softwarePatternsCA4.service.BookService;
import com.example.softwarePatternsCA4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CustomerProfileService customerProfileService;
    private final OrderService orderService;
    private final BookService bookService;

    @Autowired
    public AdminController(CustomerProfileService customerProfileService, OrderService orderService, BookService bookService) {
        this.customerProfileService = customerProfileService;
        this.orderService = orderService;
        this.bookService = bookService;
    }

    // Get all customers
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerProfile>> getAllCustomers() {
        return ResponseEntity.ok(customerProfileService.getAllProfiles());
    }

    // Get a customer's order history
    @GetMapping("/customers/{id}/orders")
    public ResponseEntity<List<Order>> getCustomerOrders(@PathVariable Long id) {
        return customerProfileService.getProfileById(id)
                .map(customer -> ResponseEntity.ok(orderService.getOrdersByCustomer(customer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/books/{id}/restock")
    public ResponseEntity<?> restockBook(@PathVariable Long id, @RequestParam int quantity) {
        Optional<Book> bookOpt = bookService.getBookById(id);
        if (bookOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book book = bookOpt.get();
        book.setStockQuantity(book.getStockQuantity() + quantity);
        Book updated = bookService.saveBook(book);

        return ResponseEntity.ok(updated);
    }


}
