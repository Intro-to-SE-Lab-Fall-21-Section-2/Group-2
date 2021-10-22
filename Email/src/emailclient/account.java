/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailclient;
import java.util.ArrayList;
import java.util.Arrays;

public class account {
    private String username; //This is without the @
    private String password;
    private boolean isLoggedIn;
    private ArrayList<email> draftList = new ArrayList();
    private ArrayList<email> inboxList = new ArrayList();
    private ArrayList<email> outboxList = new ArrayList();

    // Constructor 
    public account(String Un, String Pw){
        username = Un;
        password = Pw;
        isLoggedIn = false; //May need to change this to true later
    } 
    
    public void addDraft(email draft){
        draftList.add(draft);
    }
    
    public void addToInbox(email sentEmail){
        inboxList.add(sentEmail);
    }
    
    public void addToOutbox(email sentEmail){
        outboxList.add(sentEmail);   
    }
    
    public String[] getDraftArray(){
        String draftArray[];
        draftArray = new String[draftList.size()];
        int x = 0;
        for (email i: draftList){
            draftArray[x] = i.getSubjectLine();
            x++;
        }
        return draftArray;
    }
    
    public String[] getInboxArray(){
        String inboxArray[];
        inboxArray = new String[inboxList.size()];
        int x = 0;
        for (email i: inboxList){
            inboxArray[x] = i.getSubjectLine();
            x++;
        }
        return inboxArray;
    }
    
    public String[] getOutboxArray(){
        String outboxArray[];
        outboxArray = new String[outboxList.size()];
        int x = 0;
        for (email i: outboxList){
            outboxArray[x] = i.getSubjectLine();
            x++;
        }
        return outboxArray;
    }
    
    public ArrayList<email> search(String input){
        ArrayList<email> results = new ArrayList();
        for(email a: inboxList){
            if(a.getRecipient().equals(input) || a.getSender().equals(input)){
                results.add(a);
            }
        }
        for(email b: outboxList){
            if(b.getRecipient().equals(input) || b.getSender().equals(input)){
                results.add(b);
            }
        }
        return results;
    }
    
    public email getDraftEmail(int index){
        return draftList.get(index);
    }
    
    public email getInboxEmail(int index){
        return inboxList.get(index);
    }
    
    public email getOutboxEmail(int index){
        return outboxList.get(index);
    }
    
    public void removeDraft(email toDelete){
        draftList.remove(toDelete);
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

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public boolean getIsLoggedIn(){
        return isLoggedIn;
    }
}
