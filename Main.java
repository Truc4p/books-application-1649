// FILE: Main.java
package BooksApplication;

import java.util.Scanner;
import java.util.Date;
import java.io.Console;

import BooksApplication.models.BookBuddy;
import BooksApplication.models.CartItemBuddy;
import BooksApplication.models.OrderBuddy;
import BooksApplication.models.User;
import BooksApplication.admin.Admin;
import BooksApplication.admin.AdminBuddy;
import BooksApplication.customer.Customer;
import BooksApplication.customer.CustomerBuddy;
import BooksApplication.adt.StackADT;
import BooksApplication.algo.BinarySearch;
import BooksApplication.algo.LinearSearch;
import BooksApplication.algo.QuickSort;
import BooksApplication.models.Book;
import BooksApplication.adt.ArrayListADT;
import BooksApplication.models.CartItem;
import BooksApplication.models.Order;
import BooksApplication.adt.QueueADT;

public class Main {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();

        BookBuddy bookBuddy = new BookBuddy();
        CartItemBuddy cartItemBuddy = new CartItemBuddy();
        AdminBuddy adminBuddy = new AdminBuddy();
        CustomerBuddy customerBuddy = new CustomerBuddy();
        OrderBuddy orderBuddy = new OrderBuddy(bookBuddy, customerBuddy);
        StackADT<String> searchHistory = new StackADT<>(); // Stack to store search queries
        boolean running = true;
        User loggedInUser = null;

        // ArrayListADT to simulate database
        ArrayListADT<User> usersDatabase = new ArrayListADT<>();

        while (running) {
            ui.printHeader("Book Store Application");

            if (loggedInUser == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("----------------------");
                
                int choice = ui.promptInt("Enter your choice:");

                switch (choice) {
                    case 1:
                        // Register
                        System.out.println("\n1. Register as Customer");
                        System.out.println("2. Register as Admin");
                        int registerChoice = ui.promptInt("Enter your choice:");

                        String name = ui.promptString("Enter your name:");
                        String email = ui.promptString("Enter your email:");
                        String password = ui.promptPassword("Enter your password:");

                        if (registerChoice == 1) {
                            String address = ui.promptString("Enter your address:");
                            Customer customer = customerBuddy.createCustomer(name, email, password, address);
                            customerBuddy.registerCustomer(customer);
                            usersDatabase.add(customer); // Save to database
                            ui.printSuccess("Customer registered successfully!");
                        } else if (registerChoice == 2) {
                            String adminKey = ui.promptPassword("Enter the admin key:");
                            if (adminKey.equals("abc")) {
                                Admin admin = adminBuddy.createAdmin(name, email, password);
                                adminBuddy.registerAdmin(admin);
                                usersDatabase.add(admin); // Save to database
                                ui.printSuccess("Admin registered successfully!");
                            } else {
                                ui.printError("Invalid admin key. Registration as Admin failed.");
                            }
                        } else {
                            ui.printError("Invalid choice.");
                        }
                        ui.waitForEnter();
                        break;

                    case 2:
                        // Login
                        System.out.println("1. Login as Customer");
                        System.out.println("2. Login as Admin");
                        int loginChoice = ui.promptInt("Enter your choice:");

                        String loginEmail = ui.promptString("Enter your email:");
                        String loginPassword = ui.promptPassword("Enter your password:");

                        if (loginChoice == 1) {
                            loggedInUser = customerBuddy.loginCustomer(loginEmail, loginPassword);
                        } else if (loginChoice == 2) {
                            loggedInUser = adminBuddy.loginAdmin(loginEmail, loginPassword);
                        } else {
                            ui.printError("Invalid choice.");
                        }

                        if (loggedInUser != null) {
                            ui.printSuccess("Login successful! Welcome, " + loggedInUser.getName());
                        } else {
                            ui.printError("Invalid email or password.");
                        }
                        ui.waitForEnter();
                        break;

                    case 3:
                        // Exit
                        running = false;
                        break;

                    default:
                        ui.printError("Invalid choice. Please enter your choice again!");
                        ui.waitForEnter();
                        break;
                }
            } else {
                if (loggedInUser instanceof Customer) {
                    // Customer menu
                    int choice = 0;
                    while (true) {
                        System.out.println("1. Display all books");
                        System.out.println("2. Add book to cart");
                        System.out.println("3. Search book");
                        System.out.println("4. Go to cart");
                        System.out.println("5. Orders History");
                        System.out.println("6. Search book history");
                        System.out.println("7. Logout");
                        System.out.println("----------------------");
                        
                        choice = ui.promptInt("Enter your choice:");
                        if (choice >= 1 && choice <= 7) {
                            break;
                        } else {
                            ui.printError("Invalid choice. Please enter a number between 1 and 7.");
                        }
                    }

                    switch (choice) {
                        case 1:
                            // Display all books
                            bookBuddy.displayBooks();
                            ui.waitForEnter();
                            break;

                        case 2:
                            // Add book to cart
                            System.out.println("----------------------");
                            int bookIdToAdd = ui.promptInt("Enter the book ID to add to cart:");
                            
                            Book book = bookBuddy.getBookById(bookIdToAdd); // Assume this method retrieves the book by
                                                                            // ID
                            if (book != null) {
                                int currentQuantityInCart = cartItemBuddy.getQuantityInCart(bookIdToAdd);
                                int availableQuantity = book.getStockQuantity() - currentQuantityInCart;

                                if (availableQuantity <= 0) {
                                    ui.printError("This book is out of stock.");
                                } else {
                                    int quantity = 0;
                                    boolean validQuantity = false;

                                    while (!validQuantity) {
                                        quantity = ui.promptInt("Enter the quantity to add to cart (available: "
                                                + availableQuantity + "):");
                                        if (quantity > 0 && quantity <= availableQuantity) {
                                            validQuantity = true;
                                        } else {
                                            ui.printError(
                                                    "Invalid quantity. Please enter a quantity between 1 and "
                                                            + availableQuantity + ".");
                                        }
                                    }
                                    cartItemBuddy.addToCart(book, quantity); // Add to cart with specified quantity
                                    ui.printSuccess("Book(s) added to cart!");
                                }
                            } else {
                                ui.printError("No book found with ID: " + bookIdToAdd);
                            }
                            ui.waitForEnter();
                            break;

                        case 3:
                            // Search book
                            System.out.println("----------------------");
                            String searchQuery = ui.promptString("Enter the ID, title or author of the book:");
                            searchHistory.push(searchQuery); // Save search query to history
                            bookBuddy.searchBook(searchQuery); // Assume searchBook handles searching by ID, title, or
                                                               // author
                            ui.waitForEnter();
                            break;

                        case 4:
                            // View cart
                            System.out.println("----------------------");
                            cartItemBuddy.viewCart();

                            System.out.println("1. Place Order");
                            System.out.println("2. Go back");
                            System.out.println("3. Remove book from cart");
                            System.out.println("4. Change quantity");

                            System.out.println("----------------------");
                            int choiceCart = ui.promptInt("Enter your choice:");

                            switch (choiceCart) {
                                case 1:
                                    // Place Order
                                    if (cartItemBuddy.isEmpty()) {
                                        ui.printWarning("Your cart is empty. Add some books to the cart before checking out.");
                                    } else {
                                        Customer customer = (Customer) loggedInUser;
                                        ArrayListADT<CartItem> booksInCart = new ArrayListADT<>();
                                        for (int i = 0; i < cartItemBuddy.getBooksInCart().size(); i++) {
                                            booksInCart.add(cartItemBuddy.getBooksInCart().get(i));
                                        }
                                        Order order = orderBuddy.createOrder(new Date(), customer, "Pending",
                                                booksInCart);
                                        orderBuddy.addOrder(order);

                                        // Subtract stock quantity of the books
                                        for (int i = 0; i < booksInCart.size(); i++) {
                                            CartItem cartItem = booksInCart.get(i);
                                            Book bookToBuy = bookBuddy.getBookById(cartItem.getBookId());
                                            bookToBuy.setStockQuantity(
                                                    bookToBuy.getStockQuantity() - cartItem.getQuantity());
                                        }

                                        ui.printSuccess("Order placed successfully!");
                                        cartItemBuddy.clearCart();
                                        System.out.println(order);
                                        cartItemBuddy.clearCart();
                                        ui.printSuccess("Order placed successfully!");
                                        ui.waitForEnter();
                                    }
                                    break;

                                case 2:
                                    // Go back
                                    break;

                                case 3:
                                    // Remove book from cart
                                    cartItemBuddy.removeBookFromCart(ui.getScanner());
                                    ui.waitForEnter();
                                    break;

                                case 4:
                                    // Change quantity
                                    System.out.println("----------------------");
                                    String inputCart2 = ui.promptString("Enter the book ID to change quantity or 'n' to cancel:");
                                    if (!inputCart2.equalsIgnoreCase("n")) {
                                        try {
                                            int bookId = Integer.parseInt(inputCart2);
                                            CartItem cartItem = cartItemBuddy.getCartItemById(bookId);
                                            if (cartItem != null) {
                                                Book bookQuantityChange = bookBuddy.getBookById(bookId); 
                                                if (bookQuantityChange != null) {
                                                    int newQuantity = 0;
                                                    boolean validQuantity = false;

                                                    while (!validQuantity) {
                                                        newQuantity = ui.promptInt("Enter the new quantity (available: "
                                                                + bookQuantityChange.getStockQuantity() + "):");
                                                        if (newQuantity > 0 && newQuantity <= bookQuantityChange
                                                                .getStockQuantity()) {
                                                            validQuantity = true;
                                                        } else {
                                                            ui.printError(
                                                                    "Invalid quantity. Please enter a quantity between 1 and "
                                                                            + bookQuantityChange.getStockQuantity()
                                                                            + ".");
                                                        }
                                                    }
                                                    cartItem.setQuantity(newQuantity);
                                                    ui.printSuccess("Quantity updated successfully!");
                                                } else {
                                                    ui.printError("No book found with ID: " + bookId);
                                                }
                                            } else {
                                                ui.printError("No book found with ID: " + bookId);
                                            }
                                        } catch (NumberFormatException e) {
                                            ui.printError("Invalid input. Please enter a valid book ID.");
                                        }
                                    }
                                    ui.waitForEnter();
                                    break;
                            }
                            break;

                        case 5:
                            // View Orders History as customer
                            orderBuddy.viewOrdersHistory(loggedInUser);

                            // Search Orders as customer
                            orderBuddy.searchOrdersAsCustomer(loggedInUser, ui.getScanner());
                            ui.waitForEnter();
                            break;

                        case 6:
                            // Search book history
                            System.out.println("----------------------");
                            System.out.println(ui.CYAN + ui.BOLD + "Search History:" + ui.RESET);
                            if (searchHistory.isEmpty()) {
                                ui.printWarning("No search history found.");
                            } else {
                                System.out.println(searchHistory.toString());
                            }
                            ui.waitForEnter();
                            break;

                        case 7:
                            // Logout
                            loggedInUser = null;
                            ui.printSuccess("Logged out successfully.");
                            ui.waitForEnter();
                            break;

                        default:
                            ui.printError("Invalid choice. Please enter your choice again!");
                            ui.waitForEnter();
                            break;
                    }
                } else if (loggedInUser instanceof Admin) {
                    // Admin menu
                    System.out.println("1. Display all books");
                    System.out.println("2. Add new book");
                    System.out.println("3. Update book details");
                    System.out.println("4. Remove book");
                    System.out.println("5. View all orders");
                    System.out.println("6. Logout");
                    System.out.println("----------------------");

                    int choice = 0;
                    while (true) {
                        choice = ui.promptInt("Enter your choice:");
                        if (choice >= 1 && choice <= 6) {
                            break;
                        } else {
                            ui.printError("Invalid choice. Please enter a number between 1 and 6.");
                        }
                    }

                    switch (choice) {
                        case 1:
                            // Display all books
                            bookBuddy.displayBooks();
                            ui.waitForEnter();
                            break;

                        case 2:
                            // Add new book
                            bookBuddy.addNewBook(ui.getScanner());
                            ui.waitForEnter();
                            break;

                        case 3:
                            // Update book details
                            bookBuddy.updateBookDetails(ui.getScanner());
                            ui.waitForEnter();
                            break;

                        case 4:
                            // Remove book
                            bookBuddy.removeBook(ui.getScanner());
                            ui.waitForEnter();
                            break;

                        case 5:
                            // View all orders as admin
                            orderBuddy.viewAllOrdersAsAdmin(ui.getScanner());
                            ui.waitForEnter();
                            break;

                        case 6:
                            // Logout
                            loggedInUser = null;
                            ui.printSuccess("Logged out successfully.");
                            ui.waitForEnter();
                            break;

                        default:
                            ui.printError("Invalid choice. Please enter your choice again!");
                            ui.waitForEnter();
                            break;
                    }
                }
            }
        }

        ui.printSuccess("Exiting ...");
    }
}