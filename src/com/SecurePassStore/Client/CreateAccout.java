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
        setResizable(false);
        setTitle("Secure Password Store 0.1");

        createAccountButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                //TODO: add check for  letters, numbers and symbols
                //TODO: add check for valid email format

                if(Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword()))
                {
                    if (passwordField1.getPassword().length < 8)
                    {
                        Client.showDialog(1,"Password must be more than 8 characters long", 2);
                    } else
                        {


                        String email = emailField.getText();
                        char[] password = passwordField1.getPassword();
                        if (addUser(email, password))
                        {
                            Client.showDialog(1, "Account Created!", 3);
                            Client.ClearPanel(rootPanel);
                            Client.DisposeCreate();

                        }
                        else
                            {
                            Client.showDialog(1,"User Already Registered", 2);
                        }
                        }

                }
                else
                {
                    Client.showDialog(1,"Passwords do not match", 2);
                }


                }

        }
        );
    }
}
