import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestF2{
    @Test
    public void Send() {
        accountManager acctMan = new accountManager();
        acctMan.addAccount("user", "pass");
        acctMan.addAccount("user2", "pass2");
        email toSend = new email("user2", "user", "Test", "This is a test");
        account sender = acctMan.accountList.get("user");
        sender.addToOutbox(toSend);
        account receiver = acctMan.accountList.get("user2");
        receiver.addToInbox(toSend);
    }
    @Test
    public void Draft() {
        accountManager acctMan = new accountManager();
        acctMan.addAccount("user", "pass");
        acctMan.addAccount("user2", "pass2");
        email toSend = new email("user2", "user", "Test", "This is a test");
        account sender = acctMan.accountList.get("user");
        sender.addDraft(toSend);
    }

    @Test
    public void deleteOutbox(){
        accountManager acctMan = new accountManager();
        acctMan.addAccount("user", "pass");
        acctMan.addAccount("user2", "pass2");
        email toSend = new email("user2", "user", "Test", "This is a test");
        account sender = acctMan.accountList.get("user");
        sender.addToOutbox(toSend);
        account receiver = acctMan.accountList.get("user2");
        receiver.addToInbox(toSend);
        sender.removeOutboxEmail(toSend);
    }

    @Test
    public void deleteInbox(){
        accountManager acctMan = new accountManager();
        acctMan.addAccount("user", "pass");
        acctMan.addAccount("user2", "pass2");
        email toSend = new email("user2", "user", "Test", "This is a test");
        account sender = acctMan.accountList.get("user");
        sender.addToOutbox(toSend);
        account receiver = acctMan.accountList.get("user2");
        receiver.addToInbox(toSend);
        receiver.removeInboxEmail(toSend);
    }
}
