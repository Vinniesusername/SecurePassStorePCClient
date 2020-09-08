package com.SecurePassStore.Client;

import com.SecurePassStore.App.DataHandler;
import com.SecurePassStore.App.EncryptionHandler;
import com.SecurePassStore.App.Tools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainLanding extends JFrame
{

    private static Client client = Client.getClientInstance();
    private static MainLanding mainLanding;
    private static DataHandler dh = DataHandler.getInstance();
    private static EncryptionHandler eh = EncryptionHandler.getInstance();
    private static Tools tool;
    char[] masterPassword;


    private JPanel rootPanel;
    private JButton addLogIn;
    private JButton getEntryButton;
    private JTextField testField;


    private MainLanding()
    {
        add(rootPanel);
        setSize(550, 500);
        setLocationRelativeTo(null);

        addLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client.showNewEntry();

            }
        });
        getEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                eh.startUp(client.getMasterPassword());
                String[] results = new String[2];
                String username = client.getUsername();
                results = dh.getEntry("youtube account", client.getUsername());
                String password = null;
                try
                {
                    password = new String(eh.decryptPassword(tool.hexStringToByteArray(results[0]),
                            tool.hexStringToByteArray(results[1])), "UTF-8");

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                testField.setText(String.valueOf(password));
            }
        });
    }

    char[] getInfo(char[] info)
    {
        masterPassword = info;
        return info;

    }

    public static MainLanding getInstance()
    {
        if(mainLanding == null)
            mainLanding = new MainLanding();
        return mainLanding;
    }

}
