import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import Exceptions.*;

public class AdminUserClass extends User{

    public AdminUserClass(String username, String password, Boolean availability)throws Exception {
        super(username, password, availability);
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

    public void bookAdd(ArrayList<Book> books, HashSet<String> uniqueNumbers) throws Exception {
        try {
            System.out.println("Write title: ");
            String title = getSc().nextLine();

            System.out.println("Write author: ");
            String author = getSc().nextLine();

            System.out.println("Write genre: ");
            String genre = getSc().nextLine();

            System.out.println("Write resume: ");
            String resume = getSc().nextLine();

            int rel;
            while (true) {
                System.out.println("Write release year: ");
                String release_year = getSc().nextLine();
                try {
                    rel = Integer.parseInt(release_year);
                    break; // Exit the loop if parsing succeeds
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }


            System.out.println("Write key words (separate them by comma): ");
            String[] keywords = getSc().nextLine().split(",");
            ArrayList<String> key_words = new ArrayList<>();
            for (String keyword : keywords) {
                key_words.add(keyword.trim());
            }
            double rat;
            while (true) {
                System.out.println("Write rating: ");
                String rating = getSc().nextLine();
                try {
                    rat = Double.parseDouble(rating);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid double.");
                }
            }

            String uniqueNumber;
            do {
                System.out.println("Write unique number: ");
                uniqueNumber = getSc().nextLine();


                if (uniqueNumbers.contains(uniqueNumber)) {
                    System.out.println("Error: Unique number already exists. Please enter a different one.");
                }

            } while (uniqueNumbers.contains(uniqueNumber));
            uniqueNumbers.add(uniqueNumber);


            Book book = new Book(title, author, genre, resume, rel, key_words, rat, uniqueNumber);
            books.add(book);

        } catch (InvalidTitleException e) {
            System.out.println(e.getMessage());
        } catch (InvalidAuthorException e) {
            System.out.println(e.getMessage());
        } catch (InvalidGenreException e) {
            System.out.println(e.getMessage());
        } catch (InvalidResumeException e) {
            System.out.println(e.getMessage());
        } catch (InvalidReleaseYearException e) {
            System.out.println(e.getMessage());
        } catch (InvalidKeyWordsException e) {
            System.out.println(e.getMessage());
        } catch (InvalidRatingException e) {
            System.out.println(e.getMessage());
        } catch (InvalidUniqueNumberException e) {
            System.out.println(e.getMessage());
        }
    }

    public  void removeBookByUniqueNumber(ArrayList<Book> books, String uniqueNumber) {
        for (Book book : books) {
            if (book.getUniqueNumber().equalsIgnoreCase(uniqueNumber)) {
                books.remove(book);
                System.out.println("Book with unique number " + uniqueNumber + " has been removed.");
                return;
            }
        }
        System.out.println("Book with unique number " + uniqueNumber + " is not found.");
    }
    public void addUser(ArrayList<NormalUserClass> normal_users, String username, String password, HashSet<String> uniqueUsername) throws Exception{
        try {
            if (uniqueUsername.contains(username)){
                System.out.println("User with this username already exists.");
                return;
            }
            NormalUserClass normal_user = new NormalUserClass(username, password, false);
            normal_users.add(normal_user);
            System.out.println("Successfully added a user.");
            uniqueUsername.add(username);
        }catch (InvalidUsernameException e){
            System.out.println(e.getMessage());
        }catch (InvalidPasswordException e){
            System.out.println(e.getMessage());
        }
    }
    public  void removeUser(ArrayList<NormalUserClass> normal_users,String username,HashSet<String> users) {
        for (NormalUserClass normal_user : normal_users) {
            if (normal_user.getUsername().equals(username)) {
                normal_users.remove(normal_user);
                for(String user: users){
                    if(username.equals(user)){
                        users.remove(user);
                        break;
                    }
                }
                System.out.println("User with username " + normal_user.getUsername() + " has been removed.");
                return;
            }
        }
        System.out.println("User with id number " + username + " is not found.");
    }

}
