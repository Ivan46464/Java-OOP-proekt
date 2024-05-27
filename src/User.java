import Exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
public abstract class User implements Serializable {
    private String username;
    private String password;
    private transient Scanner sc;
    public  User(String username, String password) throws Exception{
        setUsername(username);
        setPassword(password);
        sc = new Scanner(System.in);
    }
    public void setUsername(String username) throws InvalidUsernameException {
        if (username == null || username.length() < 2) {
            throw new InvalidUsernameException("Username cannot be null or empty or smaller than 2 chars");
        }
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) throws InvalidPasswordException {
        if (password == null || password.length() < 5) {
            throw new InvalidPasswordException("Password cannot be null or empty or smaller than 5 characters");
        }
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public Scanner getSc() {
        return sc;
    }
    public void setSc(Scanner sc) {
        this.sc = sc;
    }
    public void bookSort(ArrayList<Book> books, String tag, String order){
        if(!books.isEmpty()) {
            switch (tag.toLowerCase()) {
                case "title":
                    gnomeSortBooks(books, Comparator.comparing(book -> book.getTitle().toLowerCase()), order);
                    break;
                case "author":
                    gnomeSortBooks(books, Comparator.comparing(book -> book.getAuthor().toLowerCase()), order);
                    break;
                case "year":
                    gnomeSortBooks(books, Comparator.comparingInt(Book::getRealiseYear), order);
                    break;
                case "rating":
                    gnomeSortBooks(books, Comparator.comparingDouble(Book::getRating), order);
                    break;
                default:
                    System.out.println("Invalid option: " + tag);
                    return;
            }
            System.out.println("Sorted Books:");
            for (Book book : books) {
                System.out.println(book.getTitle() + ", " + book.getAuthor() + ", " + book.getRealiseYear() + ", " + book.getRating());
            }
        }
    }
    private static void gnomeSortBooks(ArrayList<Book> books, Comparator<Book> comparator, String order) {
        int index = 0;
        while (index < books.size()) {
            if (index == 0 || (order.equals("asc") && comparator.compare(books.get(index), books.get(index - 1)) >= 0)
                    || (order.equals("desc") && comparator.compare(books.get(index), books.get(index - 1)) <= 0)) {
                index++;
            } else {
                Book temp = books.get(index);
                books.set(index, books.get(index - 1));
                books.set(index - 1, temp);
                index--;
            }
        }
    }
    public void bookFind(ArrayList<Book> books, String tag ,String word) {
        Book book_to_find = null;
        for (Book book : books) {
            if (book.getUniqueNumber().equals(word)) {
                book_to_find = book;
                break;
            }
            if (book.getTitle().equals(word)){
                book_to_find=book;
                break;
            }
            if (book.getAuthor().equals(word)){
                book_to_find = book;
                break;
            }

        }
        if (book_to_find != null) {
            System.out.println("Book's title: " + book_to_find.getTitle());
            System.out.println("Book's author: " + book_to_find.getAuthor());
            System.out.println("Book's unique number: "+ book_to_find.getUniqueNumber());
        }
        else {
            System.out.println("Book with " + tag + " is not found.");
        }
    }
    public void bookAll(ArrayList<Book> books){
        if (!books.isEmpty()){
            for(Book book:books) {
                System.out.println("Tittle: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Genre: " + book.getGenre());
                System.out.println("Unique number: " + book.getUniqueNumber());
            }
        }else{
            System.out.println("There is no books in the library.");
        }
    }
    public void bookInfo(ArrayList<Book> books, String unique_number){
        Book bookInfo = null;
        for(Book book:books){
            if(book.getUniqueNumber().equals(unique_number)){
                bookInfo=book;
            }
        }
        if(bookInfo!=null){
            System.out.println("Tittle: " + bookInfo.getTitle());
            System.out.println("Author: " + bookInfo.getAuthor());
            System.out.println("Genre: "+ bookInfo.getGenre());
            System.out.println("Resume: " + bookInfo.getResume());
            System.out.println("The release year is " + bookInfo.getRealiseYear());
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < bookInfo.getKeyWords().size(); i++) {
                output.append(bookInfo.getKeyWords().get(i));
                if (i < bookInfo.getKeyWords().size() - 1) { // Add a comma if it's not the last element
                    output.append(", ");
                }
            }
            System.out.println("Key words: " + output);
            System.out.println("Rating: " + bookInfo.getRating());
            System.out.println("Unique number: " + bookInfo.getUniqueNumber());
        }
        else{
            System.out.println("Book with this unique number is not found in our library.");
        }
    }
    public void bookView(ArrayList<Book> books){
        if(!books.isEmpty()) {
            for (Book book : books) {
                System.out.println("Title: " + book.getTitle());
            }
        }
        else{
            System.out.println("There is no books in the library.");
        }
    }
    public User logout(NonUserClass user){
        User current_user;
        current_user = (NonUserClass) user;
        return current_user;
    }


}
