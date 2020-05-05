package com.SecurePassStore.Client;

import javax.swing.*;
import java.awt.*;

public class Client
{
    private static LogIn login = new LogIn();
    private static CreateAccout createAccout = new CreateAccout();
    private static MainLanding mainLanding = new MainLanding();

    public static void main()
    {
        showLogin();
        if(showDialog(0,"This is only intended to be used for educational purposes. do not attempt to use this program " +
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
        createAccout.setAlwaysOnTop(true);
        createAccout.requestFocus();
    }

    static void DisposeCreate()
    {
        createAccout.dispose();
    }

    static void showMainLanding(String username)
    {
        mainLanding.setVisible(true);
        mainLanding.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static void DisposeMainLanding()
    {
        mainLanding.dispose();
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

    static JFrame getFrame(int mode)
    {
        switch(mode)
        {
            case 0:
                return login;
            case 1:
                return createAccout;
            case 2:
                return mainLanding;
            default:
                return new JFrame();
        }
    }

    static int showDialog(int screen, String message, int type)
    {
        JFrame root;
        int status = -1;
        if (screen == 0)
        {
           root = login;
        }
        else if(screen == 1)
        {
            root = createAccout;
        }
        else
        {
            root = new JFrame();
        }
        switch(type)
        {
            case 1: //type 1 = confirm message
            {
                status = JOptionPane.showConfirmDialog(root, message);
                break;
            }
            case 2: //type 2 = warning message
            {
                JOptionPane.showMessageDialog(root, message,"Error!", JOptionPane.ERROR_MESSAGE);
                break;
            }
            case 3: //information case
            {
                JOptionPane.showMessageDialog(root, message, "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return status;
    }
}
