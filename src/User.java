import Exceptions.InvalidPasswordException;
import Exceptions.InvalidUsernameException;

import java.io.Serializable;
import java.util.Scanner;

public abstract class User implements Serializable {

    private String username;
    private String password;

    private Boolean availability;

    private transient Scanner sc;
    public  User(String username, String password, Boolean availability) throws Exception{
        setUsername(username);
        setPassword(password);
        setAvailability(availability);
        sc = new Scanner(System.in);
    }
    public void setUsername(String username) throws InvalidUsernameException {
        if (username == null || username.length() < 2) {
            throw new InvalidUsernameException("Username cannot be null or empty or smaller than 2 chars");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidPasswordException {
        if (password == null || password.length() < 5) {
            throw new InvalidPasswordException("Password cannot be null or empty or smaller than 5 characters");
        }
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

}
