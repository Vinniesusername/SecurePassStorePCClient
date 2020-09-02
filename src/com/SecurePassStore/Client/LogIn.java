package com.SecurePassStore.Client;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.SecurePassStore.App.LoginHandler.checkUser;

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
    private static Client client = Client.getClientInstance();

    LogIn()
    {
        add(rootPanel);
        setSize(350, 150);
        setLocationRelativeTo(null);
        setTitle("Secure Password Store 0.1");
        setResizable(false);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                if(checkUser(username, password) == 1)
                {
                    client.passInfo(password, null);
                    client.showMainLanding(username);
                }
                else if(checkUser(username, password) == 0)
                {
                    client.showDialog(0, "Log In Failed, Wrong password", 2);
                }
                else
                {
                    client.showDialog(0, "user name does not exist. Create a new account", 2);
                }
                client.ClearPanel(rootPanel);
            }
        });
        newUserButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
               client.showCreateAccount();
            }
        });
    }

}
