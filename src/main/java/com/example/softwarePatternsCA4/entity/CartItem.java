package com.example.softwarePatternsCA4.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // Getters and setters
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPriceAtTime() {
		return priceAtTime;
	}

	public void setPriceAtTime(BigDecimal priceAtTime) {
		this.priceAtTime = priceAtTime;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	private int quantity;

    private BigDecimal priceAtTime; // Capture the price at the time it was added

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private ShoppingCart cart;

    public CartItem() {}

    public CartItem(Book book, int quantity, BigDecimal priceAtTime, ShoppingCart cart) {
        this.book = book;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
        this.cart = cart;
    }

   
}
