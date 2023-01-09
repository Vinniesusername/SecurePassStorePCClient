package com.SecurePassStore.Client.PC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import static com.SecurePassStore.Client.PC.LoginHandler.validPasswordCheck;
import static com.SecurePassStore.Client.PC.LoginHandler.addUser;

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
    private static final Gui guiHandler = Gui.getClientInstance();
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
                        guiHandler.showDialog(createhandler,"Password must be more than 8 characters long", 2);
                    } else if(emailField.getText().length() < 6)
                    {
                        guiHandler.showDialog(createhandler, "User name must be at least 6 letters", 2);
                    }
                    else if(!validPasswordCheck(passwordField1.getPassword()))
                    {
                        guiHandler.showDialog(createhandler, "password must contain at least one upper case, lower case" +
                                ", number and symbol", 2);
                    }
                    else
                        {
                        String email = emailField.getText();
                        char[] password = passwordField1.getPassword();
                        if (addUser(email, password, localKey))
                        {
                            guiHandler.showDialog(createhandler, "Account Created!", 3);
                            guiHandler.ClearPanel(rootPanel);
                            guiHandler.DisposeCreate();
                        }
                        else
                            {
                            guiHandler.showDialog(createhandler,"User Already Registered", 2);
                        }
                        }
                }
                else
                {
                    guiHandler.showDialog(createhandler,"Passwords do not match", 2);
                }
            }
        }
        );
        advOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                guiHandler.ShowCreateAccountADV(new CreateAccout());
            }
        });
    }

    String[] getInfo(String[] info)
    {
        if(info[0] != null)
            suggestedPassword = info[0];
        localKey = Boolean.parseBoolean(info[1]);
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