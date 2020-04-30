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

    public CreateAccout()
    {
        add(rootPanel);
        setSize(250, 150);
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
                        System.out.println("master password must be at least 8 char");
                    } else
                        {


                        String email = emailField.getText();
                        char[] password = passwordField1.getPassword();
                        if (addUser(email, password)) {
                            System.out.println("user added");
                            Client.ClearPanel(rootPanel);
                            Client.DisposeCreate();

                        } else {
                            System.out.println("unable to add user");
                        }

                        }

                }
                else
                {
                    System.out.println("passwords do not match");
                }


                }

        }
        );
    }
}
