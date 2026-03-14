// FILE: customer/CustomerBuddy.java
package BooksApplication.customer;

import BooksApplication.adt.ArrayListADT;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerBuddy {
    // List to store customer objects
    private ArrayListADT<Customer> customers;
    // Static counter to generate unique customer IDs
    private static int customerIdCounter = 1;
    private static final String CSV_FILE = "data/customers.csv";

    // Constructor to initialize the customer list
    public CustomerBuddy() {
        customers = new ArrayListADT<>();
        loadCustomers();
    }

    private void loadCustomers() {
        File file = new File(CSV_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    int id = Integer.parseInt(values[0]);
                    String name = values[1];
                    String email = values[2];
                    String password = values[3];
                    String address = values[4];
                    customers.add(new Customer(id, name, email, password, address));
                    if (id >= customerIdCounter) {
                        customerIdCounter = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
    }

    private void saveCustomers() {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (int i = 0; i < customers.size(); i++) {
                Customer c = customers.get(i);
                pw.println(c.getUserId() + "," + c.getName() + "," + c.getEmail() + "," + c.getPassword() + "," + c.getAddress());
            }
        } catch (IOException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }

    // Method to register a new customer
    public void registerCustomer(Customer customer) {
        customers.add(customer);
        saveCustomers();
    }

    // Method to login a customer by email and password
    public Customer loginCustomer(String email, String password) {
        String hashedPassword = BooksApplication.models.SecurityUtils.hashPassword(password);
        // Iterate through the customer list
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            // Check if the email matches and password matches either hash or plain text (for backward compatibility)
            if (customer.getEmail().equals(email) && 
               (customer.getPassword().equals(hashedPassword) || customer.getPassword().equals(password))) {
                return customer; // Customer found
            }
        }
        return null; // Customer not found
    }

    // Static method to generate a unique customer ID
    private static int generateCustomerId() {
        return customerIdCounter++;
    }

    // Method to create a new customer with the given details
    public Customer createCustomer(String name, String email, String password, String address) {
        int customerId = generateCustomerId();
        String hashedPassword = BooksApplication.models.SecurityUtils.hashPassword(password);
        return new Customer(customerId, name, email, hashedPassword, address);
    }

    public Customer getCustomerByEmail(String email) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getEmail().equals(email)) {
                return customers.get(i);
            }
        }
        return null;
    }
}