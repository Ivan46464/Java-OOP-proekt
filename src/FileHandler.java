import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    public static void writeToFile(String fileName, ArrayList<Book> books, ArrayList<NormalUserClass> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(books);
            oos.writeObject(users);
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void readFromFile(String fileName, ArrayList<Book> books, ArrayList<NormalUserClass> users) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            ArrayList<Book> readBooks = (ArrayList<Book>) ois.readObject();
            ArrayList<NormalUserClass> readUsers = (ArrayList<NormalUserClass>) ois.readObject();
            books.addAll(readBooks);
            users.addAll(readUsers);
            System.out.println("Data read from file successfully.");
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            System.out.println("Creating a new file...");
            writeToFile(fileName, new ArrayList<>(), new ArrayList<>());
        }
    }

    public static void writeToFile_dir(String directory, String fileName, ArrayList<Book> books, ArrayList<NormalUserClass> users) {
        File file = new File(directory, fileName);
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
            // No need to write anything, just closing the stream
            System.out.println("File closed successfully.");
        } catch (IOException e) {
            System.out.println("Error closing file: " + e.getMessage());
        }
    }
}