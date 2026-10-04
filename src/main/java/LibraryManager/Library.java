package LibraryManager;

public class Library {

    private Book[] books = new Book[10];
    private int numberOfBooks = 0;
    private Member[] members = new Member[10];
    private int numberOfMembers = 0;

    public void addBook() {
        //To manage the case where the library is full.
        if (numberOfBooks >= books.length) {
            Book[] newBooks = new Book[books.length * 2];
            //Copy the existing books to the new array.
            System.arraycopy(books, 0, newBooks, 0, books.length);      //Instead of System.arraycopy, a for loop can be used to copy the books.
            books = newBooks;                                                          //Points to the new bigger array (double size) and copies of the existing books.
        }

        //Handle invalid input, ** update, unnecessary since changing isbn from int to string.
        try {
            String title = IO.readln("Enter the title of the book: ");
            String author = IO.readln("Enter the author of the book: ");
            String isbn = IO.readln("Enter the ISBN of the book: ");        //**

            Book book = new Book(title, author, isbn);
            books[numberOfBooks] = book;
            numberOfBooks++;
        }
        catch (NumberFormatException e) {
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
                if (takenID(members, id)) {
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

    public boolean takenID(Member[] members, int id) {
        for (Member member : members) {
            if (member != null && member.getId() == id)
                return true;
        }
        return false;
    }

    public void borrowBook() {
    }

    public void returnBook() {
    }

    public void searchBook() {
    }

    public void showAllBooks() {
    }

    public void showMembersWithMostLoans() {
    }
}
