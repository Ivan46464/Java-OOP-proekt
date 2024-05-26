import java.io.Console;
import java.util.ArrayList;

public class NonUserClass extends User {

    public NonUserClass(String Username, String Password)throws Exception {
        super(Username, Password);
    }

    public User login(ArrayList<NormalUserClass> normal_users, AdminUserClass admin_user, NonUserClass user) {
        User current_user = null;
        System.out.println("Write your username: ");
        String Username = getSc().nextLine();
        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available.");
        }

        System.out.println("Write your password: ");
        char[] passwordChars = console.readPassword();
        String password = new String(passwordChars);

        
        for (int i = 0; i < passwordChars.length; i++) {
            passwordChars[i] = '\0';
        }

        for (NormalUserClass normal_user : normal_users) {
            if (Username.equals(normal_user.getUsername()) & password.equals(normal_user.getPassword())) {
                current_user = (NormalUserClass) normal_user;
                System.out.println("Welcome " + current_user.getUsername());
                return current_user;

            }

        }
        if (current_user == null) {
            if (admin_user.getUsername().equals(Username) & admin_user.getPassword().equals(password)) {
                current_user = (AdminUserClass) admin_user;
                System.out.println("Welcome " + current_user.getUsername());
            } else {
                System.out.println("There is no user with such password or username.");
                current_user = (NonUserClass) user;
            }
        }
        return current_user;
    }

}

