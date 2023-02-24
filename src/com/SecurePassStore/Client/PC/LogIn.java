package com.SecurePassStore.Client.PC;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
    private static  Gui guiHandler = Gui.getInstance();
    private static LogIn handler;

    public static  LoginHandler loginHandler = LoginHandler.getInstance();

    private LogIn()
    {
        add(rootPanel);
        setSize(350, 150);
        setLocationRelativeTo(null);
        setResizable(false);

        logInButton.addActionListener(new ActionListener() { //TODO: call contains
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                if(loginHandler.checkUser(username, password) == 1) //if user authenticates
                {
                    guiHandler.passInfo(password, handler); //passing a Login frame to passinfo will set master password
                    guiHandler.showMainLanding(username);
                }
                else if(loginHandler.checkUser(username, password) == 0) //bad password
                {
                    guiHandler.showDialog(handler, "Log In Failed, Wrong password", 2);
                }
                else
                {
                    guiHandler.showDialog(handler, "user name does not exist. Create a new account", 2);
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
        if (handler == null)
        {
            handler = new LogIn();
        }
        return handler;
    }

}
