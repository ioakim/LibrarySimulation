package javacourseworkapplication2;

/**
 *@author Ioakim Ioakim(100134028)
 * 
 */
public class LibraryBook {
    //the fields of the library book Class
    private String author;
    private String title;
    private String libraryClassification;
    private status currentStatus;
    private int numberOfPages;
    private int numOfTimesBorrowed;
    private int numOfPendingReservations;
    
    //enumerated type for the status of a book
    public enum status {REFERENCE_ONLY , ON_LOAN, AVAILABLE_FOR_LENDING}
    
    //public variable to keep track of the on-loan books
    public static int booksOnLoan = 0;
    
    //new book constructor
    public LibraryBook(String bookAuthor, String bookTitle, int bookPages){
        this.author = bookAuthor;
        this.title = bookTitle;
        this.libraryClassification = null;
        this.currentStatus = null;
        this.numberOfPages = bookPages;
        this.numOfTimesBorrowed = 0;
        this.numOfPendingReservations = 0;
    }
    
    //method to get the author of a book
    public String getAuthor(){
        return author;
    }
    
    //method to get the title of a book
    public String getTitle(){
        return title;
    }
    
    //method to get a book's number of pages
    public int getPagesNumber(){
        return numberOfPages;
    }
    
    //method to get the classification of a book
    public String getClassification(){
        return libraryClassification;
    }
    
    //method to get the number of times has been borrowed
    public int getNumbersBorrowed(){
        return numOfTimesBorrowed;
    } 
    
    //method to set the classification of a book
    public boolean setClassification(String bookClass){
        
        if (bookClass.length()>=3){
            this.libraryClassification = bookClass;
            return true;
        }else{
            return false;
        }
    }
    
    //method to set the status of a book as REFERENCE ONLY
    public status setAsReferenceOnly(){
        currentStatus = status.REFERENCE_ONLY;
        return currentStatus;
    }
    
    //method to set the status of a book as AVAILABLE FOR LENDING
    public status setAsForLending(){
        currentStatus = status.AVAILABLE_FOR_LENDING;
        return currentStatus;
    }
    
    //method to check if a book is available for lending
    public boolean isAvailable(){
        if (currentStatus == status.AVAILABLE_FOR_LENDING){
            return true;
        }else{
            return false;
        }
    }
    
    //method to check if a book can be reserved
    public boolean reserveBook(){
        if (currentStatus == status.ON_LOAN & numOfPendingReservations<3){
            return true;
        }else{
            return false;
        }
    }
    
    //method to loan out a book
    public  void borrowBook(){
        currentStatus = status.ON_LOAN;
        booksOnLoan=booksOnLoan+1;
    }
    
    //method to return a book
    public void returnBook(){
        currentStatus= status.AVAILABLE_FOR_LENDING;
        booksOnLoan=booksOnLoan-1;
    }
    
    @Override
    public String toString(){
            StringBuilder buildStr = new StringBuilder("Title: ");
            buildStr.append(title).append("\n\n");
            buildStr.append("\tAuthor:\t\t\t");
            buildStr.append(author).append("\n");
            buildStr.append("\tPages:\t\t\t");
            buildStr.append(numberOfPages).append("\n");
            buildStr.append("\tClassification:\t\t");
            buildStr.append(libraryClassification).append("\n");
            
            return buildStr.toString();
    }
    
    //method to get the number of pending reservations
    public int getPendingReservations(){
        return numOfPendingReservations;
    }
    
    //method to increase the number of pending reservations
    public int increaseNumOfReservations(){
        numOfPendingReservations = numOfPendingReservations+1;
        return numOfPendingReservations;
    }
    
    //method to decrease the number of pending reservations
    public int decreaseNumOfReservations(){
        numOfPendingReservations = numOfPendingReservations-1;
        return numOfPendingReservations;
    }
    
    //method to get the status of a book
    public status getStatus(){
        return currentStatus;
    }
}
