package com.SecurePassStore.Client;

import javax.swing.*;

public class NewEntry extends JFrame
{

    private static Client client = Client.getClientInstance();
    private static NewEntry newEntry;


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

    }


    public static NewEntry getInstance()
    {
        if(newEntry == null)
        {
            newEntry = new NewEntry();
        }
        return newEntry;
    }
}
