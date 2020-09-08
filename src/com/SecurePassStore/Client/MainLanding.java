package com.SecurePassStore.Client;

import com.SecurePassStore.App.DataHandler;
import com.SecurePassStore.App.EncryptionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MainLanding extends JFrame
{

    private static Client client = Client.getClientInstance();
    private static MainLanding mainLanding;
    private static DataHandler dh = DataHandler.getInstance();
    private static EncryptionHandler eh = EncryptionHandler.getInstance();
    private char[] masterPassword;

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
                masterPassword = client.getMasterPassword();
                String username = client.getUsername();
                results = dh.getEntry("youtube account", client.getUsername());
                //String password = Arrays.toString(eh.decryptPassword(hexStringToByteArray(results[0]), hexStringToByteArray(results[1])));
                String password = null;
                try
                {
                    password = new String(eh.decryptPassword(hexStringToByteArray(results[0]), hexStringToByteArray(results[1])), "UTF-8");

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

    private static byte[] hexStringToByteArray(String hexString)
    {
        int size = hexString.length();
        if(size == 0)
        {
            System.exit(-1);
        }
        byte[] byteArray = new byte[size /2];
        for(int i = 0; i < size; i+= 2)
        {
            byteArray[i/2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) +
                    Character.digit(hexString.charAt(i+1), 16));

        }

        return byteArray;
    }


}
