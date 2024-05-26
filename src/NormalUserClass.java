import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class NormalUserClass extends User implements Serializable {
    public NormalUserClass(String username, String password)throws Exception{
        super(username, password);
    }

}
