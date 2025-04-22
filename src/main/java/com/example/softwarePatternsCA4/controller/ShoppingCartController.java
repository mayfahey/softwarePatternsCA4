package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.CartItem;
import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.ShoppingCart;
import com.example.softwarePatternsCA4.service.CustomerProfileService;
import com.example.softwarePatternsCA4.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    private final ShoppingCartService cartService;
    private final CustomerProfileService customerProfileService;

    @Autowired
    public ShoppingCartController(ShoppingCartService cartService, CustomerProfileService customerProfileService) {
        this.cartService = cartService;
        this.customerProfileService = customerProfileService;
    }

    // Helper method to get customer
    private Optional<CustomerProfile> getCustomer(Long customerId) {
        return customerProfileService.getProfileById(customerId);
    }

    // Add item to cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long customerId,
                                       @RequestParam Long bookId,
                                       @RequestParam int quantity) {
        Optional<CustomerProfile> customerOpt = getCustomer(customerId);
        if (customerOpt.isEmpty()) return ResponseEntity.badRequest().body("Customer not found");

        ShoppingCart cart = cartService.addItemToCart(customerOpt.get(), bookId, quantity);
        return ResponseEntity.ok(cart);
    }

    // Remove item from cart
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestParam Long customerId,
                                            @RequestParam Long bookId) {
        Optional<CustomerProfile> customerOpt = getCustomer(customerId);
        if (customerOpt.isEmpty()) return ResponseEntity.badRequest().body("Customer not found");

        ShoppingCart cart = cartService.removeItemFromCart(customerOpt.get(), bookId);
        return ResponseEntity.ok(cart);
    }

    // View all items in cart
    @GetMapping("/items")
    public ResponseEntity<?> viewCartItems(@RequestParam Long customerId) {
        Optional<CustomerProfile> customerOpt = getCustomer(customerId);
        if (customerOpt.isEmpty()) return ResponseEntity.badRequest().body("Customer not found");

        List<CartItem> items = cartService.getItemsInCart(customerOpt.get());
        return ResponseEntity.ok(items);
    }

    // Clear cart
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestParam Long customerId) {
        Optional<CustomerProfile> customerOpt = getCustomer(customerId);
        if (customerOpt.isEmpty()) return ResponseEntity.badRequest().body("Customer not found");

        cartService.clearCart(customerOpt.get());
        return ResponseEntity.ok("Cart cleared.");
    }
}
