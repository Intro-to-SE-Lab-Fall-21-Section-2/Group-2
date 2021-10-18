package emailclient;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.*;
import java.awt.Desktop;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;

//Because of the way the GUI fits in with the code, the functionalities are mostly sorted by
//what button or event they are activated by 

public class accountManager {
    private Map <String, account> accountList = new HashMap<String, account>();
    
    public static void main (String [] args) {
        // I had to create an instance of the main class here because of the rules between static and non static methods
        //Any time a method of this class is called it will be called using this instance 
        accountManager acctManager = new accountManager(); 
        acctManager.loginScreen();
    }

    private String addAccount(String username, String password){
        //Checking for no input
        if (username.isEmpty() || password.isEmpty()){
            return "Please type in a username and password.";
        }
        
        //Checking for spaces in input
        if(username.contains(" ") || password.contains(" ")){
            return "Usernames and passwords cannot contain spaces";
        }
        //Checking for preexisting account under username
        if (accountList.containsKey(username) == true){
            return "There is already an account under this username. Please choose another.";
        }

        account newAccount = new account(username, password);
        accountList.put(username, newAccount);
        return "Account created successfully.";
    }

    private void deleteAccount(String username){
        //The user needs to be logged in to use this function
        //this function needs to be revised to be able to delete emails and other account data
        accountList.remove(username);
    }

    private int authenticateUser(String inUser, String inPass){
        //Getting user data from hash map
        account user = accountList.get(inUser);

        //Testing if username input is a valid account
        if(user == null){
            return 1;
        }

        //Checking if password is correct 
        String password = user.getPassword();
        if(inPass.equals(password)){
            user.login();
            return 2;
        }
        else{
            return 3;
        }

    }

    private boolean logoutUser (account user){
        //need to add a check for the logged in bool to see if 
        //they are already logged in
        user.logout();
        return true;
    }
    
    private void homeScreen(String username){
        account user = accountList.get(username);
        JFrame InboxFrame = new JFrame("Inbox");
        JTabbedPane JTabbedPane1 = new JTabbedPane();
        JScrollPane inboxScrollPane = new JScrollPane();
        JScrollPane outboxScrollPane = new JScrollPane();
        JScrollPane draftScrollPane = new JScrollPane();
        JList<String> draftDisplayList = new JList<>();
        JList<String> inboxDisplayList = new JList<>();
        JList<String> outboxDisplayList = new JList<>();
        JScrollPane jScrollPane1 = new JScrollPane();
        JPanel jPanel1 = new JPanel();
        JTextField recipientField = new JTextField();
        JTextField subjectField = new JTextField();
        JTextField searchField = new JTextField();
        JTextArea bodyField = new JTextArea();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel userLabel = new JLabel();
        JLabel searchLabel = new JLabel();
        JButton logoutButton = new JButton();
        JButton deleteAccountButton = new JButton();
        JButton sendButton = new JButton();
        JButton attachButton = new JButton();
        JButton saveButton = new JButton();
        JButton outboxButton = new JButton();
        JButton inboxButton = new JButton();
        JButton draftButton = new JButton();
        JButton searchButton = new JButton();
        JFileChooser attchChooser = new JFileChooser();
        JFileChooser saveAttch = new JFileChooser();

        InboxFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane1.setToolTipText("");
        JTabbedPane1.setName(""); // NOI18N
        JTabbedPane1.addTab("Inbox", inboxScrollPane);
        JTabbedPane1.addTab("Outbox", outboxScrollPane);
        JTabbedPane1.addTab("Drafts", draftScrollPane);

        //Displaying the Drafts
        draftDisplayList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = user.getDraftArray();
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
       
        draftDisplayList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        draftScrollPane.setViewportView(draftDisplayList);
        
        //Displaying the Inbox
        inboxDisplayList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = user.getInboxArray();
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
       
        inboxDisplayList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        inboxScrollPane.setViewportView(inboxDisplayList);
        
        //Displaying the Outbox
        outboxDisplayList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = user.getOutboxArray();
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
       
        outboxDisplayList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        outboxScrollPane.setViewportView(outboxDisplayList);
        
        bodyField.setColumns(20);
        bodyField.setRows(5);
        jScrollPane1.setViewportView(bodyField);

        jLabel1.setText("Recipients");

        jLabel2.setText("Subject");
        
        //Send Function
        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String recipient = recipientField.getText();
                String sender = user.getUsername();
                String subject = subjectField.getText();
                //Testing for a subject line
                if(subject != null) {
                    String body = bodyField.getText();
                    //Testing if recipient is valid
                    if(accountList.containsKey(recipient)){
                            account sendTo = accountList.get(recipient);
                            email newEmail = new email(recipient, sender, subject, body);
                            sendTo.addToInbox(newEmail);
                            user.addToOutbox(newEmail); 
                            JOptionPane.showMessageDialog(InboxFrame, "Email Sent");
                            
                            //Adding the email to the outbox
                            outboxDisplayList.setModel(new javax.swing.AbstractListModel<String>() {
                                String[] strings = user.getOutboxArray();
                                public int getSize() { return strings.length; }
                                public String getElementAt(int i) { return strings[i]; }
                            });
                    } 
                    else{
                            JOptionPane.showMessageDialog(InboxFrame, "Error, invalid recipient.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(InboxFrame, "Please enter a subject line.");
                }
                // Going to to the process of deleting the draft in the open draft function
            }
            
        });
        
        //Send with attachment
        attachButton.setText("Send with Attachment");
        attachButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int option = attchChooser.showOpenDialog(InboxFrame);
                if (option == JFileChooser.APPROVE_OPTION){
                    File attachment = attchChooser.getSelectedFile();
                    String recipient = recipientField.getText();
                    String sender = user.getUsername();
                    String subject = subjectField.getText();
                    //Testing for a subject line
                    if(subject != null) {
                        String body = bodyField.getText();
                        //Testing if recipient is valid
                        if(accountList.containsKey(recipient)){
                            account sendTo = accountList.get(recipient);
                            email newEmail = new email(recipient, sender, subject, body, attachment);
                            sendTo.addToInbox(newEmail);
                            user.addToOutbox(newEmail); 
                            JOptionPane.showMessageDialog(InboxFrame, "Email Sent");
                            
                            //Adding the email to the outbox
                            outboxDisplayList.setModel(new javax.swing.AbstractListModel<String>() {
                                String[] strings = user.getOutboxArray();
                                public int getSize() { return strings.length; }
                                public String getElementAt(int i) { return strings[i]; }
                            });
                        } 
                        else{
                            JOptionPane.showMessageDialog(InboxFrame, "Error, invalid recipient.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(InboxFrame, "Please enter a subject line.");
                    }
                }
            }
        });

        //Save draft function
        saveButton.setText("Save as a Draft");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String sender = username;
                String recipient = recipientField.getText();
                String subject = subjectField.getText();
                String body = bodyField.getText();
                email newEmail = new email(recipient, sender, subject, body);
                user.addDraft(newEmail);
                JOptionPane.showMessageDialog(InboxFrame, "Draft Saved");
                recipientField.setText("");
                subjectField.setText("");
                bodyField.setText("");
                
                //Adding the new draft to the display
                draftDisplayList.setModel(new javax.swing.AbstractListModel<String>() {
                    String[] strings = user.getDraftArray();
                    public int getSize() { return strings.length; }
                    public String getElementAt(int i) { return strings[i]; }
                });
            }
        });
        
        //This block of code is GUI. 
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                    .addComponent(subjectField)
                    .addComponent(recipientField)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attachButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recipientField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subjectField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendButton)
                    .addComponent(attachButton)
                    .addComponent(saveButton))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        //Logout function
        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Maybe add things here to save a draft if open
                int input = JOptionPane.showConfirmDialog(InboxFrame, "Continue with logout?", "Continue with logout?", JOptionPane.OK_CANCEL_OPTION);
                if(input == 0){
                    logoutUser(user);
                    InboxFrame.dispose();
                    loginScreen();
                }
            }
        });

        //Delete function
        deleteAccountButton.setText("Delete Account");
        deleteAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int input = JOptionPane.showConfirmDialog(InboxFrame, "Continue with account deletion?", "Continue with account deletion?", JOptionPane.OK_CANCEL_OPTION);
                if(input == 0){
                    deleteAccount(username);
                    InboxFrame.dispose();
                    loginScreen();
                }
            }
        });
        
        //Open draft
        draftButton.setText("Open Draft");
        draftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(draftDisplayList.getSelectedIndex() != -1){
                    int index = draftDisplayList.getSelectedIndex();
                    email openDraft = user.getDraftEmail(index);
                    recipientField.setText(openDraft.getRecipient());
                    subjectField.setText(openDraft.getSubjectLine());
                    bodyField.setText(openDraft.getBody());
                }
            }
        });
        
        //Open sent email
        outboxButton.setText("Open Sent Email");
        outboxButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(outboxDisplayList.getSelectedIndex() != -1){
                    int index = outboxDisplayList.getSelectedIndex();
                    
                    //Fetching email
                    email openEmail = user.getOutboxEmail(index);
                    
                    //Attachment
                    if (openEmail.getHasAttch()){
                        int y = JOptionPane.showConfirmDialog(InboxFrame, "This email has an attachment. \nWould you like to open it?", "Attachment", JOptionPane.YES_NO_OPTION);
                        if(y == JOptionPane.YES_OPTION){
                            try {  
                                File attachment = openEmail.getAttachment();
                                if(Desktop.isDesktopSupported()){  
                                    Desktop desktop = Desktop.getDesktop(); 
                                    if(attachment.exists()){
                                        desktop.open(attachment);
                                    }  
                                }  
                                else{
                                    JOptionPane.showMessageDialog(InboxFrame, "Desktop is not Supported.");
                                } 
                            }
                            catch(Exception e){  
                                JOptionPane.showMessageDialog(InboxFrame, "Error: Something went wrong");
                            }  
                        }
                    }
                    
                    //Creating Dialog for email display
                    Object[] options = {
                        "Forward",
                        "Close"
                    };
                    int option = JOptionPane.showOptionDialog(InboxFrame, "Recipient: " 
                            + openEmail.getRecipient() + "\nSubject: " + 
                            openEmail.getSubjectLine() + "\n" + openEmail.getBody(),"Outbox", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
                    //Forward option
                    if(option == 0){
                        recipientField.setText("");
                        subjectField.setText("Fwd: " + openEmail.getSubjectLine());
                        bodyField.setText("Sender: " + openEmail.getSender() + "\nRecipient: " 
                                + openEmail.getRecipient() + "\nFwd: " + openEmail.getBody() + "\n");
                    }
                }
            }
        });
        
        //Open received email
        inboxButton.setText("Open Received Email");
        inboxButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(inboxDisplayList.getSelectedIndex() != -1){
                    int index = inboxDisplayList.getSelectedIndex();
                    
                    //Fetching the email
                    email openEmail = user.getInboxEmail(index);
                    
                    //Checking for attachement 
                    if (openEmail.getHasAttch()){
                        int y = JOptionPane.showConfirmDialog(InboxFrame, "This email has an attachment. \nWould you like to open it?", "Attachment", JOptionPane.YES_NO_OPTION);
                        if(y == JOptionPane.YES_OPTION){
                            try {  
                                File attachment = openEmail.getAttachment();
                                if(Desktop.isDesktopSupported()){  
                                    Desktop desktop = Desktop.getDesktop(); 
                                    if(attachment.exists()){
                                        desktop.open(attachment);
                                    }  
                                }  
                                else{
                                    JOptionPane.showMessageDialog(InboxFrame, "Desktop is not Supported.");
                                } 
                            }
                            catch(Exception e){  
                                JOptionPane.showMessageDialog(InboxFrame, "Error: Something went wrong");
                            }  
                        }
                        
                    }
                    //Creating Dialog for the email
                    Object[] options = {
                        "Forward",
                        "Close"
                    };
                    int option = JOptionPane.showOptionDialog(InboxFrame, "Recipient: " 
                            + openEmail.getRecipient() + "\nSubject: " + 
                            openEmail.getSubjectLine() + "\n" + openEmail.getBody(),"Inbox", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
                    //Forward option
                    if(option == 0){
                        recipientField.setText("");
                        subjectField.setText("Fwd: " + openEmail.getSubjectLine());
                        bodyField.setText("Sender: " + openEmail.getSender() + "\nRecipient: " 
                                + openEmail.getRecipient() + "\nFwd: " + openEmail.getBody() + "\n");
                    }
                }
            }
        });

        userLabel.setText("Welcome " + user.getUsername());

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String searchInput = searchField.getText();
                ArrayList<email> searchResult = user.search(searchInput);
                if(searchResult.isEmpty()){
                    JOptionPane.showMessageDialog(InboxFrame, "No results were found.");
                }
                else{
                    String[] emails = new String [searchResult.size()];
                    int w = 0;
                    for(email i: searchResult){
                        emails[w] = searchResult.indexOf(i) + " From: " + i.getSender() + " To: " 
                                + i.getRecipient() + " " + i.getSubjectLine();
                        w++;
                    }
                    Object[] possibilities = emails;
                    String k = (String)JOptionPane.showInputDialog(
                        InboxFrame, "Search Results: ",
                        "Search", JOptionPane.PLAIN_MESSAGE, null,
                        possibilities, emails[0]);
                    if ((k != null) && (k.length() > 0)) {
                        char ind = k.charAt(0);
                        int index = Character.getNumericValue(ind);
                        
                        email toOpen = searchResult.get(index);
                        //Fetching the email
                    
                    //Checking for attachement 
                    if (toOpen.getHasAttch()){
                        int y = JOptionPane.showConfirmDialog(InboxFrame, "This email has an attachment. \nWould you like to open it?", "Attachment", JOptionPane.YES_NO_OPTION);
                        if(y == JOptionPane.YES_OPTION){
                            try {  
                                File attachment = toOpen.getAttachment();
                                if(Desktop.isDesktopSupported()){  
                                    Desktop desktop = Desktop.getDesktop(); 
                                    if(attachment.exists()){
                                        desktop.open(attachment);
                                    }  
                                }  
                                else{
                                    JOptionPane.showMessageDialog(InboxFrame, "Desktop is not Supported.");
                                } 
                            }
                            catch(Exception e){  
                                JOptionPane.showMessageDialog(InboxFrame, "Error: Something went wrong");
                            }  
                        }
                        
                    }
                    //Creating Dialog for the email
                    Object[] options = {
                        "Forward",
                        "Close"
                    };
                    int option = JOptionPane.showOptionDialog(InboxFrame, "Recipient: " 
                            + toOpen.getRecipient() + "\nSubject: " + 
                            toOpen.getSubjectLine() + "\n" + toOpen.getBody(),"Inbox", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
                    //Forward option
                    if(option == 0){
                        recipientField.setText("");
                        subjectField.setText("Fwd: " + toOpen.getSubjectLine());
                        bodyField.setText("Sender: " + toOpen.getSender() + "\nRecipient: " 
                                + toOpen.getRecipient() + "\nFwd: " + toOpen.getBody() + "\n");
                    }
                    }
                }

            }
        });

        searchLabel.setText("Email Address");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(InboxFrame.getContentPane());
        InboxFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchLabel)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(inboxButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outboxButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(draftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inboxButton)
                    .addComponent(logoutButton)
                    .addComponent(deleteAccountButton)
                    .addComponent(draftButton)
                    .addComponent(outboxButton)
                    .addComponent(userLabel))
                .addGap(18, 18, 18)
                .addComponent(searchLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchButton)
                            .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        InboxFrame.pack();
        InboxFrame.setVisible(true);
    }

    private void loginScreen(){
        JFrame LoginFrame = new JFrame("Welcome"); 
        JTextField loginField1 = new JTextField();
        JTextField loginField2 = new JTextField();
        JTextField NAField1 = new JTextField();
        JTextField NAField2 = new JTextField();
        JButton NASubmit = new JButton();
        JButton loginSubmit = new JButton();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();

        LoginFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        NASubmit.setText("Create New Account");
        NASubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String userIn1 = NAField1.getText();
                String passIn1 = NAField2.getText();
                String addAcctFlag = addAccount(userIn1, passIn1);
                JOptionPane.showMessageDialog(LoginFrame, addAcctFlag);
            }
        });

        loginSubmit.setText("Login");
        loginSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String userIn2 = loginField1.getText();
                String passIn2 = loginField2.getText();
                int loginFlag = authenticateUser(userIn2, passIn2);
                if(loginFlag == 1){
                    JOptionPane.showMessageDialog(LoginFrame, "There is no existing account with this username.");
                }
                else if (loginFlag == 2){
                    JOptionPane.showMessageDialog(LoginFrame, "Logged in successfully.");
                    homeScreen(userIn2);
                    LoginFrame.dispose();
                }
                else if (loginFlag == 3){
                    JOptionPane.showMessageDialog(LoginFrame, "Password is incorrect.");
                }
                else{
                    JOptionPane.showMessageDialog(LoginFrame, "Error, something went wrong.");
                }
            }
        });

        jLabel1.setText("Username");

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        jLabel4.setText("Password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(LoginFrame.getContentPane());
        LoginFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginField2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(loginField1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(NASubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NAField2)
                        .addComponent(NAField1))
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(56, 56, 56))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NAField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NAField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginSubmit)
                    .addComponent(NASubmit))
                .addContainerGap(165, Short.MAX_VALUE))
        );

        LoginFrame.pack();
        LoginFrame.setVisible(true);
    }  
}

//account class
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

//email class
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

