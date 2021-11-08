import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.io.File;
public class TestF3 {
    @Test
    public void attachmentTest(){
        accountManager acctMan = new accountManager();
        acctMan.addAccount("user", "pass");
        acctMan.addAccount("user2", "pass2");
        File attchmt = new File("GroceryList.txt");
        email toSend = new email("user2", "user", "Test", "This is a test",attchmt);
        account sender = acctMan.accountList.get("user");
        sender.addToOutbox(toSend);
        account receiver = acctMan.accountList.get("user2");
        receiver.addToInbox(toSend);
    }

    @Test
    public void attchTestJPG(){
        accountManager acctMan = new accountManager();
        acctMan.addAccount("user", "pass");
        acctMan.addAccount("user2", "pass2");
        File attchmt = new File("shells.jpg");
        email toSend = new email("user2", "user", "Test", "This is a test",attchmt);
        account sender = acctMan.accountList.get("user");
        sender.addToOutbox(toSend);
        account receiver = acctMan.accountList.get("user2");
        receiver.addToInbox(toSend);
    }
}
