package com.SecurePassStore.Client;

import javax.swing.*;
import java.awt.*;

public class Client
{
    private static LogIn login = new LogIn();
    private static CreateAccout createAccout = new CreateAccout();

    public static void main()
    {
        showLogin();
        if(showDialog("This is only intended to be used for educational purposes. do not attempt to use this program " +
                "to handle your personal accounts. do you agree?", 1) != 0)
        {
            System.exit(0);
        }


    }

    static void showLogin()
    {
        login.setVisible(true);
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void DisposeLogin()
    {
        login.dispose();
    }

    static void showCreateAccount()
    {
        createAccout.setVisible(true);
        createAccout.toFront();
        createAccout.requestFocus();

    }

    static void DisposeCreate()
    {
        createAccout.dispose();
    }

    static void ClearPanel(JPanel root)
    {
        for(Component c: root.getComponents())
        {
            if(c instanceof JTextField)
            {
                ((JTextField) c).setText("");
            }
            else if(c instanceof JPasswordField)
            {
                ((JPasswordField)c).setText("");
            }
        }
    }

    static int showDialog(String message, int type)
    {
        int status = -1;
        switch(type)
        {
            case 1: //type 1 = confirm message
            {
                status = JOptionPane.showConfirmDialog(new JFrame(), message);
                break;
            }
            case 2: //type 2 = warning message
            {
                JOptionPane.showMessageDialog(new JFrame(), message,"Error!", JOptionPane.ERROR_MESSAGE);
                break;
            }
            case 3: //information case
            {
                JOptionPane.showMessageDialog(new JFrame(), message, "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        return status;

    }

}
