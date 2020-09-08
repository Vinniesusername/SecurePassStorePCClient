package com.SecurePassStore.Client;

import javax.swing.*;
import java.awt.*;

public class Client
{
    private static  Client clientHandler;
    private LogIn login = LogIn.getInstance();
    private CreateAccout createAccout = CreateAccout.getInstance();
    private MainLanding mainLanding = MainLanding.getInstance();
    private CreateAccountAdv cAAdv = new CreateAccountAdv();
    private NewEntry newEntry = NewEntry.getInstance();
    private char[] masterPassword;
    private String username = null;


    private Client()
    {


    }
    public static Client getClientInstance()
    {
        if(clientHandler == null)
            clientHandler = new Client();
        return clientHandler;
    }

    void passInfo(String[] info, JFrame frame)
    {
        if( frame instanceof CreateAccout)
        {
            createAccout.getInfo(info);
        }
        else if( frame instanceof NewEntry)
        {
            newEntry.getInfo(info);
        }

    }

    void passInfo(char[] info, JFrame frame)
    {
        if(frame instanceof MainLanding)
        {
            mainLanding.getInfo(info);
        }
        else if(frame instanceof  LogIn)
        {
            clientHandler.masterPassword = info;
        }
    }


    void actOnInfo(JFrame frame)
    {
        if(frame instanceof CreateAccout)
        {
                createAccout.actOnInfo();
        }
        else if(frame instanceof NewEntry)
        {
            newEntry.actOnInfo();
        }

    }

    public char[] getMasterPassword()
    {
        if(clientHandler.masterPassword == null)
        {
            //TODO: handle this case. throw exception?
            System.out.println("password not there");
        }
        return clientHandler.masterPassword;
    }

    public String getUsername()
    {
        return clientHandler.username;
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

    void showLogin()
    {
        clientHandler.login.setVisible(true);
        clientHandler.login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void DisposeLogin()
    {
        clientHandler.login.dispose();
    }

    void showCreateAccount()
    {
        clientHandler.createAccout.setVisible(true);
        clientHandler.createAccout.setAlwaysOnTop(true);
        clientHandler.createAccout.requestFocus();
    }

    void ShowCreateAccountADV(CreateAccout caller)
    {
        clientHandler.cAAdv.setVisible(true);
        clientHandler.cAAdv.setAlwaysOnTop(true);
        clientHandler.cAAdv.setCaller(caller);
    }

    void ShowCreateAccountADV(NewEntry caller)
    {

        clientHandler.cAAdv.hideKey(true);
        clientHandler.cAAdv.setVisible(true);
        clientHandler.cAAdv.setAlwaysOnTop(true);
        clientHandler.cAAdv.setCaller(caller);
    }


    void showNewEntry()
    {
        clientHandler.newEntry.setVisible(true);
    }

    void disposeNewEntry()
    {

        clientHandler.newEntry.dispose();
    }

    void disposeCreateAccountADV()
    {
        clientHandler.cAAdv.setCaller(null);
        clientHandler.cAAdv.hideKey(false);
        clientHandler.cAAdv.dispose();


    }

    void DisposeCreate()
    {
        clientHandler.createAccout.dispose();
    }

    void showMainLanding(String username)
    {
        clientHandler.username = username.toLowerCase();
        clientHandler.DisposeLogin();
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
        else if(screen == 3)
        {
            root = clientHandler.mainLanding;
        }
        else if (screen == 4)
        {
            root = clientHandler.newEntry;
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
