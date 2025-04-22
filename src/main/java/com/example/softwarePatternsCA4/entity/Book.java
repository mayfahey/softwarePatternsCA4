package com.example.softwarePatternsCA4.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String publisher;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private String category;
    private String isbn;
    private String imageUrl;

    @Column(length = 1000)
    private String description;

    private int stockQuantity;

    // bi-directional link to reviews
    //@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    //private List<Review> reviews;

    // getters and setters
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	/*
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	} */

	// Constructors
    public Book() {}

    public Book(String title, String author, String publisher, BigDecimal price,
                String category, String isbn, String imageUrl, String description, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.category = category;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

    // to string method
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", publisher=" + publisher + ", price="
				+ price + ", category=" + category + ", isbn=" + isbn + ", imageUrl=" + imageUrl + ", description="
				+ description + ", stockQuantity=" + stockQuantity + "]";
	}

}
