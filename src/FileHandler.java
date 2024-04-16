import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class FileHandler {

    public static void writeToFile(String fileName, ArrayList<Book> books, ArrayList<NormalUserClass> users, HashSet<String> uniqueNumbers, HashSet<String> uniqueUsernames) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(books);
            oos.writeObject(users);
            oos.writeObject(uniqueNumbers);
            oos.writeObject(uniqueUsernames);
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public static void readFromFile(String fileName, ArrayList<Book> books, ArrayList<NormalUserClass> users, HashSet<String> uniqueNumbers, HashSet<String> uniqueUsernames) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            ArrayList<Book> readBooks = (ArrayList<Book>) ois.readObject();
            ArrayList<NormalUserClass> readUsers = (ArrayList<NormalUserClass>) ois.readObject();
            HashSet<String> readUniqueNumbers = (HashSet<String>) ois.readObject();
            HashSet<String> readUniqueUsernames = (HashSet<String>) ois.readObject();
            books.addAll(readBooks);
            users.addAll(readUsers);
            uniqueNumbers.addAll(readUniqueNumbers);
            uniqueUsernames.addAll(readUniqueUsernames);
            System.out.println("Data read from file successfully.");
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            System.out.println("Creating a new file...");
            writeToFile(fileName, new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new HashSet<>());
        }
    }


    public static void writeToFile_dir(String directory, String fileName, ArrayList<Book> books, ArrayList<NormalUserClass> users,HashSet<String> uniqueNumbers, HashSet<String> uniqueUsernames) {
        File file = new File(directory, fileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(books);
            oos.writeObject(users);
            oos.writeObject(uniqueNumbers);
            oos.writeObject(uniqueUsernames);
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