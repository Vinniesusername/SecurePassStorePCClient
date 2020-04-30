package com.SecurePassStore.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static com.SecurePassStore.App.Security.addUser;

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

    CreateAccout()
    {
        add(rootPanel);
        setSize(850, 350);
        setLocationRelativeTo(null);
        setTitle("Secure Password Store 0.1");

        createAccountButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                //add check for minimum number of chars
                //add check for valid email format

                if(Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword()))
                {
                    if (passwordField1.getPassword().length < 8)
                    {
                        Client.showDialog("Password must be more than 8 characters long", 2);
                    } else
                        {


                        String email = emailField.getText();
                        char[] password = passwordField1.getPassword();
                        if (addUser(email, password))
                        {
                            Client.showDialog("Account Created!", 3);
                            Client.ClearPanel(rootPanel);
                            Client.DisposeCreate();

                        }
                        else
                            {
                            Client.showDialog("User Already Registered", 2);
                        }
                        }

                }
                else
                {
                    Client.showDialog("Passwords do not match", 2);
                }


                }

        }
        );
    }
}
