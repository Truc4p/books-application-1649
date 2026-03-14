// FILE: models/Book.java
package BooksApplication.models;

public class Book implements Comparable<Book> {
    private int bookId; // Unique identifier for the book
    private String title; // Title of the book
    private String author; // Author of the book
    private double price; // Price of the book
    private int stockQuantity; // Quantity of the book in stock
    private String category; // Category of the book
    private String cover; // Cover image URL

    // Constructor to initialize the book object
    public Book(int bookId, String title, String author, double price, int stockQuantity, String category, String cover) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.cover = cover;
    }

    // Overloaded Constructor for backward compatibility in your console app
    public Book(int bookId, String title, String author, double price, int stockQuantity) {
        this(bookId, title, author, price, stockQuantity, "Uncategorized", "images/default-cover.png");
    }

    // Getter method for bookId
    public int getBookId() {
        return bookId;
    }

    // Getter method for title
    public String getTitle() {
        return title;
    }

    // Setter method for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter method for author
    public String getAuthor() {
        return author;
    }

    // Setter method for author
    public void setAuthor(String author) {
        this.author = author;
    }

    // Getter method for price
    public double getPrice() {
        return price;
    }

    // Setter method for price
    public void setPrice(double price) {
        this.price = price;
    }

    // Getter method for stockQuantity
    public int getStockQuantity() {
        return stockQuantity;
    }

    // Setter method for stockQuantity
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    // Override compareTo method to compare books by bookId
    @Override
    public int compareTo(Book other) {
        return Integer.compare(this.bookId, other.bookId);
    }

    // Override toString method to return a string representation of the book object
    @Override
    public String toString() {
        return String.format("Book ID: %d\tTitle: %s\tAuthor: %s\tPrice: %.2f\tStock Quantity: %d",
                bookId, title, author, price, stockQuantity);
    }
}