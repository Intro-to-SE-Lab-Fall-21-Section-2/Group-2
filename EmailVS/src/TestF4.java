import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class TestF4 {
    @Test
    public void searchTest(){
        account user = new account("username", "password");
        ArrayList <email> searchResult = user.search("hello");
        assertTrue(searchResult.isEmpty());
    }

    @Test
    public void searchTestWResults(){
        account user = new account("username", "password");
        email toSend = new email("user", "user", "Email Subject", "Email Body");
        user.addToInbox(toSend);
        user.addToOutbox(toSend);
        ArrayList <email> searchResult = user.search("user");
        assertTrue(searchResult.contains(toSend));
    }

    @Test
    public void subjectSearch(){
        account user = new account("username", "password");
        email toSend = new email("user", "user", "Email Subject", "Email Body");
        user.addToInbox(toSend);
        user.addToOutbox(toSend);
        ArrayList <email> searchResult = user.search("Email Subject");
        assertTrue(searchResult.contains(toSend));
    }

    @Test
    public void bodySearch(){
        account user = new account("username", "password");
        email toSend = new email("user", "user", "Email Subject", "Email Body");
        user.addToInbox(toSend);
        user.addToOutbox(toSend);
        ArrayList <email> searchResult = user.search("Email Body");
        assertTrue(searchResult.contains(toSend));
    }


}
