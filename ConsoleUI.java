package BooksApplication;

import java.io.Console;
import java.util.Scanner;

public class ConsoleUI {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    private Scanner scanner;
    private Console console;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.console = System.console();
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    public void clearScreen() {
        // Works nicely on Mac/Linux terminals
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printHeader(String title) {
        clearScreen();
        System.out.println(CYAN + BOLD + "============================================================" + RESET);
        System.out.println(CYAN + BOLD + "   " + title.toUpperCase() + RESET);
        System.out.println(CYAN + BOLD + "============================================================" + RESET);
        System.out.println();
    }

    public void printSuccess(String message) {
        System.out.println(GREEN + "✔ " + message + RESET);
    }

    public void printError(String message) {
        System.out.println(RED + "✘ " + message + RESET);
    }

    public void printWarning(String message) {
        System.out.println(YELLOW + "⚠ " + message + RESET);
    }

    public void waitForEnter() {
        System.out.println();
        System.out.print(CYAN + "Press [ENTER] to continue..." + RESET);
        scanner.nextLine();
    }

    public String promptString(String prompt) {
        System.out.print(YELLOW + prompt + " " + RESET);
        return scanner.nextLine().trim();
    }

    public String promptPassword(String prompt) {
        if (console != null) {
            char[] passwordArray = console.readPassword(YELLOW + prompt + " " + RESET);
            return new String(passwordArray);
        } else {
            return promptString(prompt + " (Warning: visible input):");
        }
    }

    public int promptInt(String prompt) {
        while (true) {
            System.out.print(YELLOW + prompt + " " + RESET);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printError("Invalid input. Please enter a valid number.");
            }
        }
    }
}
