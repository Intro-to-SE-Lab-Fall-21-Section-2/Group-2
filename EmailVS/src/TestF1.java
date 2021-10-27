
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestF1 {
  @Test
  public void validateUser() {
    accountManager acctMan = new accountManager();
    acctMan.addAccount("Test123", "password");
    int test = acctMan.authenticateUser("Test123", "password");
    if(test == 2){
        System.out.println("Log in worked successfully");
    }
  }
  @Test
  public void invalidateUser() {
    accountManager acctMan = new accountManager();
    acctMan.addAccount("Test123", "password");
    int test = acctMan.authenticateUser("Test12", "password");
    if(test == 2){
        System.out.println("Log in worked successfully");
    }
  }
}