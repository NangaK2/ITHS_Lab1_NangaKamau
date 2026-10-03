package Laboration1;

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
            System.arraycopy(books, 0, newBooks, 0, books.length);
            books = newBooks;
        }

        String title = IO.readln("Enter the title of the book: ");
        String author = IO.readln("Enter the author of the book: ");
        String isbn = IO.readln("Enter the ISBN of the book: ");

        Book book = new Book(title, author, isbn);
        books[numberOfBooks] = book;
        numberOfBooks++;
    }
}
