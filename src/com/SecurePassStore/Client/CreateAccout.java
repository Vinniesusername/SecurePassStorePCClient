package com.SecurePassStore.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import static com.SecurePassStore.App.LoginHandler.validPasswordCheck;
import static com.SecurePassStore.App.LoginHandler.addUser;

public class CreateAccout extends JFrame
{
    private JPanel rootPanel;
    private JButton createAccountButton;
    private JTextField emailField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JLabel emailLabel;
    private JLabel passwordLabel1;
    private JLabel passwordLabel2;
    private JButton advOptionsButton;
    private static Client client = Client.getClientInstance();
    private String suggestedPassword = null;
    private boolean localKey = false;
    private static CreateAccout createhandler;


    private CreateAccout()
    {

        add(rootPanel);
        setSize(850, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Secure Password Store 0.1");



        createAccountButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                //TODO: add check for valid email format

                if(Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword()))
                {
                    if (passwordField1.getPassword().length <= 8)
                    {
                        client.showDialog(1,"Password must be more than 8 characters long", 2);
                    } else if(emailField.getText().length() < 6)
                    {
                        client.showDialog(1, "User name must be at least 6 letters", 2);
                    }
                    else if(!validPasswordCheck(passwordField1.getPassword()))
                    {
                        client.showDialog(1, "password must contain at least one upper case, lower case" +
                                ", number and symbol", 2);
                    }
                    else
                        {
                        String email = emailField.getText();
                        char[] password = passwordField1.getPassword();
                        if (addUser(email, password))
                        {
                            client.showDialog(1, "Account Created!", 3);
                            client.ClearPanel(rootPanel);
                            client.DisposeCreate();
                        }
                        else
                            {
                            client.showDialog(1,"User Already Registered", 2);
                        }
                        }
                }
                else
                {
                    client.showDialog(1,"Passwords do not match", 2);
                }
            }
        }
        );
        advOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                client.ShowCreateAccountADV();
            }
        });
    }

    String[] getInfo(String[] info)
    {
        if(info[0] != null)
            suggestedPassword = info[0];
        localKey = Boolean.getBoolean(info[1]);
        return info;
    }

    void actOnInfo()
    {
        passwordField1.setText(suggestedPassword);
        passwordField2.setText(suggestedPassword);

        emailField.repaint();
        passwordField1.repaint();


    }

    public static CreateAccout getInstance()
    {
        if(createhandler == null)
            createhandler = new CreateAccout();
        return createhandler;
    }


}
