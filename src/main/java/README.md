* A record is used to store information about a book 
with the fields title, author and isbn. 
Since the book information is immutable, meaning the information doesn't change, 
a record is a good choice for representing a book.

* A record is also used to store information about loan information 
with fields Member and book. This way I can link a member to a book easily and represent them
as one object.

The member class represents a member in the library system. 
It contains fields for the member's name, id etc. setters and getters where needed. 
It also has an own method maxActiveLoans() to verify the maximum allowed number of active loans
when a book is loaned out using borrowBook() in Library.

The main class represents the main entry point of the library system. 
It contains a main method that creates an instance of the Library class which I call Bookweb. It uses
Scanner to read user input, handle wrong inputs and control the interactive menu to choose which operation to perform with
an enhanced switch statement.

The Library class represents the deeper functionality of the library system. It contains fields for the list of books, 
list of members and list of loans. It also has methods to add books, members and loans, 
borrow books, return books and display information about books, members and loans, which are
accessible through the main class.
* Since we can only use arrays with a fixed size, if the arrays are filled to their maximum capacity, 
a new array with double the size has to be created and the old array is copied to the new array.
* If Arraylist was allowed, it would have made it easier to manage the arrays, as there would be no need to 
manually resize the array and copy its contents.
* Since both the Books and Members array was made to have dynamic capacity, it followed
to also make the Loans array dynamic.
* A simple bubble sort algorithm was implemented to sort the books in ShowAllBooks by title. Moreover it shows
the status of the books, i.e., whether the book is available or not.
* Lastly some statistics were implemented to show the member with the most loans. 

Possible Improvements:
* Make it perhaps easier to register new members, using only name. Since
I used a unique 4 digit id number. Which is then used to identify the member when borrowing a book.
* Implement better statistics to for example show the total ranking of all members based on the number of loans.
Instead of only showing the member with the most loans.
* To also include Scanner in Library, so that it can be used throughout the class. Instead of basic readline
and try-catch.
* To implement a more efficient sorting algorithm, such as quicksort or mergesort, to improve the performance of sorting the books.
* To implement a more efficient data structure for storing books, members and loans, such as a hash table or a binary search tree, 
to improve the performance of searching and sorting operations. But it's beyond the scope of this lab.