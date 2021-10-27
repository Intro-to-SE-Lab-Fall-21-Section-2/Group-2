import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestF2 {
    @Test
    public void Send() {
        accountManager acctMan = new accountManager();
        acctMan.addAccount("user", "pass");
        acctMan.addAccount("user2", "pass2");
        email toSend = new email("user", "user2", "Test", "This is a test");

    }
}
