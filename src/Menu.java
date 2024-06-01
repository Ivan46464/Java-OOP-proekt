import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

public class Menu {
    private Map<String, Consumer<String[]>> commandHandlers;
    private  Map<String, Consumer<String[]>> commandHandlerBook;
    private  Map<String, Consumer<String[]>> commandHandlerUser;
    private User currentUser;
    private String fileName;
    private Library library;
    private boolean fileOpened = false;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Menu() throws Exception {
        commands();
        library = new Library();
        currentUser =  library.getUsers().stream()
                .filter(user -> user instanceof NonUserClass)
                .findFirst()
                .orElseThrow(() -> new Exception("NonUser not found in the library."));
        fileName = null;


    }
    public void commands(){
        commandHandlers = new HashMap<>();
        commandHandlerBook = new HashMap<>();
        commandHandlerUser = new HashMap<>();
        commandHandlers.put("open", this::openFile);
        commandHandlers.put("save",s -> saveFile());
        commandHandlers.put("saveas", this::saveFileAs);
        commandHandlers.put("close", s -> {
            try{
                closeFile();
            } catch (Exception e)
            {System.out.println(e.getMessage());}});
        commandHandlers.put("help", s -> displayHelp());
        commandHandlers.put("login", words->{
            try {
                login(words);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }});
        commandHandlers.put("logout", s -> {
            try {
                logout();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        commandHandlers.put("exit", s -> {});
        commandHandlers.put("books", words -> {
            if (fileOpened) {
                try {
                    handleCommandBook(words);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }} else {
                System.out.println("Open a file first.");
            }});
        commandHandlers.put("users", words -> {
            if (fileOpened) {
                if (currentUser instanceof AdminUserClass) {
                    try {
                        handleCommandUser(words);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }} else {
                    System.out.println("You must be logged in as adminUser to access user commands.");
                }} else {
                System.out.println("Open a file first.");
            }
        });
        commandHandlerBook.put("add", s-> {
            try {
                bookAdd();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
        commandHandlerBook.put("all", s-> bookAll());
        commandHandlerBook.put("view", s->bookView());
        commandHandlerBook.put("info", this::bookInfo);
        commandHandlerBook.put("remove", words->{
            try{
                bookRemove(words);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
        commandHandlerBook.put("find", this::bookFind);
        commandHandlerBook.put("sort", this::bookSort);
        commandHandlerUser.put("add", words-> {
            try {
                userAdd(words);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        commandHandlerUser.put("remove",words-> userRemove(words));
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
    private void handleCommandBook(String[] words) {
        Consumer<String[]> commandHandler = commandHandlerBook.getOrDefault(words[1], this::unknownCommand);
        commandHandler.accept(words);
    }
    private void handleCommandUser(String[] words) {
        Consumer<String[]> commandHandler = commandHandlerUser.getOrDefault(words[1], this::unknownCommand);
        commandHandler.accept(words);
    }
    private void unknownCommand(String[] words) {
        System.out.println("Unknown command type help.");
    }
    private void openFile(String[] words) {
        if (!this.fileOpened && words.length == 2) {
            fileName = words[1];
            FileHandler.readFromFile(fileName, library.getBooks(), library.getUsers());
            this.fileOpened = true;
        } else if (this.fileOpened) {
            System.out.println("A file is already opened.");
        } else {
            System.out.println("Type help if you need to see the commands.");
        }
    }
    private void saveFile() {
        if (fileOpened) {
            FileHandler.writeToFile(fileName, library.getBooks(), library.getUsers());
        } else {
            System.out.println("No file is currently opened.");
        }
    }
    private void saveFileAs(String[] words) {
        if (fileOpened) {
            if (words.length > 1) {
                String dir = words[1];
                FileHandler.writeToFile_dir(dir, fileName, library.getBooks(), library.getUsers());
                System.out.println("Successfully saved " + fileName + " in " + dir);
            } else {
                System.out.println("Specify the directory where you want to save the file.");
            }
        } else {
            System.out.println("No file is currently opened.");
        }
    }
    private void closeFile() throws Exception {
        if (fileOpened) {
            FileHandler.closeFile(fileName);
            fileOpened = false;
            fileName = null;
            library.getBooks().clear();
            library.getUsers().clear();
            library.getUsers().add(new AdminUserClass("admin", "i<3c++"));
            library.getUsers().add(new NonUserClass("non_user", "12345"));
            logoutForClosedFile();
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
    private void login(String[] words) throws Exception {
        if (words.length < 2) {
            if (currentUser instanceof NonUserClass) {
                currentUser = library.login();
            } else {
                System.out.println("You have already logged in.");
            }
        }
    }
    private void logoutForClosedFile() throws Exception {
        currentUser = library.getUsers().stream()
                .filter(user -> user instanceof NonUserClass)
                .findFirst()
                .orElseThrow(() -> new Exception("NonUser not found in the library."));
    }
    private void logout() throws Exception {
        currentUser = library.getUsers().stream()
                .filter(user -> user instanceof NonUserClass)
                .findFirst()
                .orElseThrow(() -> new Exception("NonUser not found in the library."));
        System.out.println("You have successfully logged out.");
    }
    private void bookAll(){
        if(!(currentUser instanceof NonUserClass)) {
            library.booksAll();
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
            if(!(currentUser instanceof NonUserClass)) {
                library.booksFind(tag, String.valueOf(option));
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
                if(!(currentUser instanceof NonUserClass)) {
                    library.booksSort(sortOption,sortOrder);
                }
                else{
                    System.out.println("You should be logged in to use this command.");
                }
            }else {
                if(!(currentUser instanceof NonUserClass)) {
                    library.booksSort(sortOption,sortOrder);
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
            if(!(currentUser instanceof NonUserClass)) {
               library.booksInfo(isbnValue);
            }
            else{
                System.out.println("You should be logged in to use this command.");
            }
        } else {
            System.out.println("Your syntax os wrong write help to see how.");
        }
    }
    private void bookView(){
        if(!(currentUser instanceof NonUserClass)) {
            library.booksView();
        }
        else{
            System.out.println("You should be logged in to use this command.");
        }
    }
    private void bookAdd() throws Exception {
        if(currentUser instanceof AdminUserClass) {
            library.bookAdd();
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
                library.removeBookByUniqueNumber(unique_number);
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
                library.addUser(username,password);
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
                library.removeUser(username);
            }
            else{
                System.out.println("You should be admin to add users.");
            }
        } else {
            System.out.println("Your syntax os wrong write help to see how.");
        }
    }

}



