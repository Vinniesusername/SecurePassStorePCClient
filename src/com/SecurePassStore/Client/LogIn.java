package com.SecurePassStore.Client;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.SecurePassStore.App.Security.addUser;
import static com.SecurePassStore.App.Security.checkUser;

public class LogIn extends JFrame
{

    private JPanel rootPanel;
    private JButton logInButton;
    private JLabel usernameLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JButton newUserButton;
    private JButton loginButton;

    LogIn()
    {
        add(rootPanel);
        setSize(250, 150);
        setLocationRelativeTo(null);
        setTitle("Secure Password Store 0.1");


        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                System.out.println(checkUser(username,password));
                Client.ClearPanel(rootPanel);


            }
        });
        newUserButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
               Client.showCreateAccount();


            }
        });
    }

}
