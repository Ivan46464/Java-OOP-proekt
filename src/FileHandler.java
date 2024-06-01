import java.io.*;
import java.util.HashSet;

public class FileHandler {

    private static final User adminUser;

    static {
        try {
            adminUser = new AdminUserClass("admin", "i<3c++");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final User nonUser;

    static {
        try {
            nonUser = new NonUserClass("non_user", "12345");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToFile(String fileName, HashSet<Book> books, HashSet<User> users) {
        users.add(adminUser);
        users.add(nonUser);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(books);
            oos.writeObject(users);
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void readFromFile(String fileName, HashSet<Book> books, HashSet<User> users) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            HashSet<Book> readBooks = (HashSet<Book>) ois.readObject();
            HashSet<User> readUsers = (HashSet<User>) ois.readObject();
            books.addAll(readBooks);
            users.addAll(readUsers);
            users.add(adminUser);
            users.add(nonUser);
            System.out.println("Data read from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            System.out.println("Creating a new file...");
            writeToFile(fileName, new HashSet<>(), new HashSet<>());
        }
    }

    public static void writeToFile_dir(String directory, String fileName, HashSet<Book> books, HashSet<User> users) {
        File file = new File(directory, fileName);
        users.add(adminUser);
        users.add(nonUser);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(books);
            oos.writeObject(users);
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void closeFile(String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
            System.out.println("File closed successfully.");
        } catch (IOException e) {
            System.out.println("Error closing file: " + e.getMessage());
        }
    }
}
