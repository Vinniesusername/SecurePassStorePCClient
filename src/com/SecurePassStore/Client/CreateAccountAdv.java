package com.SecurePassStore.Client;

import com.SecurePassStore.App.Generator;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class CreateAccountAdv extends JFrame {
    private JRadioButton enableLocalKeyRadioButton;
    private JPanel rootPanel;
    private JTextField suggestedPasswordField;
    private JButton generateMasterPasswordButton;
    private JButton saveOptionsAndSelectButton;
    private JRadioButton includeUpperCaseRadioButton;
    private JRadioButton includeSymbolsRadioButton;
    private JRadioButton includeNumbersRadioButton;
    private JRadioButton includeLowerCaseRadioButton;
    private JSlider charSlider;
    private JLabel slideLabel;
    private static Client client = Client.getClientInstance();

    public CreateAccountAdv() {
        add(rootPanel);
        setSize(850, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Secure Password Store 0.1");


        generateMasterPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                Generator gen = new Generator();
                boolean upper = includeUpperCaseRadioButton.isSelected();
                boolean lower = includeLowerCaseRadioButton.isSelected();
                boolean symbols = includeSymbolsRadioButton.isSelected();
                boolean numbers = includeNumbersRadioButton.isSelected();
                int n = charSlider.getValue();
                if(upper || lower || numbers || symbols )
                {
                    suggestedPasswordField.setText(gen.generateNewPassword(n, lower, upper, numbers, symbols));
                }
                else
                {
                    client.showDialog(2, "must select at least one type of character", 2);
                }


            }
        });
        charSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent)
            {
                slideLabel.setText(String.valueOf(charSlider.getValue()));

            }
        });


        saveOptionsAndSelectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String[] info = new String[2];
                info[0] = suggestedPasswordField.getText();
                info[1] = String.valueOf(enableLocalKeyRadioButton.isSelected());
                client.passInfo(info, 0);
                client.actOnInfo(0);
                client.disposeCreateAccountADV();


            }
        });
    }

    private static String[] scanInfo(String password, boolean accKey)
    {
        String[] info = new String[2];
        if(password.length() > 0)
            info[0] = password;
        info[1] = String.valueOf(accKey);

        return info;


    }



}
