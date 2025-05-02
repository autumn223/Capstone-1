package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class HomeScreen {


    private static boolean running = true;

    public static void main(String[] args) {
        // Load existing transactions from the file
        List<Transaction> transactions = TransactionFileManager.readFile();
        Scanner scanner = new Scanner(System.in);

        // Main application loop
        while (running) {
            displayMainMenu(); // Show main menu options
            String choice = scanner.nextLine().trim().toUpperCase(); // Read user choice

            switch (choice) {
                case "A":
                    handleAddDeposit(transactions, scanner);
                    break;
                case "B":
                    handleMakePayment(transactions, scanner);
                    break;
                case "C":
                    showLedgerOptions(transactions, scanner);
                    break;
                case "D":
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Displays the main menu options
    private static void displayMainMenu() {
        System.out.println("\nMain Menu");
        System.out.println("A) Add Deposit");
        System.out.println("B) Make Payment");
        System.out.println("C) View Ledger");
        System.out.println("D) Exit");
        System.out.print("Select an option: ");
    }

    // Shows the ledger sub-menu and handles its options
    private static void showLedgerOptions(List<Transaction> transactions, Scanner scanner) {
        boolean viewingLedger = true;

        while (viewingLedger) {
            System.out.println("\nLedger Options");
            System.out.println("A) View All Transactions");
            System.out.println("B) View Deposits Only");
            System.out.println("C) View Payments Only");
            System.out.println("D) View Reports");
            System.out.println("E) Return to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A":
                    displayAllTransactions(transactions);
                    break;
                case "B":
                    displayDepositsOnly(transactions);
                    break;
                case "C":
                    displayPaymentsOnly(transactions);
                    break;
                case "D":
                    showReportOptions(transactions, scanner);
                    break;
                case "E":
                    viewingLedger = false; // Exit ledger menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Shows the report sub-menu and handles its options
    private static void showReportOptions(List<Transaction> transactions, Scanner scanner) {
        boolean viewingReports = true;

        while (viewingReports) {
            System.out.println("\nReport Options");
            System.out.println("A) Current Month");
            System.out.println("B) Previous Month");
            System.out.println("C) Current Year");
            System.out.println("D) Previous Year");
            System.out.println("E) Search by Vendor");
            System.out.println("F) Return to Ledger Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A":
                    reportCurrentMonth(transactions);
                    break;
                case "B":
                    reportPreviousMonth(transactions);
                    break;
                case "C":
                    reportCurrentYear(transactions);
                    break;
                case "D":
                    reportPreviousYear(transactions);
                    break;
                case "E":
                    searchByVendor(transactions, scanner);
                    break;
                case "F":
                    viewingReports = false; // Exit report menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handles user input to create and record a deposit
    private static void handleAddDeposit(List<Transaction> transactions, Scanner scanner) {
        System.out.println("\nAdd Deposit");

        System.out.print("Enter Vendor Name: ");
        String vendor = scanner.nextLine().trim();

        System.out.println("Enter description:");
        String description = scanner.nextLine().trim();

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        if (amount <= 0) {
            System.out.println("Deposit must be greater than 0.");
            return;
        }

        // Create a deposit transaction (positive amount)
        Transaction deposit = new Transaction(
                LocalDate.now(), LocalTime.now(), description, vendor, amount);

        // Save to file and memory
        TransactionFileManager.appendTransaction(deposit);
        transactions.add(deposit);

        System.out.println("Deposit recorded successfully.");
    }

    // Handles user input to create and record a payment
    private static void handleMakePayment(List<Transaction> transactions, Scanner scanner) {
        System.out.println("\nMake Payment");

        System.out.print("Enter Vendor Name: ");
        String vendor = scanner.nextLine().trim();

        System.out.println("Enter description:");
        String description = scanner.nextLine().trim();

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        if (amount <= 0) {
            System.out.println("Payment must be greater than 0.");
            return;
        }

        amount = -amount; // Convert to negative for payments

        // Create a payment transaction (negative amount)
        Transaction payment = new Transaction(
                LocalDate.now(), LocalTime.now(), description, vendor, amount);

        // Save to file and memory
        TransactionFileManager.appendTransaction(payment);
        transactions.add(payment);

        System.out.println("Payment recorded successfully.");
    }

    // Displays every transaction
    private static void displayAllTransactions(List<Transaction> transactions) {
        System.out.println("\nAll Transactions");
        for (Transaction t : transactions) {
            printTransaction(t);
        }
    }

    // Displays only deposits (positive amounts)
    private static void displayDepositsOnly(List<Transaction> transactions) {
        System.out.println("\nDeposits Only");
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) printTransaction(t);
        }
    }

    // Displays only payments (negative amounts)
    private static void displayPaymentsOnly(List<Transaction> transactions) {
        System.out.println("\nPayments Only");
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) printTransaction(t);
        }
    }

    // Shows transactions from the current month
    private static void reportCurrentMonth(List<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        System.out.println("\nCurrent Month");
        for (Transaction t : transactions) {
            if (t.getDate().getMonth() == today.getMonth() &&
                    t.getDate().getYear() == today.getYear()) {
                printTransaction(t);
            }
        }
    }

    // Shows transactions from the previous month
    private static void reportPreviousMonth(List<Transaction> transactions) {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        System.out.println("\nPrevious Month");
        for (Transaction t : transactions) {
            if (t.getDate().getMonth() == lastMonth.getMonth() &&
                    t.getDate().getYear() == lastMonth.getYear()) {
                printTransaction(t);
            }
        }
    }

    // Shows transactions from the current year
    private static void reportCurrentYear(List<Transaction> transactions) {
        int year = LocalDate.now().getYear();
        System.out.println("\nCurrent Year");
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == year) {
                printTransaction(t);
            }
        }
    }

    // Shows transactions from the previous year
    private static void reportPreviousYear(List<Transaction> transactions) {
        int lastYear = LocalDate.now().getYear() - 1;
        System.out.println("\nPrevious Year");
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == lastYear) {
                printTransaction(t);
            }
        }
    }

    // Searches for transactions by vendor name
    private static void searchByVendor(List<Transaction> transactions, Scanner scanner) {
        System.out.print("Enter vendor name to search: ");
        String input = scanner.nextLine().trim().toLowerCase();

        System.out.println("\nSearch Results");
        for (Transaction t : transactions) {
            if (t.getVendor().toLowerCase().contains(input)) {
                printTransaction(t);
            }
        }
    }

    // Utility method to print a formatted transaction
    private static void printTransaction(Transaction t) {
        System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f%n",
                t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
    }


    private static void exitProgram() {
        System.out.println("Exiting application...");
        running = false;
    }
}