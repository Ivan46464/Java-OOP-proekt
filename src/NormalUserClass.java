import java.io.Serializable;

public class NormalUserClass extends User implements Serializable {
    public NormalUserClass(String username, String password)throws Exception{
        super(username, password);
    }

}
