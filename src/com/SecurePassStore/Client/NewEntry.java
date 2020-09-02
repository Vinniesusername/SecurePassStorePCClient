package com.SecurePassStore.Client;

import com.SecurePassStore.App.Generator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEntry extends JFrame
{

    private static Client client = Client.getClientInstance();
    private static NewEntry newEntry;
    private String generatedPassword;



    private JPanel rootpanel;
    private JLabel namelabel;
    private JLabel passwordLabel;
    private JButton generatePasswordButton;
    private JPasswordField passwordField;
    private JTextField urlField;
    private JTextField nameField;
    private JButton addButton;

    private NewEntry()
    {
        add(rootpanel);
        setSize(550, 500);
        setLocationRelativeTo(null);

        generatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                client.ShowCreateAccountADV(newEntry);

            }
        });
    }


    public static NewEntry getInstance()
    {
        if(newEntry == null)
        {
            newEntry = new NewEntry();
        }
        return newEntry;
    }

    public void  getInfo(String[] info)
    {
        generatedPassword = info[0];
    }

    public void actOnInfo()
    {
        passwordField.setText(generatedPassword);
    }
}
