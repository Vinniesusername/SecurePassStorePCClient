package com.SecurePassStore.Client;

import com.SecurePassStore.App.EncryptionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLanding extends JFrame
{

    private static Client client = Client.getClientInstance();
    private static MainLanding mainLanding;
    private char[] masterPassword;

    private JPanel rootPanel;
    private JButton addLogIn;
    private JButton getEntryButton;


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
