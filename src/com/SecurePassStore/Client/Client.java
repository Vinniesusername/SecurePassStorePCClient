package com.SecurePassStore.Client;

import javax.swing.*;
import java.awt.*;

public class Client
{
    private static  Client clientHandler;
    private LogIn login = new LogIn();
    private CreateAccout createAccout = CreateAccout.getInstance();
    private MainLanding mainLanding = new MainLanding();
    private CreateAccountAdv cAAdv = new CreateAccountAdv();

    private Client()
    {


    }
    public static Client getClientInstance()
    {
        if(clientHandler == null)
            clientHandler = new Client();
        return clientHandler;
    }

    void passInfo(String[] info, int receiver)
    {
        //TODO: change numbering when other passinfo options are implemented
        switch (receiver)
        {
            case 0:
                createAccout.getInfo(info);
                break;
        }

    }

    void actOnInfo(int frame)
    {
        switch(frame)
        {
            case 0:
                createAccout.actOnInfo();

        }

    }


    public void startup()
    {
        showLogin();
        if(showDialog(0,"This is only intended to be used for educational purposes. do not attempt to use this program " +
                "to handle your personal accounts. do you agree?", 1) != 0)
        {
            System.exit(0);
        }
    }

    public void showLogin()
    {
        clientHandler.login.setVisible(true);
        clientHandler.login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void DisposeLogin()
    {
        clientHandler.login.dispose();
    }

    void showCreateAccount()
    {
        clientHandler.createAccout.setVisible(true);
        clientHandler.createAccout.setAlwaysOnTop(true);
        clientHandler.createAccout.requestFocus();
    }

    void ShowCreateAccountADV()
    {
        clientHandler.cAAdv.setVisible(true);
        clientHandler.cAAdv.setAlwaysOnTop(true);


    }

    void disposeCreateAccountADV()
    {
        clientHandler.cAAdv.dispose();
        ;
    }

    void DisposeCreate()
    {
        clientHandler.createAccout.dispose();
    }

    void showMainLanding(String username)
    {
        clientHandler.mainLanding.setVisible(true);
        clientHandler.mainLanding.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void DisposeMainLanding()
    {
        clientHandler.mainLanding.dispose();
    }

    void ClearPanel(JPanel root)
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
            else if(c instanceof JRadioButton)
            {
                ((JRadioButton)c).setSelected(false);
            }
        }
    }

    JFrame getFrame(int mode)
    {
        switch(mode)
        {
            case 0:
                return clientHandler.login;
            case 1:
                return clientHandler.createAccout;
            case 2:
                return clientHandler.mainLanding;
            case 3:
                return clientHandler.cAAdv;
            default:
                return new JFrame();
        }
    }

    int showDialog(int screen, String message, int type)
    {
        JFrame root;
        int status = -1;
        if (screen == 0)
        {
           root = clientHandler.login;
        }
        else if(screen == 1)
        {
            root = clientHandler.createAccout;
        }
        else if(screen == 2)
        {
            root = clientHandler.cAAdv;
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
