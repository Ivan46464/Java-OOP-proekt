import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
/*        try {
            ArrayList<Book> books = new ArrayList<>();
            ArrayList<NormalUserClass> NormalUsers = new ArrayList<>();
            AdminUserClass AdminUser = new AdminUserClass("admin", "i<3c++", true);
            NonUserClass NonUser = new NonUserClass("non_user","12345",false);
            User CurentUser;
            CurentUser = AdminUser;
            String FileName = null;
            HashSet<String> uniqueNumbers = new HashSet<>();
            HashSet<String> uniqueUsername = new HashSet<>();

            System.out.println("Opened a new command prompt.");
            boolean FileOpened = false;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String UserInput;

            do {

                System.out.print(">> ");
                UserInput = reader.readLine();
                UserInput = UserInput.trim();
                String[] words = UserInput.split("\\s+");
                switch (words[0]) {
                    case "open":
                        if (!FileOpened && words.length == 2) {
                            FileName = words[1];
                            FileHandler.readFromFile(FileName, books, NormalUsers);
                            FileOpened = true;
                        } else if (FileOpened) {
                            System.out.println("A file is already opened.");
                        } else {
                            System.out.println("Type help if you need to see the commands.");
                        }
                        break;
                    case "save":
                        if (FileOpened) {
                            FileHandler.writeToFile(FileName, books, NormalUsers);
                        } else {
                            System.out.println("No file is currently opened.");
                        }
                        break;
                    case "saveas":
                        if (FileOpened) {
                            if (words.length > 1) {
                                String dir = words[1];
                                FileHandler.writeToFile_dir(dir, FileName, books, NormalUsers);
                                System.out.println("Successfully saved " + FileName + " in " + dir);
                            } else {
                                System.out.println("Specify the directory where you want to save the file.");
                            }
                        } else {
                            System.out.println("No file is currently opened.");
                        }
                        break;
                    case "close":
                        if (FileOpened) {
                            FileHandler.closeFile(FileName);
                            FileOpened

                                    = false;
                            FileName = null;
                            books.clear();
                            NormalUsers.clear();
                        } else {
                            System.out.println("No file is currently opened.");
                        }
                        break;
                    case "help":
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
                        break;
                    case "login":
                        if(words.length<2) {
                            if (CurentUser instanceof NonUserClass) {
                                CurentUser = ((NonUserClass) CurentUser).login(NormalUsers, AdminUser, NonUser);
                            } else {
                                System.out.println("You have already logged in.");
                            }
                        }
                        break;
                    case "logout":
                        if(CurentUser instanceof AdminUserClass){
                            CurentUser = ((AdminUserClass) CurentUser).logout(NonUser);
                        }
                        else if (CurentUser instanceof NormalUserClass){
                            CurentUser = ((NormalUserClass) CurentUser).logout(NonUser);
                        }
                        break;
                    case "exit":
                        // Handle exit command
                        break;
                    case "books":
                        if (words.length > 1) {
                            switch (words[1]) {
                                case "all":
                                    if(CurentUser instanceof AdminUserClass) {
                                        ((AdminUserClass)CurentUser).bookAll(books);
                                    }
                                    else if(CurentUser instanceof NormalUserClass){
                                        ((NormalUserClass)CurentUser).bookAll(books);
                                    }
                                    else{
                                        System.out.println("You should be logged in to use this command.");
                                    }
                                    break;
                                case "find":
                                    if (words.length > 3) {
                                        String tag = words[2];
                                        StringBuilder option = new StringBuilder();
                                        for (int i = 3; i < words.length; i++) {
                                            option.append(words[i]);
                                            if (i < words.length - 1) {
                                                option.append(" ");
                                            }
                                        }
                                        if(CurentUser instanceof AdminUserClass) {
                                            ((AdminUserClass)CurentUser).bookFind(books, tag, option.toString());
                                        }
                                        else if(CurentUser instanceof NormalUserClass){
                                            ((NormalUserClass)CurentUser).bookFind(books, tag, option.toString());
                                        }
                                        else{
                                            System.out.println("You should be logged in to use this command.");
                                        }
                                    } else {
                                        System.out.println("Your syntax is wrong. Write 'help' to see how.");
                                    }
                                    break;
                                case "sort":
                                    if (words.length > 2) {
                                        String sortOption = words[2];
                                        String sortOrder = "asc"; // Default to ascending order
                                        if (words.length > 3) {
                                            sortOrder = words[3];
                                            if(CurentUser instanceof AdminUserClass) {
                                                ((AdminUserClass)CurentUser).bookSort(books, sortOption, sortOrder);
                                            }
                                            else if(CurentUser instanceof NormalUserClass){
                                                ((NormalUserClass)CurentUser).bookSort(books, sortOption, sortOrder);
                                            }
                                            else{
                                                System.out.println("You should be logged in to use this command.");
                                            }
                                            break;
                                        }
                                        if(CurentUser instanceof AdminUserClass) {
                                            ((AdminUserClass)CurentUser).bookSort(books, sortOption, sortOrder);
                                        }
                                        else if(CurentUser instanceof NormalUserClass){
                                            ((NormalUserClass)CurentUser).bookSort(books, sortOption, sortOrder);
                                        }
                                        else{
                                            System.out.println("You should be logged in to use this command.");
                                        }
                                    } else {
                                        System.out.println("Your syntax os wrong write help to see how.");
                                    }
                                    break;
                                case "info":
                                    if (words.length > 2) {
                                        String isbnValue = words[2];
                                        if(CurentUser instanceof AdminUserClass) {
                                            ((AdminUserClass)CurentUser).bookInfo(books,isbnValue);
                                        }
                                        else if(CurentUser instanceof NormalUserClass){
                                            ((NormalUserClass)CurentUser).bookInfo(books,isbnValue);
                                        }
                                        else{
                                            System.out.println("You should be logged in to use this command.");
                                        }
                                    } else {
                                        System.out.println("Your syntax os wrong write help to see how.");
                                    }
                                    break;
                                case "view":
                                    if(CurentUser instanceof AdminUserClass) {
                                        ((AdminUserClass)CurentUser).bookView(books);
                                    }
                                    else if(CurentUser instanceof NormalUserClass){
                                        ((NormalUserClass)CurentUser).bookView(books);
                                    }
                                    else{
                                        System.out.println("You should be logged in to use this command.");
                                    }
                                    break;
                                case "add":
                                    if(CurentUser instanceof AdminUserClass) {
                                        ((AdminUserClass)CurentUser).bookAdd(books,uniqueNumbers);
                                    }
                                    else{
                                        System.out.println("You should be admin to use this command.");
                                    }
                                    break;
                                case "remove":
                                    if(words.length > 2){
                                        System.out.println("Wrong syntax.");

                                    }
                                    else {
                                        System.out.println("Write the unique number of the book: ");
                                        String unique_number = reader.readLine();
                                        if (CurentUser instanceof AdminUserClass) {
                                            ((AdminUserClass) CurentUser).removeBookByUniqueNumber(books, unique_number);
                                        } else {
                                            System.out.println("You should be admin to use this command.");
                                        }
                                    }
                                    break;
                                default:
                                    System.out.println("Unknown command type help.");
                                    break;
                            }
                        } else {
                            System.out.println("Unknown command type help.");
                        }
                        break;
                    case "users":

                        if (words.length > 1) {
                            switch (words[1]) {
                                case "add":
                                    if (words.length > 3) {
                                        String username = words[2];
                                        String password = words[3];
                                        if(CurentUser instanceof AdminUserClass) {

                                            ((AdminUserClass)CurentUser).addUser(NormalUsers, username, password,uniqueUsername);
                                        }
                                        else{
                                            System.out.println("You should be admin to add users.");
                                        }
                                    } else {
                                        System.out.println("Your syntax os wrong write help to see how.");
                                    }
                                    break;
                                case "remove":
                                    if (words.length > 2) {
                                        String username = words[2];
                                        if(CurentUser instanceof AdminUserClass) {
                                            ((AdminUserClass)CurentUser).removeUser(NormalUsers,username);
                                        }
                                        else{
                                            System.out.println("You should be admin to add users.");
                                        }
                                    } else {
                                        System.out.println("Your syntax os wrong write help to see how.");
                                    }
                                    break;
                                default:
                                    System.out.println("Unknown command type help.");
                                    break;
                            }
                        } else {
                            System.out.println("Incomplete command.");
                        }
                        break;
                    default:
                        System.out.println("Unknown command type help.");
                        break;
                }

            } while (!UserInput.equals("exit"));



            System.out.println("Command prompt closed.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
*/
        Menu operations = new Menu();
        operations.startCommandPrompt();

    }
}