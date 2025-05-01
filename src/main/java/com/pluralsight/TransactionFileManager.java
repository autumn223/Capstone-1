package com.pluralsight;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileManager {

    public static List<Transaction> readFile() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {

            bufferedReader.readLine();

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                if (input.trim().isEmpty()) continue;

                String[] row = input.split("\\|");
                if (row.length < 5) continue;

                try {
                    LocalDate date = LocalDate.parse(row[0].trim());
                    LocalTime time = LocalTime.parse(row[1].trim());
                    String description = row[2].trim();
                    String vendor = row[3].trim();
                    double amount = Double.parseDouble(row[4].trim());

                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    transactions.add(transaction);
                } catch (Exception e) {
                    System.out.println("Skipping invalid row: " + input);
                }
            }

        } catch (IOException ex) {
            System.out.println("Issue loading csv file: " + ex.getMessage());
        }

        return transactions;
    }

    public static void appendTransaction(Transaction transaction) {
        String filePath = "src/main/resources/transactions.csv";
        File file = new File(filePath);

        try {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            boolean fileExists = file.exists();
            boolean isEmpty = !fileExists || file.length() == 0;

            FileWriter writer = new FileWriter(file, true);

            if (isEmpty) {
                writer.write("date|time|description|vendor|amount\n");
            }

            writer.write(transaction.toString() + "\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("Something went wrong while saving the transaction.");
            e.printStackTrace();
        }
    }
}
