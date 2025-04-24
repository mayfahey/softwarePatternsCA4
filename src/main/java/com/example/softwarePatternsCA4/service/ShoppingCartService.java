package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.*;
import com.example.softwarePatternsCA4.repository.BookRepository;
import com.example.softwarePatternsCA4.repository.CartItemRepository;
import com.example.softwarePatternsCA4.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository cartRepository,
                               CartItemRepository cartItemRepository,
                               BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookRepository = bookRepository;
    }

    public ShoppingCart getOrCreateCart(CustomerProfile customer) {
        return cartRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart();
                    cart.setCustomer(customer);
                    cart.setTotal(BigDecimal.ZERO);
                    return cartRepository.save(cart);
                });
    }

    public ShoppingCart addItemToCart(CustomerProfile customer, Long bookId, int quantity) {
        ShoppingCart cart = getOrCreateCart(customer);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isEmpty()) throw new RuntimeException("Book not found");

        Book book = bookOpt.get();

        // Check if item already exists
        List<CartItem> items = cartItemRepository.findByCart(cart);
        Optional<CartItem> existingItem = items.stream()
                .filter(i -> i.getBook().getId().equals(bookId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(book, quantity, book.getPrice(), cart);
            cartItemRepository.save(newItem);
        }

        return updateCartTotal(cart);
    }

    public ShoppingCart removeItemFromCart(CustomerProfile customer, Long bookId) {
        ShoppingCart cart = getOrCreateCart(customer);
        List<CartItem> items = cartItemRepository.findByCart(cart);

        items.stream()
             .filter(i -> i.getBook().getId().equals(bookId))
             .findFirst()
             .ifPresent(cartItemRepository::delete);

        return updateCartTotal(cart);
    }

    public ShoppingCart updateCartTotal(ShoppingCart cart) {
        List<CartItem> items = cartItemRepository.findByCart(cart);
        BigDecimal total = items.stream()
                .map(i -> i.getPriceAtTime().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotal(total);
        return cartRepository.save(cart);
    }

    public List<CartItem> getItemsInCart(CustomerProfile customer) {
        ShoppingCart cart = getOrCreateCart(customer);
        return cartItemRepository.findByCart(cart);
    }

    public void clearCart(CustomerProfile customer) {
        ShoppingCart cart = getOrCreateCart(customer);
        cartItemRepository.deleteAll(cartItemRepository.findByCart(cart));
        cart.setTotal(BigDecimal.ZERO);
        cartRepository.save(cart);
    }
    
    public ShoppingCart updateItemQuantity(CustomerProfile customer, Long bookId, int quantity) {
        ShoppingCart cart = getOrCreateCart(customer);
        List<CartItem> items = cartItemRepository.findByCart(cart);

        for (CartItem item : items) {
            if (item.getBook().getId().equals(bookId)) {
                if (quantity <= 0) {
                    cartItemRepository.delete(item); // remove if zero or negative
                } else {
                    item.setQuantity(quantity);
                    cartItemRepository.save(item);
                }
                break;
            }
        }
        return updateCartTotal(cart);
    }

}
