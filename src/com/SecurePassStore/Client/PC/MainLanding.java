package com.SecurePassStore.Client.PC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;


public class MainLanding extends JFrame
{

    private static final Gui guiHandler = Gui.getInstance();
    private static MainLanding mainLanding;
    private static final Client client = Client.getInstance();
    private static final EncryptionHandler eh = EncryptionHandler.getInstance();
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
                guiHandler.showNewEntry();

            }
        });
        getEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                eh.startUp(guiHandler.getMasterPassword());
                String[] results = new String[2];
                String username = guiHandler.getUsername();
                results = client.getEntry("youtube account", username);
                String password = null;
                try
                {
                    password = new String(eh.decryptPassword(tool.hexStringToByteArray(results[0]),
                            tool.hexStringToByteArray(results[1])), StandardCharsets.UTF_8);
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
