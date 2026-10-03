package Laboration1;

import java.util.Scanner;

public class Main {
    static void main() {
        Library bookWeb = new Library();
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean running = true;

        do {
            IO.println("""
                    ======================================================
                    Welcome to BookWeb! A digital library management system.
                    Please make your menu choice:
                    ======================================================
                    1. Add book.
                    2. Register member.
                    3. Borrow book.
                    4. Return book.
                    5. Search book (title or author, partial or full word).
                    6. Show all books and status.
                    7. Show member(s) with the most active loans.
                    8. Exit.
                    ======================================================
                    """);

            IO.println("Enter your choice (1-8): ");

            // Read user input safely and handle invalid input
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
            } else {
                IO.println("Invalid input. Please enter a number between 1 and 8.");
                scanner.nextLine(); // Clear the invalid input
                continue;
            }

            //Processing the user's choice with enhanced switch
            switch (choice) {
                case 1 -> bookWeb.addBook();
                case 2 -> bookWeb.registerMember();
                case 3 -> bookWeb.borrowBook();
                case 4 -> bookWeb.returnBook();
                case 5 -> bookWeb.searchBook();
                case 6 -> bookWeb.showAllBooks();
                case 7 -> bookWeb.showMembersWithMostLoans();
                case 8 -> {
                    IO.println("Thank you for using BookWeb. Goodbye!");

                    running = false;
                }
                default -> IO.println("Invalid choice. Please select a number between 1 and 8.");
            }

            IO.println(); // Prints a blank line for spacing

        } while (running);

        scanner.close();
    }
}
