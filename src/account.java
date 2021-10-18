import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class accountManager {
    private Map <String, account> accountList = new HashMap<String, account>();

    public static void main (String [] args) {
        // I had to create an instance of the main class here because of the rules between static and non static methods
        //Any time a method of this class is called it will be called using this instance 
        accountManager acctManager = new accountManager(); 
        int menuInput = 0;
        String curUser = null;
        Scanner blue = new Scanner(System.in); //Scanners are named after colors 
        Scanner red = new Scanner(System.in);
        do{
            acctManager.menu();
            menuInput = red.nextInt();
            switch(menuInput){
                case 1:
                System.out.println("Type in your desired username");
                String userIn = blue.nextLine();
                System.out.println("Type in your desired password");
                String passIn = blue.nextLine();
                boolean addAcctFlag = acctManager.addAccount(userIn, passIn);
                if (addAcctFlag == true){
                    System.out.println("Account successfully created.");
                }
                break;

                case 2: 
                System.out.println("Enter your username");
                String userIn2 = blue.nextLine();
                System.out.println("Enter in your password");
                String passIn2 = blue.nextLine();
                boolean loginFlag = acctManager.authenticateUser(userIn2, passIn2); //add stuff for the bool
                break;

                case 3:
                System.out.println("Enter your username");
                String userIn3 = blue.nextLine();
                acctManager.logoutUser(userIn3);
                break; 

                case 4:
                System.out.println("Enter your username");
                String userIn4 = blue.nextLine();
                acctManager.deleteAccount(userIn4);
                System.out.println("Account Deleted");
                break;

                case 5:
                System.out.println("Exiting Menu");
                acctManager.writeEmail(curUser);
                    break;

                default:
                System.out.println("Invalid Input");

            }
        }
        while(menuInput != 5);
        blue.close();
        red.close();
    }

    private boolean addAccount(String username, String password){

        //Checking for preexisting account under username
        if (accountList.containsKey(username) == true){
            System.out.println("There is already an account under this username. Please choose another.");
            return false;
        }

        account newAccount = new account(username, password);
        accountList.put(username, newAccount);

        return true;
    }

    private void deleteAccount(String username){
        //The user needs to be logged in to use this function
        //this function needs to be revised to be able to delete emails and other account data
        accountList.remove(username);
    }

    private boolean authenticateUser(String inUser, String inPass){
        //Getting user data from hash map
        account user = accountList.get(inUser);

        //Testing if username input is a valid account
        if(user == null){
            System.out.println("There is no existing account with this username.");
            return false;
        }

        //Checking if password is correct 
        String password = user.getPassword();
        if(inPass.equals(password)){
            user.login();
            System.out.println("Logged in successfully.");
            return true;
        }
        else{
            System.out.println("Password is incorrect.");
            return false;
        }

    }

    private boolean logoutUser (String username){
        //need to add a check for the logged in bool to see if 
        //they are already logged in
        account user = accountList.get(username);
        user.logout();
        return true;
    }

    private void menu(){
        System.out.println("Menu");
        System.out.println("1. Create new account");
        System.out.println("2. Login to account");
        System.out.println("3. Logout of account");
        System.out.println("4. Delete account");
        System.out.println("5. Exit menu ");
    }


    private void writeEmail(String user){

        if(user == "")
        {
            System.out.println("ERROR: user not logged in.");
            return;
        }

        Scanner recieve = new Scanner(System.in);

        System.out.println("Enter target email to submit to:");
        String target = recieve.nextLine();
        System.out.println("Enter the subject");
        String subject = recieve.nextLine();
        System.out.println("Enter the message:");
        String text = recieve.nextLine();


      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(user));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(target));

         // Set Subject: header field
         message.setSubject(subject);

         // Now set the actual message
         message.setText(text);

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
       return;
    }

}



public class account {
       private String username; //This is without the @
    private String password;
    private String email;
    private boolean isLoggedIn;
    //have email file here

    // Constructor 
    public account(String Un, String Pw){
        username = Un;
        password = Pw;
        email = Un + ".txt";
        isLoggedIn = false; //May need to change this to true later
    } 

    //Login
    public void login(){
        isLoggedIn = true;
    }

    //Logout
    public void logout(){
        isLoggedIn = false;
    }

    //settters and getters
    public void setUsername(String Un){
        username=Un;
    }

    public void setPassword(String Pw){
        password=Pw;
    }

    public void setEmail(String Un){
        email=Un+".txt";
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    public boolean getIsLoggedIn(){
        return isLoggedIn;
    }
}
