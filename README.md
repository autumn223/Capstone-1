![AL1](https://github.com/user-attachments/assets/d2a98e91-880c-4a2f-932e-44b2d95d7e1c)
![AL2](https://github.com/user-attachments/assets/4043399a-6dee-4211-87db-479c7a307957)
![AL3](https://github.com/user-attachments/assets/e2ec5d3a-c3c2-4df0-9f37-284308f3b9a2)
![AL4](https://github.com/user-attachments/assets/6f5019ec-bf45-461f-84d6-29d141f3aba4)
![AL5](https://github.com/user-attachments/assets/a3e8516d-2ba8-4e6b-9747-7eff024e3900)
![AL6](https://github.com/user-attachments/assets/d3e7b2b2-8c78-4bf1-a9dc-67209e05d800)
![AL7](https://github.com/user-attachments/assets/44a29d56-9e5c-4d0e-81e7-0ec95c98c09b)
![al8](https://github.com/user-attachments/assets/a27ff10d-dfff-495a-8076-2782f595dbe6)
![al9](https://github.com/user-attachments/assets/2bff74c0-7bf3-4324-96d6-91f26e53911c)
![al10](https://github.com/user-attachments/assets/9344c1b9-0c1b-44f1-ac04-943eee22a943)
![al11](https://github.com/user-attachments/assets/8019d20b-9cad-4ff0-8e41-4645dd166f9d)
![al12](https://github.com/user-attachments/assets/7f722b92-27f9-445d-8160-e89ba1ff9d9b)
![al13](https://github.com/user-attachments/assets/d2fbf9ab-3fc1-4617-be33-a2f8bec4d612)



This is a simple console-based Java app that lets users track their personal finances.
It allows you to:
- Add deposits 
-  Make payments 
-  View your transaction ledger
-  Run basic reports (e.g. monthly totals, search by vendor)
-  Save and load data from a CSV file

  How it works:
  The main file HomeScreen.java controls the menus and user input.

  Each transaction is stored in a Transaction object.

  All transactions are saved in a CSV file (transactions.csv).

  The program reads and writes this file using TransactionFileManager.java.

  One cool feature of this app is how it writes your transactions to a file

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





