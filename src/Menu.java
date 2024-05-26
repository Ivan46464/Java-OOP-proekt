import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

public class Menu {
    private final Map<String, Consumer<String[]>> commandHandlers;
    private ArrayList<Book> books;
    private ArrayList<NormalUserClass> normalUsers;
    private AdminUserClass adminUser;
    private NonUserClass nonUser;
    private User currentUser;
    private String fileName;
    private HashSet<String> uniqueNumbers;
    private HashSet<String> uniqueUsernames;
    private boolean fileOpened = false;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Menu() throws Exception {
        commandHandlers = new HashMap<>();
        commandHandlers.put("open", this::openFile);
        commandHandlers.put("save",s -> saveFile());
        commandHandlers.put("saveas", this::saveFileAs);
        commandHandlers.put("close", s -> closeFile());
        commandHandlers.put("help", s -> displayHelp());
        commandHandlers.put("login", this::login);
        commandHandlers.put("logout", s -> logout());
        commandHandlers.put("exit", s -> {});
        commandHandlers.put("books", words -> {
            if (fileOpened) {
                try {
                    handleBooksCommand(words);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }} else {
                System.out.println("Open a file first.");
            }});
        commandHandlers.put("users", words -> {
            if (fileOpened) {
                if (currentUser instanceof AdminUserClass) {
                    try {
                        handleUsersCommand(words);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }} else {
                    System.out.println("You must be logged in as adminUser to access user commands.");
                }} else {
                System.out.println("Open a file first.");
            }
        });

        books = new ArrayList<>();
        normalUsers = new ArrayList<>();
        adminUser = new AdminUserClass("admin", "i<3c++");
        nonUser = new NonUserClass("non_user", "12345");
        currentUser = adminUser;
        fileName = null;
        uniqueNumbers = new HashSet<>();
        uniqueUsernames = new HashSet<>();
    }

    public void startCommandPrompt() {
        try {
            System.out.println("Opened a new command prompt.");
            String userInput;

            do {
                System.out.print(">> ");
                userInput = reader.readLine().trim();
                String[] words = userInput.split("\\s+");
                handleCommand(words);
            } while (!userInput.equals("exit"));

            System.out.println("Command prompt closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleCommand(String[] words) {
        Consumer<String[]> commandHandler = commandHandlers.getOrDefault(words[0], this::unknownCommand);
        commandHandler.accept(words);
    }
    private void unknownCommand(String[] words) {
        System.out.println("Unknown command type help.");
    }

    private void openFile(String[] words) {
        if (!this.fileOpened && words.length == 2) {
            fileName = words[1];
            FileHandler.readFromFile(fileName, books, normalUsers,uniqueNumbers,uniqueUsernames);
            this.fileOpened = true;
        } else if (this.fileOpened) {
            System.out.println("A file is already opened.");
        } else {
            System.out.println("Type help if you need to see the commands.");
        }
    }

    private void saveFile() {
        if (fileOpened) {
            FileHandler.writeToFile(fileName, books, normalUsers,uniqueNumbers,uniqueUsernames);
        } else {
            System.out.println("No file is currently opened.");
        }
    }

    private void saveFileAs(String[] words) {
        if (fileOpened) {
            if (words.length > 1) {
                String dir = words[1];
                FileHandler.writeToFile_dir(dir, fileName, books, normalUsers,uniqueNumbers,uniqueUsernames);
                System.out.println("Successfully saved " + fileName + " in " + dir);
            } else {
                System.out.println("Specify the directory where you want to save the file.");
            }
        } else {
            System.out.println("No file is currently opened.");
        }
    }

    private void closeFile() {
        if (fileOpened) {
            FileHandler.closeFile(fileName);
            fileOpened = false;
            fileName = null;
            books.clear();
            normalUsers.clear();
            uniqueNumbers.clear();
            uniqueUsernames.clear();
        } else {
            System.out.println("No file is currently opened.");
        }
    }

    private void displayHelp() {
        System.out.println("login You login in your account.");
        System.out.println("logout You logout of your account.");
        System.out.println("open <FileName> You open a file and its content.");
        System.out.println("save Saves the changes in the file you are in.");
        System.out.println("saveas Saves the file in the directory you want.");
        System.out.println("help Shows you all the commands and what they do.");
        System.out.println("books all Shows title, author, genre and unique number of all books.");
        System.out.println("books info <unique number> Shows the information for the book with the specific isbn(unique number).");
        System.out.println("books find <option> <option_string> Finds the book with the <option>, which is one of title, author, tag, <option_string> is the search criteria value, may contain spaces.");
        System.out.println("books sort <option> [asc | desc] <option> is one of title, author, year, rating asc means ascending sort (default) and des means descending sort.");
        System.out.println("books view Shows all the books");
        System.out.println("books add You add a book.");
        System.out.println("books remove You remove a book.");
        System.out.println("users add <user> <password> Adds a new user with username <user> and password <password>. The user and his password are saved to a file.");
        System.out.println("users remove <user_id> Deletes the user with username id <user_id> from the file.");
    }

    private void login(String[] words) {
        if (words.length < 2) {
            if (currentUser instanceof NonUserClass) {
                currentUser = ((NonUserClass) currentUser).login(normalUsers, adminUser, nonUser);
            } else {
                System.out.println("You have already logged in.");
            }
        }
    }

    private void logout() {
        if (currentUser instanceof AdminUserClass) {
            currentUser = ((AdminUserClass) currentUser).logout(nonUser);
        } else if (currentUser instanceof NormalUserClass) {
            currentUser = ((NormalUserClass) currentUser).logout(nonUser);
        }
    }

    private void handleBooksCommand(String[] words) throws Exception {
        if (words.length > 1) {
            switch (words[1]) {
                case "all":
                    bookAll();
                    break;
                case "find":
                    bookFind(words);
                    break;
                case "sort":
                    bookSort(words);
                    break;
                case "info":
                    bookInfo(words);
                    break;
                case "view":
                    bookView();
                    break;
                case "add":
                    bookAdd();
                    break;
                case "remove":
                    bookRemove(words);
                    break;
                default:
                    System.out.println("Unknown command type help.");
                    break;
            }
        } else {
            System.out.println("Unknown command type help.");
        }
    }
    private void handleUsersCommand(String[] words) throws Exception {
        if (words.length > 1)
        {
            switch (words[1]) {
                case "add":
                    userAdd(words);
                    break;
                case "remove":
                    userRemove(words);
                    break;
                default:
                    System.out.println("Unknown command type help.");
                    break;
            }
        }
        else {
            System.out.println("Incomplete command.");
        }
    }

    private void bookAll(){
        if(currentUser instanceof AdminUserClass) {
            (currentUser).bookAll(books);
        }
        else if(currentUser instanceof NormalUserClass){
            (currentUser).bookAll(books);
        }
        else{
            System.out.println("You should be logged in to use this command.");
        }
    }
    private void bookFind(String[] words){
        if (words.length > 3) {
            String tag = words[2];
            StringBuilder option = new StringBuilder();
            for (int i = 3; i < words.length; i++) {
                option.append(words[i]);
                if (i < words.length - 1) {
                    option.append(" ");
                }
            }
            if(currentUser instanceof AdminUserClass) {
                currentUser.bookFind(books, tag, option.toString());
            }
            else if(currentUser instanceof NormalUserClass){
                currentUser.bookFind(books, tag, option.toString());
            }
            else{
                System.out.println("You should be logged in to use this command.");
            }
        } else {
            System.out.println("Your syntax is wrong. Write 'help' to see how.");
        }
    }
    private void bookSort(String[] words){
        if (words.length > 2) {
            String sortOption = words[2];
            String sortOrder = "asc"; // Default to ascending order
            if (words.length > 3) {
                sortOrder = words[3];
                if(currentUser instanceof AdminUserClass) {
                    (currentUser).bookSort(books, sortOption, sortOrder);
                }
                else if(currentUser instanceof NormalUserClass){
                    (currentUser).bookSort(books, sortOption, sortOrder);
                }
                else{
                    System.out.println("You should be logged in to use this command.");
                }
            }else {
                if (currentUser instanceof AdminUserClass) {
                    ( currentUser).bookSort(books, sortOption, sortOrder);
                } else if (currentUser instanceof NormalUserClass) {
                    (currentUser).bookSort(books, sortOption, sortOrder);
                } else {
                    System.out.println("You should be logged in to use this command.");
                }
            }
        } else {
            System.out.println("Your syntax os wrong write help to see how.");
        }
    }
    private void bookInfo(String[] words){
        if (words.length > 2) {
            String isbnValue = words[2];
            if(currentUser instanceof AdminUserClass) {
                (currentUser).bookInfo(books,isbnValue);
            }
            else if(currentUser instanceof NormalUserClass){
                (currentUser).bookInfo(books,isbnValue);
            }
            else{
                System.out.println("You should be logged in to use this command.");
            }
        } else {
            System.out.println("Your syntax os wrong write help to see how.");
        }
    }
    private void bookView(){
        if(currentUser instanceof AdminUserClass) {
            (currentUser).bookView(books);
        }
        else if(currentUser instanceof NormalUserClass){
            (currentUser).bookView(books);
        }
        else{
            System.out.println("You should be logged in to use this command.");
        }
    }
    private void bookAdd() throws Exception {
        if(currentUser instanceof AdminUserClass) {
            ((AdminUserClass) currentUser).bookAdd(books,uniqueNumbers);
        }
        else{
            System.out.println("You should be admin to use this command.");
        }
    }
    private void bookRemove(String[] words ) throws Exception {
        if(words.length > 2){
            System.out.println("Wrong syntax.");
        }
        else {
            if (currentUser instanceof AdminUserClass) {
                System.out.println("Write the unique number of the book: ");
                String unique_number = reader.readLine();
                ((AdminUserClass) currentUser).removeBookByUniqueNumber(books, unique_number, uniqueNumbers);
            } else {
                System.out.println("You should be admin to use this command.");
            }
        }
    }
    private void userAdd(String[] words) throws Exception{
        if (words.length > 3) {
            String username = words[2];
            String password = words[3];
            if(currentUser instanceof AdminUserClass) {
                ((AdminUserClass)currentUser).addUser(normalUsers, username, password,uniqueUsernames);
            }
            else{
                System.out.println("You should be admin to add users.");
            }
        } else {
            System.out.println("Your syntax os wrong write help to see how.");
        }
    }
    private void userRemove(String[] words){
        if (words.length > 2) {
            String username = words[2];
            if(currentUser instanceof AdminUserClass) {
                ((AdminUserClass)currentUser).removeUser(normalUsers, username,uniqueUsernames);
            }
            else{
                System.out.println("You should be admin to add users.");
            }
        } else {
            System.out.println("Your syntax os wrong write help to see how.");
        }
    }

}



