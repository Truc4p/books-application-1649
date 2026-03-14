// FILE: admin/AdminBuddy.java
package BooksApplication.admin;

import BooksApplication.adt.ArrayListADT;
import BooksApplication.customer.Customer;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AdminBuddy {
    // List to store admin objects
    private ArrayListADT<Admin> admins;
    // Static counter to generate unique admin IDs
    private static int adminIdCounter = 1;
    private static final String CSV_FILE = "data/admins.csv";

    // Constructor to initialize the admin list
    public AdminBuddy() {
        admins = new ArrayListADT<>();
        loadAdmins();
    }

    private void loadAdmins() {
        File file = new File(CSV_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int id = Integer.parseInt(values[0]);
                    String name = values[1];
                    String email = values[2];
                    String password = values[3];
                    admins.add(new Admin(id, name, email, password));
                    if (id >= adminIdCounter) {
                        adminIdCounter = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading admins: " + e.getMessage());
        }
    }

    private void saveAdmins() {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (int i = 0; i < admins.size(); i++) {
                Admin a = admins.get(i);
                pw.println(a.getUserId() + "," + a.getName() + "," + a.getEmail() + "," + a.getPassword());
            }
        } catch (IOException e) {
            System.err.println("Error saving admins: " + e.getMessage());
        }
    }

    // Method to register a new admin
    public void registerAdmin(Admin admin) {
        admins.add(admin);
        saveAdmins();
    }

    // Method to login an admin by email and password
    public Admin loginAdmin(String email, String password) {
        String hashedPassword = BooksApplication.models.SecurityUtils.hashPassword(password);
        for (int i = 0; i < admins.size(); i++) {
            Admin admin = admins.get(i);
            // Check if email matches and password matches either the hash or the plain text (for backward compatibility)
            if (admin.getEmail().equals(email) && 
               (admin.getPassword().equals(hashedPassword) || admin.getPassword().equals(password))) {
                return admin;
            }
        }
        return null;
    }

    // Static method to generate a unique admin ID
    private static int generateAdminId() {
        return adminIdCounter++;
    }

    // Method to create a new admin with the given details
    public Admin createAdmin(String name, String email, String password) {
        int adminId = generateAdminId();
        String hashedPassword = BooksApplication.models.SecurityUtils.hashPassword(password);
        return new Admin(adminId, name, email, hashedPassword);
    }
}