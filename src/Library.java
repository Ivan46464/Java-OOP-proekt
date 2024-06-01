import Exceptions.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Library {


    private HashSet<Book> books;
    private HashSet<User> users;
    private Scanner sc;
    private Auntie lelichka = new Auntie();;
    public Library() throws Exception {
        books = new HashSet<>();
        sc = new Scanner(System.in);
        users = new HashSet<>();
        User adminUser = new AdminUserClass("admin", "i<3c++");
        User nonUser = new NonUserClass("non_user", "12345");
        users.add(adminUser);
        users.add(nonUser);
    }

    public HashSet<User> getUsers() {
        return users;
    }

    public void setUsers(HashSet<User> users) {
        this.users = users;
    }
    public HashSet<Book> getBooks() {
        return books;
    }
    public void setBooks(HashSet<Book> books) {
        this.books = books;
    }
    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }
    public void bookAdd() throws Exception {
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
                    break;
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
            boolean uniqueNumberExists;
            do {
                System.out.println("Write unique number: ");
                uniqueNumber = getSc().nextLine();
                final String uniqueNumberCheck = uniqueNumber;
                uniqueNumberExists = books.stream().anyMatch(book -> book.getUniqueNumber().equals(uniqueNumberCheck));

                if (uniqueNumberExists) {
                    System.out.println("Error: Unique number already exists. Please enter a different one.");
                }
            } while (uniqueNumberExists);

            Book book = new Book(title, author, genre, resume, rel, key_words, rat, uniqueNumber);
            books.add(book);
        } catch (InvalidTitleException | InvalidAuthorException | InvalidGenreException | InvalidResumeException | InvalidReleaseYearException | InvalidKeyWordsException | InvalidRatingException | InvalidUniqueNumberException e) {
            System.out.println(e.getMessage());
        }
    }
    public  void removeBookByUniqueNumber(String uniqueNumber) {
        for (Book book : books) {
            if (book.getUniqueNumber().equalsIgnoreCase(uniqueNumber)) {
                books.remove(book);
                System.out.println("Book with unique number " + uniqueNumber + " has been removed.");
                return;
            }
        }

        System.out.println("Book with unique number " + uniqueNumber + " is not found.");
    }
    public void addUser(String username, String password) throws Exception{
        try {
            User newUser = new NormalUserClass(username, password); // Assuming NormalUserClass is a subclass of User
            if (users.contains(newUser)) {
                System.out.println("User with this username already exists.");
            } else {
                users.add(newUser);
                System.out.println("Successfully added a user.");
            }
        } catch (InvalidUsernameException | InvalidPasswordException e) {
            System.out.println(e.getMessage());
        }
    }
    public  void removeUser(String username) {
        for (User normal_user : users) {
            if (normal_user.getUsername().equals(username)) {
                users.remove(normal_user);
                System.out.println("User with username " + normal_user.getUsername() + " has been removed.");
                return;
            }
        }
        System.out.println("User with id number " + username + " is not found.");
    }
    public void booksAll(){
        lelichka.bookAll(books);
    }
    public void booksView(){
        lelichka.bookView(books);
    }
    public void booksSort(String tag, String order){
        List<Book> bookList = new ArrayList<>(books);
        lelichka.bookSort(bookList, tag, order);
    }
    public void booksFind(String tag, String word){
        lelichka.bookFind(books, tag, word);
    }
    public void booksInfo(String uniqueNumber){
        lelichka.bookInfo(books,uniqueNumber);
    }
    public User login() throws Exception {
        User current_user = null;
        System.out.println("Write your username: ");
        String Username = getSc().nextLine();
        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available.");
        }

        System.out.println("Write your password(* start): ");
        String password = PasswordField.readPassword("");
//        char[] passwordChars = console.readPassword();
//        String password = new String(passwordChars);
//
//
//        for (int i = 0; i < passwordChars.length; i++) {
//            passwordChars[i] = '\0';
//        }
        for (User user : users) {
            if (Username.equals(user.getUsername()) & password.equals(user.getPassword())) {
                current_user =  user;
                System.out.println("\nWelcome " + current_user.getUsername());
                return current_user;

            }

        }
        if(current_user == null) {
            System.out.println("There is no user with such password or username.");
            current_user = getUsers().stream()
                    .filter(user -> user instanceof NonUserClass)
                    .findFirst()
                    .orElseThrow(() -> new Exception("NonUser not found in the library."));

        }
        return current_user;
    }

}
