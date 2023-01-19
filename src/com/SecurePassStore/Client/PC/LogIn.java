package com.SecurePassStore.Client.PC;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.SecurePassStore.Client.PC.LoginHandler.checkUser;

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
    private static final Gui guiHandler = Gui.getClientInstance();
    private static LogIn logInHandler;  //re name this to avoid confusion

    private LogIn()
    {
        add(rootPanel);
        setSize(350, 150);
        setLocationRelativeTo(null);
        setTitle(guiHandler.version);
        setResizable(false);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                if(checkUser(username, password) == 1)
                {
                    guiHandler.passInfo(password, logInHandler);
                    guiHandler.showMainLanding(username);
                }
                else if(checkUser(username, password) == 0)
                {
                    guiHandler.showDialog(logInHandler, "Log In Failed, Wrong password", 2);
                }
                else
                {
                    guiHandler.showDialog(logInHandler, "user name does not exist. Create a new account", 2);
                }
                guiHandler.ClearPanel(rootPanel);
            }
        });
        newUserButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
               guiHandler.showCreateAccount();
            }
        });
    }

    public static LogIn getInstance()
    {
        if (logInHandler == null)
        {
            logInHandler = new LogIn();
        }
        return logInHandler;
    }

}
