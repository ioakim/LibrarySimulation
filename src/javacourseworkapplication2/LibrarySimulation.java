package javacourseworkapplication2;
/**
 * @author Ioakim Ioakim(100134028)
 */

import java.util.Random;//importing Random utility

public class LibrarySimulation {

    public static LibraryBook [] generateBookStock(){
        /**
        * A method to generate a collection of LibraryBook objects to use as
        * test data in the simulation
        * @return an array of LibraryBook objects
        *
        */
        
        String [] authorsList =//list of authors
            { "Lewis and Loftus", "Mitrani", "Goodrich",
              "Lippman", "Gross", "Baase", "Maclane", "Dahlquist",
              "Stimson", "Knuth", "Hahn", "Cormen and Leiserson",
              "Menzes", "Garey and Johnson"};
        
        String [] titlesList =//list of book titles
            { "Java Software Solutions", "Simulation",
              "Data Structures", "C++ Primer", "Graph Theory",
              "Computer Algorithms", "Algebra", "Numerical Methods",
              "Cryptography","Semi-Numerical Algorithms",
              "Essential MATLAB", "Introduction to Algorithms",
              "Handbook of Applied Cryptography",
              "Computers and Intractability"};
        
        int [] pagesList = //list of book pages
            {832, 185, 695, 614, 586, 685, 590, 573, 475,
                            685, 301, 1175, 820, 338};

        int n = authorsList.length;

        LibraryBook [] bookStock = new LibraryBook[n];//creating a new array

        for(int i = 0; i < n; i++){//adding data to the bookStock array
            bookStock[i] = new LibraryBook(authorsList[i],
                               titlesList[i], pagesList[i]);
        }

        // set library classification for half of the LibraryBooks
        for(int i = 0; i < n; i=i+2){
                bookStock[i].setClassification("QA" + (99 - i));
        }

        // set approx. two thirds of LIbraryBooks in test data as
        // lending books
        for(int i = 0; i < 2*n/3; i++)
            bookStock[i].setAsForLending();
        
        // set approx. one third of LibraryBooks in test data as
        // reference-only
        for(int i = 2*n/3; i < n; i++)
            bookStock[i].setAsReferenceOnly();
        
        return bookStock;
    }
    
    public static String[] runSimulation(LibraryBook[] bookStock, 
                                                    int numberOFevents){
        /**
         * the runSimulation method that simulates the library activity
         */
        
        Random randomNumber=new Random();//Random constructor
        
        int n = bookStock.length;/**
                                 * int n is assigned the value of 
                                 * the array's length
                                 */
       
        String [] output= new String[numberOFevents];/**
                                                     * new array with the length 
                                                     * of the times the simulation
                                                     * will be executed
                                                     */
        
        int classificationIterations = 0;/**number that is going to be
                                         * increased every time an unclassified
                                         * book is classified so there are not
                                         * books with the same classification
                                         */
        
        for(int i=0; i<numberOFevents; i++){
            int index= randomNumber.nextInt(n);/**random number in the range
                                                    * of array's length to be 
                                                    * the selector of a book
                                                    * in the bookStock array
                                                    */
            
            //getting the classification of the selected book
            String classification = bookStock[index].getClassification();
            
            //getting the status of the selected book
            LibraryBook.status bookStatus= bookStock[index].getStatus();
            
            if (classification==null)//checking if book is unclassified
                {
                //giving a book a new classification
                bookStock[index].setClassification("QA" + (98 - classificationIterations));
                /**increasing the classificationIterations number so the next 
                *unclassified book doesn't get the same classification
                */
                classificationIterations=classificationIterations +2;
                //outputting the result of the event
                output[i] = (i + "  " +LibraryBook.booksOnLoan+ "   " + "--" 
                                                    +"   " + "BOOK IS CLASSIFIED");
                }
            else{//if a book is already classified the status of it is checked
                if (bookStatus == LibraryBook.status.REFERENCE_ONLY)
                    {/**if the book is reference only the event 
                     *outputs that is a reference only book
                     */ 
                    output[i] = (i+ "  " +LibraryBook.booksOnLoan + "  "
                                +  classification + "  " + "REFERENCE ONLY BOOK");
                    }
                else{
                if (bookStock[index].isAvailable()==true)
                    {/**if the book is available for lending the event
                     * loans out the book by calling the borrowBook method
                     * and it outputs that the book is loaned out
                     */
                    output[i] = (i + "  " + LibraryBook.booksOnLoan + "  "+ classification 
                                                         + "  "      + "BOOK IS LOANED OUT");
                    bookStock[index].borrowBook();
                    }
                else
                   if (bookStatus== LibraryBook.status.ON_LOAN)
                       {/**if the book is loaned out the event 
                        *  there are two cases
                        */
                       int caseLoaned=randomNumber.nextInt(2);//random number to decide the case
                       switch(caseLoaned){//case 0 is for trying to return the book
                                     //case 1 is for trying to place a reservation
                           case 0: ;
                                   if (bookStock[index].getPendingReservations()>0)
                                       {/**if the book that is trying to be returned
                                        * has pending reservations, it remains on loan
                                        *but the number of reservations is decreased
                                        * and the event output is that the book 
                                        * is still loaned out
                                        */
                                       bookStock[index].decreaseNumOfReservations();
                                       output[i] = (i + "  " +LibraryBook.booksOnLoan+ "  " + classification
                                                                             + "  "      + "BOOK IS LOANED OUT");
                                       }
                                   else{/**otherwise the book is returned by 
                                       *calling the returnBook method
                                       */ 
                                       output[i] = (i + "  " + LibraryBook.booksOnLoan + "  "+ classification 
                                                                              + "  "      + "BOOK IS RETURNED");
                                       bookStock[index].returnBook();
                                       }
                                    break;
                           case 1:  
                                if (bookStock[index].reserveBook()==true)
                                    {/**if the return value from the reserveBook
                                     * is true then a reservation is placed for
                                     * the book by increasing the reservations 
                                     * number by calling the 
                                     * increaseNumOfReservations method
                                     */
                                    bookStock[index].increaseNumOfReservations();
                                    output[i] = (i + "  "+ LibraryBook.booksOnLoan+ "  " + classification 
                                                         + "  "       + "RESERVATION PLACED FOR ON LOAN BOOK");
                                    }
                                else{/**if the return value from the reserveBook
                                     * is false then a reservation can't be placed 
                                     * for the book and the output of the event
                                     * is that a reservation can't be placed
                                     */
                                    output[i] = (i + "  " + LibraryBook.booksOnLoan+ "  " + classification 
                                                        + "  "     + "BOOK IS ON LOAN BUT CANNOT BE RESERVED");
                                    }
                            }    
                        }
                }        
            }
        } 
        return output;
    }
    
    public static void main(String[] args){
        LibraryBook [] stockCreator=new LibraryBook[14];//creating an array for the stock
        stockCreator=generateBookStock();
        
        String[] finalOutput = new String[14];//creating an array for the simulation output
        finalOutput=runSimulation(stockCreator,20);//run the simulation
        
        for (int i=0; i<20; i++)
            System.out.println(finalOutput[i]);//printing out the result of the simulation
    }
}
