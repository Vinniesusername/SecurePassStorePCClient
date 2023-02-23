package com.SecurePassStore.Client.PC;

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
    private JFrame caller;
    private static final Gui guiHandler = Gui.getInstance();

    public CreateAccountAdv()
    {
        add(rootPanel);
        setSize(850, 350);
        setLocationRelativeTo(null);
        setResizable(false);


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
                    guiHandler.showDialog(new CreateAccountAdv(), "must select at least one type of character", 2);
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
                guiHandler.passInfo(info, caller);
                guiHandler.actOnInfo(caller);
                guiHandler.ClearPanel(rootPanel);
                guiHandler.disposeCreateAccountADV();


            }
        });
    }

    public void hideKey(boolean b)
    {
        enableLocalKeyRadioButton.setVisible(!b);
    }

    public void setCaller(JFrame c)
    {
        caller = c;
    }



}
