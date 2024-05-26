import java.util.ArrayList;
import java.util.HashSet;

import Exceptions.*;

public class AdminUserClass extends User{

    public AdminUserClass(String username, String password)throws Exception {
        super(username, password);
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
            do {
                System.out.println("Write unique number: ");
                uniqueNumber = getSc().nextLine();


                if (uniqueNumbers.contains(uniqueNumber)) {
                    System.out.println("Error: Unique number already exists. Please enter a different one.");
                }

            } while (uniqueNumbers.contains(uniqueNumber));

            Book book = new Book(title, author, genre, resume, rel, key_words, rat, uniqueNumber);
            uniqueNumbers.add(uniqueNumber);
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
    public  void removeBookByUniqueNumber(ArrayList<Book> books, String uniqueNumber, HashSet<String> uniqueNumbers) {
        for (Book book : books) {
            if (book.getUniqueNumber().equalsIgnoreCase(uniqueNumber)) {
                books.remove(book);
                System.out.println("Book with unique number " + uniqueNumber + " has been removed.");
                uniqueNumbers.remove(uniqueNumber);
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
            NormalUserClass normal_user = new NormalUserClass(username, password);
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
