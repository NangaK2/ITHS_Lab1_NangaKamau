package LibraryManager;

public class Library {

    private Book[] books = new Book[10];
    private int numberOfBooks = 0;
    private Member[] members = new Member[10];
    private int numberOfMembers = 0;
    private Loan[] loanedBooks = new Loan[10];
    private int numberOfLoans = 0;

    public void addBook() {
        //To manage the case where the library is full.
        if (numberOfBooks >= books.length) {
            Book[] newBooks = new Book[books.length * 2];
            //Copy the existing books to the new array.
            System.arraycopy(books, 0, newBooks, 0, books.length);      //Instead of System.arraycopy, a for loop can be used to copy the books.
            books = newBooks;                                                          //Points to the new bigger array (double size) and copies of the existing books.
        }

        //Handle invalid input, empty title, empty author, empty isbn
        try {
            //While loop to avoid starting from the menu if empty inputs are entered
            while (true) {
                String title = IO.readln("Enter the title of the book: ");
                if(title.isEmpty()){
                    IO.println("Title cannot be empty. Please enter a valid title.");
                    continue;
                }

                String author = IO.readln("Enter the author of the book: ");
                if(author.isEmpty()){
                    IO.println("Author cannot be empty. Please enter a valid author.");
                    continue;
                }

                String isbn = IO.readln("Enter the isbn of the book: ");
                if(isbn.isEmpty()){
                    IO.println("Isbn cannot be empty. Please enter a valid isbn.");
                    continue;
                }

                Book book = new Book(title, author, isbn);
                books[numberOfBooks] = book;
                numberOfBooks++;
                IO.println("The book " + title + " has been added successfully!");
                break;
            }
        } catch(NumberFormatException e){   //Is not actually needed here since isbn was changed to type string
                IO.println("Invalid ISBN format. Please enter a valid ISBN using numbers");
            }
    }

    public void registerMember() {
        //To manage the case where the Members list is full.
        if (numberOfMembers >= members.length) {
            Member[] newMembers = new Member[members.length * 2];
            //Copy the existing members to the new array.
            System.arraycopy(members, 0, newMembers, 0, members.length);
            members = newMembers;
        }

        //Handles invalid name and id number input. Absolutely needed here since id is a 4-digit int where isbn in addBook() is not.
        try {
            //While loop to not start over from the starting menu when invalid/occupied member id input is given or empty name input.
            while (true) {
                String name = IO.readln("Enter the name of the member: ");
                if (name.isEmpty()) {
                    IO.println("No name entered. Please enter a valid name.");
                    continue;
                }

                int id = Integer.parseInt(IO.readln("Enter the members intended id number using 4-digits: "));
                if (id < 1000 || id > 9999) {
                    IO.println("Id number is not 4-digits. Please enter a valid 4-digit integer.");
                    continue;
                }

                //Handles the case where a member with the same id already exists.
                if (takenID(id)) {
                    IO.println("Member with the same id already exists. Enter a different valid id number.");
                    continue;
                }

                //If no member with the same id exists and id is valid, creates a new member and adds it to the array members.
                Member member = new Member(name, id);
                members[numberOfMembers] = member;
                numberOfMembers++;
                IO.println("Member registered successfully!");
                break;
            }

        } catch(NumberFormatException e){
                IO.println("Invalid id number. Please enter a valid 4-digit integer.");
            }
    }

    //Method for checking if a member with the same id already exists.
    public boolean takenID(int id) {
        for (Member member : members) {
            if (member != null && member.getId() == id)
                return true;
        }
        return false;
    }

    public void borrowBook() {
        //Since the member ID is unique, we can use it to find the member instead of name.
        int id = 0;
        String name = "";
        try {
            name = IO.readln("Name of member who wants to borrow a book: ");
            if (name.isEmpty()) {
                IO.println("No name entered. Please enter a valid name. Returning to main menu.");
                return;
            }

            id = Integer.parseInt(IO.readln("Enter the id number of the member who wants to borrow a book: "));
            if (id < 1000 || id > 9999) {
                IO.println("Invalid id number. Please enter a valid 4-digit integer. Returning to main menu.");
                return;
            }

        } catch (NumberFormatException e) {
            IO.println("Invalid id number. Please enter a valid 4-digit integer. Returning to main menu.");
        }

        //Find the member by ID in the members array if valid id number has been entered.
        Member targetMember = null;
        for (Member member : members) {
            if (member != null && member.getId() == id) {
                targetMember = member;
                break;
            }
        }

        if (targetMember == null) {
            IO.println("Member: " + name + " with ID: " + id + " is not registered. Returning to main menu.");
            return;
        }

        //Check if the member can add books to their active loans array
        if (targetMember.maxActiveLoans()) {
            IO.println("Member: " + targetMember.getName() + " has reached their maximum borrowing limit (3 books). Returning to main menu.");
            return;
        }

        String targetSearch = IO.readln("Enter the title or author of the book you want to borrow: ");
        Book[] bookMatches = findBook(targetSearch);
        int matchCounter = 0;               //Variable to count how many matching books are found in bookMatches.
        for (Book bookMatch : bookMatches) {
            if (bookMatch != null) {
                matchCounter++;
            }
        }
        if (matchCounter == 0) {
            IO.println("No books found with the title or author: " + targetSearch + ". Returning to main menu.");
            return;
        }

        Book targetBook;
        if (matchCounter == 1)       //Only one matching book found, it is found at index 0.
            targetBook = bookMatches[0];
        else {
            IO.println("Multiple books found with the title or author " + targetSearch + ". Please specify which book you want to borrow.");
            for (int i = 0; i < matchCounter; i++) {
                IO.println((i + 1) + ". " + bookMatches[i].title() + " - " + bookMatches[i].author());
            }

            //To handle multiple matches
            while (true) {
                try {
                    int choice = Integer.parseInt(IO.readln("Enter the number of the book you want to borrow: "));
                    if (choice >= 1 && choice <= matchCounter) {
                        targetBook = bookMatches[choice - 1];       //Since the user input is 1-based, we need to subtract 1 to get the correct index.
                        break;
                    } else {
                        IO.println("Invalid choice. Please try again, enter a number between 1 and " + matchCounter + ": ");
                    }
                } catch (NumberFormatException e) {
                    IO.println("Invalid input. Please enter a valid number.");
                }
            }
        }

        //Need a check if the book is already loaned. Need to implement a new record Loan with the book and member.
        //Then add the loan to the loanedBooks array.
        for (int i = 0; i < numberOfLoans; i++) {
            if (loanedBooks[i].book().equals(targetBook)) {
                IO.println("Book is already loaned.");
                return;
            }
        }

        //To manage the case when the array loanedBooks is full. Double the size of the array.
        if (numberOfLoans >= loanedBooks.length) {
            Loan[] newLoans = new Loan[loanedBooks.length * 2];
            //Copy the existing loans to the new array.
            System.arraycopy(loanedBooks, 0, newLoans, 0, loanedBooks.length);
            loanedBooks = newLoans;
        }

        Loan newLoan = new Loan(targetMember, targetBook);
        loanedBooks[numberOfLoans++] = newLoan;

        targetMember.increaseActiveLoan();
        IO.println("Book loaned successfully. Member: " + targetMember.getName() + " has now loaned " + targetBook.title() + ".");
        IO.println("Active loans for member: " + targetMember.getActiveLoans());
    }


    public Book[] findBook(String targetSearch) {
        Book[] bookMatches = new Book[numberOfBooks];
        int numberOfMatches = 0;

        for (int i = 0; i < numberOfBooks; i++) {
            if (books[i].author().toLowerCase().contains(targetSearch.toLowerCase()) || books[i].title().toLowerCase().contains(targetSearch.toLowerCase())) {
                bookMatches[numberOfMatches++] = books[i];
            }
        }
        return bookMatches;
    }

    public void returnBook() {
        //Unsure if functionality should include that only the member with the loaned book can return that book. Or if it is general?
        //Assumption is it is general.
//        int id = 0;
//        String name = "";
//        try {
//            name = IO.readln("Name of member who wants to return a book: ");
//            if (name.isEmpty()) {
//                IO.println("No name entered. Please enter a valid name. Returning to main menu.");
//                return;
//            }
//
//            id = Integer.parseInt(IO.readln("Enter the id number of the member who wants to return a book: "));
//            if (id < 1000 || id > 9999) {
//                IO.println("Invalid id number. Please enter a valid 4-digit integer. Returning to main menu.");
//                return;
//            }
//
//        } catch (NumberFormatException e) {
//            IO.println("Invalid id number. Please enter a valid 4-digit integer. Returning to main menu.");
//        }
//
//        //Find the member by ID in the members array if valid id number has been entered.
//        Member targetMember = null;
//        for (Member member : members) {
//            if (member != null && member.getId() == id) {
//                targetMember = member;
//                break;
//            }
//        }
//        //Continue

        String targetSearch = IO.readln("Which book do you want to return. Enter title or part of title: ");
        Loan[] loanMatches = new Loan[numberOfLoans];
        int matchCounter = 0;
        Loan targetLoan = null;

        //Loop through the loanedBooks array to find matching loans based on the target search title.
        for (int i = 0; i < numberOfLoans; i++) {
            if (loanedBooks[i].book().title().toLowerCase().contains(targetSearch.toLowerCase())) {
                loanMatches[matchCounter++] = loanedBooks[i];
            }
        }
        if (matchCounter == 0) {
            IO.println("No matching loans found. Returning to main menu");
            return;
        }

        if (matchCounter == 1) {
            IO.println("Found 1 matching loan:");
            IO.println(loanMatches[0].book().title() + " - " + loanMatches[0].book().author() + "- Loaned by " + loanMatches[0].member().getName());
            targetLoan = loanMatches[0];
        } else if (matchCounter > 1) {
            IO.println("Found " + matchCounter + " matching loans:");
            for (int i = 0; i < matchCounter; i++) {
                //IO.println(loanMatches[i].book().title());
                IO.println((i + 1) + ". " + loanMatches[i].book().title() + " - " + loanMatches[i].book().author() +
                        "- Loaned by " + loanMatches[i].member().getName());
            }

            //To handle multiple matches
            while (true) {
                try {
                    int choice = Integer.parseInt(IO.readln("Enter the number of the book you want to return: "));
                    if (choice >= 1 && choice <= matchCounter) {
                        targetLoan = loanMatches[choice - 1];       //Since the user input is 1-based, we need to subtract 1 to get the correct index.
                        break;
                    } else {
                        IO.println("Invalid choice. Please try again, enter a number between 1 and " + matchCounter + ": ");
                    }
                } catch (NumberFormatException e) {
                    IO.println("Invalid input. Please enter a valid number.");
                }
            }
        }

        IO.println("Want to return loan: " + targetLoan.book().title() + " - " + targetLoan.book().author() + " - Loaned by " + targetLoan.member().getName());
        //Confirm return of book
        while (true) {
            String input = IO.readln("Confirm return? (Yes/No): ");
            if (input.equalsIgnoreCase("yes")) {
                break;
            } else if (input.equalsIgnoreCase("no")) {
                IO.println("Book return aborted. Returning to main menu.");
                return;
            } else {
                IO.println("Invalid input. Please enter 'Yes' or 'No'.");
            }
        }

        //Remove targetLoan from loanedBooks list
        int targetLoanIndex = -1;
        for (int i = 0; i < numberOfLoans; i++) {
            if (loanedBooks[i].equals(targetLoan)) {
                targetLoanIndex = i;                      //Find index of targetLoan in loanedBooks list
                break;
            }
        }

        if (targetLoanIndex == -1) {
            IO.println("ERROR, targetLoan not found in loanedBooks list");
            return;
        }
        for(int i = targetLoanIndex; i < numberOfLoans - 1; i++) {
            loanedBooks[i] = loanedBooks[i + 1];                //Move all loans after targetLoan one index down
        }
        loanedBooks[numberOfLoans - 1] = null;                  //Set last loan in list to null
        numberOfLoans--;                                        //Decrease number of loans
        targetLoan.member().decreaseActiveLoan();               //Decrease active loan count for member who borrowed targetLoan
        IO.println(targetLoan.book().title()  + " has been returned.");

        //Print active loan count for member who returned book
        IO.println("Active loan count for " + targetLoan.member().getName() + " is now " + targetLoan.member().getActiveLoans());

        //Show remaining active loans for member who returned book
        if (targetLoan.member().getActiveLoans() > 0) {
            IO.println("Borrowed books by member: " + targetLoan.member().getName());
            for (int i = 0; i < numberOfLoans; i++) {
                if (loanedBooks[i].member().equals(targetLoan.member())) {
                    IO.println("- " + loanedBooks[i].book().title());
                }
            }
        }
    }

    public void searchBook() {
    }

    public void showAllBooks() {
    }

    public void showMembersWithMostLoans() {
    }
}
