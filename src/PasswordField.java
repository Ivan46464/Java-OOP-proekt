import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;

public class PasswordField {

    public static String readPassword(String prompt) {
        EraserThread et = new EraserThread(prompt);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(et, 0, 1);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";

        try {
            password = in.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        // stop masking
        timer.cancel();
        // return the password entered by the user
        return password;
    }
}