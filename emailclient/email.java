/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailclient;

/**
 *
 * @author ajlew
 */
import java.io.File; 

public class email {
    private String recipient;
    private String sender;
    private String subjectLine;
    private String body; 
    private File attachment;
    private boolean hasAttch;
    
    public email(String rec, String sen, String sub, String bod){
        recipient = rec;
        sender = sen;
        subjectLine = sub;
        body = bod;
        hasAttch = false;
    }
    
    public email(String rec, String sen, String sub, String bod, File attch){
        recipient = rec;
        sender = sen;
        subjectLine = sub;
        body = bod;
        attachment = attch;
        hasAttch = true;
    }
    
    
    public String getRecipient(){
        return recipient;
    }
    
    public String getSubjectLine(){
        return subjectLine;
    }
    
    public String getSender(){
        return sender;
    }
    
    public String getBody(){
        return body;
    }
    
    public boolean getHasAttch(){
        return hasAttch;
    }
    
    public File getAttachment(){
        return attachment;
    }
    
    
    public void setRecipient(String in){
        recipient = in;
    }
    
    public void setSubjectLine(String in){
        subjectLine = in;
    }
    
    public void setBody(String in){
        body = in;
    }
    
}
