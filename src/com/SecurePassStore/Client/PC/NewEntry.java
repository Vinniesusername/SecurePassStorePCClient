package com.SecurePassStore.Client.PC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEntry extends JFrame
{

    private static final Gui guiHandler = Gui.getClientInstance();
    private static NewEntry newEntry;
    private String generatedPassword;
    private static final EncryptionHandler eh = EncryptionHandler.getInstance();
    private static final Client client = Client.getInstance();
    private final Tools tool = new Tools();


    private JPanel rootpanel;
    private JLabel namelabel;
    private JLabel passwordLabel;
    private JButton generatePasswordButton;
    private JPasswordField passwordField;
    private JTextField urlField;
    private JTextField nameField;
    private JButton addButton;
    private JTextField typeField;
    private JLabel typeLabel;
    private JButton viewButton;
    private boolean hidden = true;

    private NewEntry()
    {
        add(rootpanel);
        setSize(550, 500);
        setLocationRelativeTo(null);

        generatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                guiHandler.ShowCreateAccountADV(newEntry);

            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                if(hidden)
                {
                    passwordField.setEchoChar((char) 0);
                    hidden = false;
                }
                else
                {
                    passwordField.setEchoChar('*');
                    hidden = true;
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                eh.startUp(getMasterPassword());
                byte[][] encrypted;
                encrypted = eh.encryptPassword(String.valueOf(passwordField.getPassword()).getBytes());
                if(!client.addNewEntry(guiHandler.getUsername(), nameField.getText(), typeField.getText(), tool.byteArrayToHex(encrypted[0]),
                        tool.byteArrayToHex(encrypted[1]), urlField.getText()))
                {
                    System.out.println("error adding entry");
                }
                else
                {
                    guiHandler.showDialog(newEntry, "entry added", 3);
                    guiHandler.ClearPanel(rootpanel);
                    guiHandler.disposeNewEntry();
                }


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

    private char[] getMasterPassword()
    {
        return guiHandler.getMasterPassword();
    }

}
