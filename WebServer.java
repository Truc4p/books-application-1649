package BooksApplication;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import BooksApplication.models.BookBuddy;
import BooksApplication.models.Book;
import BooksApplication.models.OrderBuddy;
import BooksApplication.models.Order;
import BooksApplication.customer.Customer;
import BooksApplication.models.CartItem;
import BooksApplication.adt.ArrayListADT;
import BooksApplication.adt.QueueADT;
import java.util.Date;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import BooksApplication.customer.CustomerBuddy;
import BooksApplication.admin.Admin;
import BooksApplication.admin.AdminBuddy;

public class WebServer {
    public static BookBuddy bookBuddy = new BookBuddy();
    public static CustomerBuddy customerBuddy = new CustomerBuddy();
    public static AdminBuddy adminBuddy = new AdminBuddy();
    public static OrderBuddy orderBuddy = new OrderBuddy(bookBuddy, customerBuddy);

    public static void main(String[] args) throws IOException {
        // Create an HTTP server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Define the /api/books endpoint
        server.createContext("/api/books", new BooksHandler());
        server.createContext("/api/orders", new OrdersHandler());
        server.createContext("/api/login", new LoginHandler());
        server.createContext("/api/register", new RegisterHandler());
        
        // Serve the frontend static files
        server.createContext("/", new StaticFileHandler());

        server.setExecutor(null); // Default executor
        System.out.println("Backend Server is running on http://localhost:8080");
        server.start();
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }
            
            Path filePath = Paths.get("frontend", path);
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                String contentType = "text/plain";
                if (path.endsWith(".html")) contentType = "text/html";
                else if (path.endsWith(".css")) contentType = "text/css";
                else if (path.endsWith(".js")) contentType = "application/javascript";
                else if (path.endsWith(".svg")) contentType = "image/svg+xml";

                exchange.getResponseHeaders().add("Content-Type", contentType);
                byte[] bytes = Files.readAllBytes(filePath);
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } else {
                String response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    static class BooksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Setup CORS headers so frontend can access it
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("GET".equals(exchange.getRequestMethod())) {
                // Send list of books
                String jsonResponse = booksToJson();
                byte[] bytes = jsonResponse.getBytes("UTF-8");
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } else if ("POST".equals(exchange.getRequestMethod()) || "PUT".equals(exchange.getRequestMethod())) {
                // Read incoming JSON
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), "UTF-8");
                
                // Very basic custom parsing (since we don't use external libraries)
                try {
                    int id = extractInt(body, "\"id\"");
                    String title = extractString(body, "\"title\"");
                    String author = extractString(body, "\"author\"");
                    double price = extractDouble(body, "\"price\"");
                    int stock = extractInt(body, "\"stock\"");
                    
                    Book b = bookBuddy.getBookById(id);
                    if (b != null) {
                        b.setTitle(title);
                        b.setAuthor(author);
                        b.setPrice(price);
                        b.setStockQuantity(stock);
                        bookBuddy.saveBooks();
                        System.out.println("Updated Book ID " + id + " via API");
                    } else {
                        String category = extractString(body, "\"category\"");
                        String cover = extractString(body, "\"cover\"");
                        if (category == null || category.isEmpty()) category = "Fiction";
                        if (cover == null || cover.isEmpty()) cover = "images/default-cover.png";
                        
                        Book newBook = new Book(id, title, author, price, stock, category, cover);
                        bookBuddy.addBook(newBook);
                        System.out.println("Added Book ID " + id + " via API");
                    }
                    
                    String resp = "{\"status\": \"success\"}";
                    exchange.sendResponseHeaders(200, resp.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(resp.getBytes());
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    String resp = "{\"status\": \"error\"}";
                    exchange.sendResponseHeaders(400, resp.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(resp.getBytes());
                    os.close();
                }
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                try {
                    String query = exchange.getRequestURI().getQuery();
                    if (query != null && query.contains("id=")) {
                        int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3).split("&")[0]);
                        if (bookBuddy.removeBook(id)) {
                            System.out.println("Deleted Book ID " + id + " via API");
                        }
                    }
                    String resp = "{\"status\": \"success\"}";
                    exchange.sendResponseHeaders(200, resp.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(resp.getBytes());
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    String resp = "{\"status\": \"error\"}";
                    exchange.sendResponseHeaders(400, resp.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(resp.getBytes());
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
            }
        }
        
        // Simple manual JSON stringifier
        private String booksToJson() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            BooksApplication.adt.ArrayListADT<Book> allBooks = bookBuddy.getAllBooks();
            for (int i = 0; i < allBooks.size(); i++) {
                Book b = allBooks.get(i);
                sb.append("{");
                sb.append("\"id\":").append(b.getBookId()).append(",");
                sb.append("\"title\":\"").append(escapeJson(b.getTitle())).append("\",");
                sb.append("\"author\":\"").append(escapeJson(b.getAuthor())).append("\",");
                sb.append("\"price\":").append(b.getPrice()).append(",");
                sb.append("\"stock\":").append(b.getStockQuantity()).append(",");
                sb.append("\"category\":\"").append(escapeJson(b.getCategory())).append("\",");
                sb.append("\"cover\":\"").append(escapeJson(b.getCover())).append("\"");
                sb.append("}");
                if (i < allBooks.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        private String escapeJson(String str) {
            if (str == null) return "";
            return str.replace("\\", "\\\\").replace("\"", "\\\"");
        }

        // Basic helper methods to parse simple JSON without external libraries
        private String extractString(String json, String key) {
            String search = key + ":";
            int idx = json.indexOf(search);
            if (idx == -1) return "";
            int start = json.indexOf("\"", idx + search.length()) + 1;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }

        private int extractInt(String json, String key) {
            String search = key + ":";
            int idx = json.indexOf(search);
            if (idx == -1) return 0;
            int start = idx + search.length();
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return Integer.parseInt(json.substring(start, end).trim());
        }

        private double extractDouble(String json, String key) {
            String search = key + ":";
            int idx = json.indexOf(search);
            if (idx == -1) return 0.0;
            int start = idx + search.length();
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return Double.parseDouble(json.substring(start, end).trim());
        }
    }

    static class OrdersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("GET".equals(exchange.getRequestMethod())) {
                String jsonResponse = ordersToJson();
                byte[] bytes = jsonResponse.getBytes("UTF-8");
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } else if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    InputStream is = exchange.getRequestBody();
                    String body = new String(is.readAllBytes(), "UTF-8");
                    
                    String email = extractString(body, "\"email\"");
                    String itemsJson = extractArray(body, "\"items\"");

                    Customer cust = new Customer(1, "Customer", email, "pass", "none");
                    ArrayListADT<CartItem> items = parseItems(itemsJson);

                    Order newOrder = orderBuddy.createOrder(new Date(), cust, "Paid", items);
                    orderBuddy.addOrder(newOrder);
                    

                    String resp = "{\"status\": \"success\"}";
                    exchange.getResponseHeaders().add("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, resp.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(resp.getBytes());
                    os.close();
                } catch(Exception e) {
                    e.printStackTrace();
                    String resp = "{\"status\": \"error\"}";
                    exchange.getResponseHeaders().add("Content-Type", "application/json");
                    exchange.sendResponseHeaders(500, resp.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(resp.getBytes());
                    os.close();
                }
            }
        }
        
        private ArrayListADT<CartItem> parseItems(String itemsJson) {
            ArrayListADT<CartItem> list = new ArrayListADT<>();
            String[] objects = itemsJson.split("\\}\\s*,\\s*\\{");
            for(String obj : objects) {
                if(obj.trim().isEmpty() || obj.equals("[]")) continue;
                int bookId = extractInt(obj, "\"bookId\"");
                int qty = extractInt(obj, "\"quantity\"");
                Book b = bookBuddy.getBookById(bookId);
                if (b != null) {
                   list.add(new CartItem(b, qty));
                }
            }
            return list;
        }

        private String ordersToJson() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            QueueADT<Order> q = orderBuddy.getOrders();
            ArrayListADT<Order> tmp = new ArrayListADT<>();
            while(!q.isEmpty()) { tmp.add(q.poll()); }
            // Do not re-add orders to orderBuddy, or they will duplicate every time!
            // for(int i=0; i<tmp.size(); i++) { orderBuddy.addOrder(tmp.get(i)); }

            for(int i=0; i<tmp.size(); i++) {
                Order o = tmp.get(i);
                sb.append("{");
                sb.append("\"id\":").append(o.getOrderId()).append(",");
                sb.append("\"date\":\"").append(o.getDate().toString()).append("\",");
                sb.append("\"email\":\"").append(escapeJson(o.getCustomer().getEmail())).append("\",");
                sb.append("\"status\":\"").append(escapeJson(o.getStatus())).append("\",");
                
                sb.append("\"items\":[");
                ArrayListADT<CartItem> items = o.getItems();
                for(int j=0; j<items.size(); j++) {
                    CartItem ci = items.get(j);
                    sb.append("{");
                    sb.append("\"bookId\":").append(ci.getBookId()).append(",");
                    sb.append("\"title\":\"").append(escapeJson(ci.getTitle())).append("\",");
                    sb.append("\"quantity\":").append(ci.getQuantity()).append(",");
                    sb.append("\"price\":").append(ci.getPrice());
                    sb.append("}");
                    if(j < items.size()-1) sb.append(",");
                }
                sb.append("]");

                sb.append("}");
                if(i < tmp.size()-1) sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        }

        private String escapeJson(String str) {
            if (str == null) return "";
            return str.replace("\\", "\\\\").replace("\"", "\\\"");
        }
        private String extractString(String json, String key) {
            int idx = json.indexOf(key + ":");
            if (idx == -1) return "";
            int start = json.indexOf("\"", idx + key.length() + 1) + 1;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }
        private int extractInt(String json, String key) {
            int idx = json.indexOf(key + ":");
            if (idx == -1) {
                // Sometimes keys are unquoted in JS stringify or differently spaced
                idx = json.indexOf(key.replace("\"", "") + ":");
                if (idx == -1) return 0;
            }
            int start = idx + key.length() + 1;
            // Handle when start contains spaces
            while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == ':')) {
                start++;
            }
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            if (end == -1) end = json.length(); // Fallback if no delimiter

            if (start >= end) return 0; // Fixes StringIndexOutOfBoundsException begin 25, end -1
            
            try {
                return Integer.parseInt(json.substring(start, end).replace("\"", "").replace("}", "").strip());
            } catch (Exception e) {
                return 0; // Fallback to 0 if parsing fails
            }
        }
        private String extractArray(String json, String key) {
            int idx = json.indexOf(key + ":");
            if (idx == -1) return "[]";
            int start = json.indexOf("[", idx);
            int end = json.indexOf("]", start);
            return json.substring(start + 1, end).strip();
        }
    }

    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes());
                
                String email = extractString(requestBody, "\"email\"");
                String password = extractString(requestBody, "\"password\"");
                String role = extractString(requestBody, "\"role\"");

                boolean success = false;
                String name = "";

                if ("admin".equals(role)) {
                    Admin admin = adminBuddy.loginAdmin(email, password);
                    if (admin != null) {
                        success = true;
                        name = admin.getName();
                    }
                } else {
                    Customer customer = customerBuddy.loginCustomer(email, password);
                    if (customer != null) {
                        success = true;
                        name = customer.getName();
                    }
                }

                String responsePath = success ? "{\"success\":true,\"name\":\"" + name + "\"}" : "{\"success\":false,\"message\":\"Invalid email or password\"}";
                int statusCode = success ? 200 : 401;

                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(statusCode, responsePath.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(responsePath.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }

        private String extractString(String json, String key) {
            int idx = json.indexOf(key + ":");
            if (idx == -1) return "";
            int start = json.indexOf("\"", idx + key.length() + 1) + 1;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }
    }

    static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes());
                
                String name = extractString(requestBody, "\"name\"");
                String email = extractString(requestBody, "\"email\"");
                String password = extractString(requestBody, "\"password\"");
                String role = extractString(requestBody, "\"role\"");
                String adminKey = extractString(requestBody, "\"adminKey\"");

                // Ideally should check for existing email, but simple logic for now
                if ("admin".equals(role)) {
                    if (!"abc".equals(adminKey)) {
                        String errorResponse = "{\"success\":false, \"message\":\"Invalid admin key.\"}";
                        exchange.getResponseHeaders().add("Content-Type", "application/json");
                        exchange.sendResponseHeaders(400, errorResponse.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(errorResponse.getBytes());
                        os.close();
                        return;
                    }
                    Admin admin = adminBuddy.createAdmin(name, email, password);
                    adminBuddy.registerAdmin(admin);
                } else {
                    Customer customer = customerBuddy.createCustomer(name, email, password, "Not Provided");
                    customerBuddy.registerCustomer(customer);
                }

                String responsePath = "{\"success\":true}";
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, responsePath.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(responsePath.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }

        private String extractString(String json, String key) {
            int idx = json.indexOf(key + ":");
            if (idx == -1) return "";
            int start = json.indexOf("\"", idx + key.length() + 1) + 1;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }
    }
}

