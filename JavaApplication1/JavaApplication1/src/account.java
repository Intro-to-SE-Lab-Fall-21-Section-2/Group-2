/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author J
 */
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
