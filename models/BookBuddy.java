// FILE: models/BookBuddy.java
package BooksApplication.models;

import BooksApplication.adt.ArrayListADT;
import BooksApplication.algo.QuickSort;
import BooksApplication.algo.BinarySearch;
import BooksApplication.algo.LinearSearch;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BookBuddy {
    private ArrayListADT<Book> books; // List to store book objects
    private int maxBookId; // Variable to keep track of the maximum book ID
    private static final String CSV_FILE = "data/books.csv";

    public BookBuddy() {
        this.books = new ArrayListADT<>();
        loadBooks();

        // If file doesn't exist or is empty, initialize with mock data
        if (books.size() == 0) {
            initializeMockData();
            saveBooks();
        }

        // Initialize maxBookId to the highest ID
        maxBookId = 0;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId() > maxBookId) {
                maxBookId = books.get(i).getBookId();
            }
        }
    }

    private void loadBooks() {
        File file = new File(CSV_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 7) {
                    try {
                        int id = Integer.parseInt(values[0]);
                        String title = values[1];
                        String author = values[2];
                        double price = Double.parseDouble(values[3]);
                        int stock = Integer.parseInt(values[4]);
                        String category = values[5];
                        String image = values[6];
                        books.add(new Book(id, title, author, price, stock, category, image));
                    } catch (NumberFormatException e) {
                        // ignore malformed lines
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
    }

    public void saveBooks() {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (int i = 0; i < books.size(); i++) {
                Book b = books.get(i);
                pw.println(b.getBookId() + "," + b.getTitle() + "," + b.getAuthor() + "," + 
                           b.getPrice() + "," + b.getStockQuantity() + "," + b.getCategory() + "," + b.getCover());
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    private void initializeMockData() {
        books.add(new Book(101, "Velvet Autumn", "Mira Solis", 18.50, 10, "Fiction", "images/velvet-autumn.svg"));
        books.add(new Book(102, "Quiet Geometry", "R. Han", 24.00, 6, "Non-fiction", "images/quiet-geometry.svg"));
        books.add(new Book(103, "Paper Lullaby", "Neve Holt", 16.75, 12, "Children", "images/paper-lullaby.svg"));
        books.add(new Book(104, "Neon Circuit", "Kai Chen", 21.00, 8, "Sci-Fi", "images/neon-circuit.svg"));
        
        // 16 New mock books:
        books.add(new Book(105, "The Alchemist's Daughter", "Elara Vance", 19.99, 15, "Fantasy", "images/default-cover.png"));
        books.add(new Book(106, "Echoes of the Void", "Marcus Chen", 22.50, 5, "Sci-Fi", "images/default-cover.png"));
        books.add(new Book(107, "Code Complete", "Steve McConnell", 45.00, 2, "Education", "images/default-cover.png"));
        books.add(new Book(108, "The Silent Patient", "Alex Michaelides", 14.50, 20, "Thriller", "images/default-cover.png"));
        books.add(new Book(109, "Design Patterns", "Gang of Four", 55.00, 4, "Education", "images/default-cover.png"));
        books.add(new Book(110, "Whispers in the Wind", "Sarah Jenkins", 12.99, 8, "Romance", "images/default-cover.png"));
        books.add(new Book(111, "Beyond the Stars", "James Holden", 16.00, 11, "Sci-Fi", "images/default-cover.png"));
        books.add(new Book(112, "Culinary Magic", "Chef Gordon", 29.99, 3, "Non-fiction", "images/default-cover.png"));
        books.add(new Book(113, "The Iron Citadel", "Brandon Sanderson", 25.00, 7, "Fantasy", "images/default-cover.png"));
        books.add(new Book(114, "Modern Art Explained", "Lisa Zhou", 34.50, 6, "Non-fiction", "images/default-cover.png"));
        books.add(new Book(115, "Data Structures Java", "Mark Allen Weiss", 65.00, 10, "Education", "images/default-cover.png"));
        books.add(new Book(116, "Crimson Sky", "A.G. Riddle", 18.00, 14, "Historical", "images/default-cover.png"));
        books.add(new Book(117, "The Midnight Library", "Alex Michaelides", 15.50, 9, "Thriller", "images/default-cover.png"));
        books.add(new Book(118, "Clean Architecture", "Robert C. Martin", 42.00, 5, "Education", "images/default-cover.png"));
        books.add(new Book(119, "Ocean's Song", "David Mitchell", 11.20, 18, "Fiction", "images/default-cover.png"));
        books.add(new Book(120, "Atomic Habits", "Robert C. Martin", 38.00, 10, "Education", "images/default-cover.png"));
    }

    // Method to get all books for the web server
    public ArrayListADT<Book> getAllBooks() {
        return books;
    }

    // Method to display books
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books to display.");
        } else {
            // Sort books using QuickSort
            QuickSort<Book> sorter = new QuickSort<>();
            sorter.sort(books);

            // Display sorted books
            System.out.println("Book List:");
            for (int i = 0; i < books.size(); i++) {
                Book currentBook = books.get(i);
                System.out.println(currentBook);
            }
        }
    }

    // Method to search for books by ID, title, or author
    public void searchBook(String query) {
        System.out.println("Searching for books matching: " + query);
        boolean found = false;

        // Sort books using QuickSort
        QuickSort<Book> sorter = new QuickSort<>();
        sorter.sort(books);

        // Search by ID using BinarySearch
        try {
            int bookId = Integer.parseInt(query);
            BinarySearch<Book> binarySearch = new BinarySearch<>();
            int index = binarySearch.search(books, new Book(bookId, "", "", 0, 0));
            if (index != -1) {
                System.out.println(books.get(index));
                found = true;
            }
        } catch (NumberFormatException e) {
            // If query is not an integer, continue to search by title or author
        }

        // Search by title or author using LinearSearch
        if (!found) {
            LinearSearch<Book> linearSearch = new LinearSearch<>();
            int index = linearSearch.search(books, null,
                    book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(query.toLowerCase()));
            if (index != -1) {
                System.out.println(books.get(index));
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found matching: " + query);
        }
    }

    // Method to display a book's details by its ID
    public void displayBook(int bookId) {
        Book book = getBookById(bookId);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("No book found with ID: " + bookId);
        }
    }

    // Method to get a book by its ID
    public Book getBookById(int bookId) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null; // If no book found, return null
    }

    // Method to update book details
    public void updateBookDetails(Scanner scanner) {
        System.out.println("----------------------");
        System.out.print("Enter the book ID to update: ");
        int bookIdToUpdate = Integer.parseInt(scanner.nextLine());
        Book bookToUpdate = getBookById(bookIdToUpdate);
        if (bookToUpdate != null) {
            System.out.print("Enter the new title (leave blank to keep current): ");
            String newTitle = scanner.nextLine();
            System.out.print("Enter the new author (leave blank to keep current): ");
            String newAuthor = scanner.nextLine();
            System.out.print("Enter the new price (leave blank to keep current): ");
            String newPriceStr = scanner.nextLine();
            System.out.print("Enter the new stock quantity (leave blank to keep current): ");
            String newStockQuantityStr = scanner.nextLine();

            if (!newTitle.isEmpty()) {
                bookToUpdate.setTitle(newTitle);
            }
            if (!newAuthor.isEmpty()) {
                bookToUpdate.setAuthor(newAuthor);
            }
            if (!newPriceStr.isEmpty()) {
                double newPrice = Double.parseDouble(newPriceStr);
                bookToUpdate.setPrice(newPrice);
            }
            if (!newStockQuantityStr.isEmpty()) {
                int newStockQuantity = Integer.parseInt(newStockQuantityStr);
                bookToUpdate.setStockQuantity(newStockQuantity);
            }
            saveBooks();

            System.out.println("Book details updated successfully!");
        } else {
            System.out.println("No book found with ID: " + bookIdToUpdate);
        }
    }

    // Method to add a new book
    public void addNewBook(Scanner scanner) {
        System.out.println("----------------------");
        System.out.print("Enter the book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the book author: ");
        String author = scanner.nextLine();
        double price = 0;
        while (true) {
            try {
                System.out.print("Enter the book price: ");
                price = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price.");
            }
        }
        int stockQuantity = 0;
        while (true) {
            try {
                System.out.print("Enter the stock quantity: ");
                stockQuantity = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid stock quantity.");
            }
        }
        // Assign a new unique ID
        int newBookId = ++maxBookId;
        Book newBook = new Book(newBookId, title, author, price, stockQuantity);
        addBook(newBook);
        System.out.println("Book added successfully with ID: " + newBookId);
    }

    // Method to remove a book
    public void removeBook(Scanner scanner) {
        System.out.println("----------------------");
        System.out.print("Enter the book ID to remove: ");
        int bookIdToRemove = Integer.parseInt(scanner.nextLine());
        if (removeBook(bookIdToRemove)) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("No book found with ID: " + bookIdToRemove);
        }
    }

    // Method to add a book to the list
    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    // Method to remove a book from the list by its ID
    public boolean removeBook(int bookId) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId() == bookId) {
                books.remove(i);
                saveBooks();
                return true;
            }
        }
        return false;
    }
}