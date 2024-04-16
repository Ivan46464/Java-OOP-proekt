import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Operations {
        private ArrayList<Book> books;
        private ArrayList<NormalUserClass> normalUsers;
        private AdminUserClass adminUser;
        private NonUserClass nonUser;
        private User currentUser;
        private String fileName;
        private HashSet<String> uniqueNumbers;
        private HashSet<String> uniqueUsernames;

        public Operations() throws Exception {
            books = new ArrayList<>();
            normalUsers = new ArrayList<>();
            adminUser = new AdminUserClass("admin", "i<3c++", true);
            nonUser = new NonUserClass("non_user", "12345", false);
            currentUser = adminUser;
            fileName = null;
            uniqueNumbers = new HashSet<>();
            uniqueUsernames = new HashSet<>();
        }

        public void startCommandPrompt() {
            try {
                System.out.println("Opened a new command prompt.");
                boolean fileOpened = false;
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String userInput;

                do {
                    System.out.print(">> ");
                    userInput = reader.readLine().trim();
                    String[] words = userInput.split("\\s+");
                    handleCommand(words);
                } while (!userInput.equals("exit"));

                System.out.println("Command prompt closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleCommand(String[] words) {
            switch (words[0]) {
                case "open":
                    openFile(words);
                    break;
                case "save":
                    saveFile();
                    break;
                case "saveas":
                    saveFileAs(words);
                    break;
                case "close":
                    closeFile();
                    break;
                case "help":
                    displayHelp();
                    break;
                case "login":
                    login();
                    break;
                case "logout":
                    logout();
                    break;
                case "exit":
                    // Handle exit command
                    break;
                case "books":
                    handleBooksCommand(words);
                    break;
                case "users":
                    handleUsersCommand(words);
                    break;
                default:
                    System.out.println("Unknown command type help.");
                    break;
            }
        }

        private void openFile(String[] words) {
            // Implement openFile functionality
        }

        private void saveFile() {
            // Implement saveFile functionality
        }

        private void saveFileAs(String[] words) {
            // Implement saveFileAs functionality
        }

        private void closeFile() {
            // Implement closeFile functionality
        }

        private void displayHelp() {
            // Implement displayHelp functionality
        }

        private void login() {
            // Implement login functionality
        }

        private void logout() {
            // Implement logout functionality
        }

        private void handleBooksCommand(String[] words) {
            // Implement handling of books commands
        }

        private void handleUsersCommand(String[] words) {
            // Implement handling of users commands
        }


    }



