import Exceptions.*;

import java.io.Serializable;
import java.util.*;

public abstract class User implements Serializable {
    private String username;
    private String password;
    private transient Scanner sc;
    public  User(String username, String password) throws Exception{
        setUsername(username);
        setPassword(password);
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
    public Scanner getSc() {
        return sc;
    }
    public void setSc(Scanner sc) {
        this.sc = sc;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
    public User logout(NonUserClass user){
        User current_user;
        current_user = user;
        return current_user;
    }


}
