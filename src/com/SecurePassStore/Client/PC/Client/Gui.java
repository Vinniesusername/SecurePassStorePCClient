package com.SecurePassStore.Client.PC.Client;

import javax.swing.*;
import java.awt.*;

public class Gui //Gui handles all user interface classes and is used to pass information between frames.
{
    private static Gui guiHandler;
    private final LogIn login = LogIn.getInstance();
    private final CreateAccout createAccout = CreateAccout.getInstance();
    private final MainLanding mainLanding = MainLanding.getInstance();
    private final CreateAccountAdv cAAdv = new CreateAccountAdv();
    private final NewEntry newEntry = NewEntry.getInstance();
    private char[] masterPassword; // storing master password in RAM, never to be sent to server!
    private String username = null;


    private Gui()
    {


    }
    public static Gui getClientInstance()
    {
        if(guiHandler == null)
            guiHandler = new Gui();
        return guiHandler;
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
            guiHandler.masterPassword = info;
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
        if(guiHandler.masterPassword == null)
        {
            //TODO: handle this case. throw exception?
            System.out.println("password not there");
        }
        return guiHandler.masterPassword;
    }

    public String getUsername()
    {
        return guiHandler.username;
    }




    public void startup()
    {
        showLogin();
        if(showDialog(guiHandler.login,"This is only intended to be used for educational purposes. do not attempt to use this program " +
                "to handle your personal accounts. do you agree?", 1) != 0)
        {
            System.exit(0);
        }
    }

    void showLogin()
    {
        guiHandler.login.setVisible(true);
        guiHandler.login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void DisposeLogin()
    {
        guiHandler.login.dispose();
    }

    void showCreateAccount()
    {
        guiHandler.createAccout.setVisible(true);
        guiHandler.createAccout.setAlwaysOnTop(true);
        guiHandler.createAccout.requestFocus();
    }

    void ShowCreateAccountADV(CreateAccout caller)
    {
        guiHandler.cAAdv.setVisible(true);
        guiHandler.cAAdv.setAlwaysOnTop(true);
        guiHandler.cAAdv.setCaller(caller);
    }

    void ShowCreateAccountADV(NewEntry caller)
    {

        guiHandler.cAAdv.hideKey(true);
        guiHandler.cAAdv.setVisible(true);
        guiHandler.cAAdv.setAlwaysOnTop(true);
        guiHandler.cAAdv.setCaller(caller);
    }


    void showNewEntry()
    {
        guiHandler.newEntry.setVisible(true);
    }

    void disposeNewEntry()
    {

        guiHandler.newEntry.dispose();
    }

    void disposeCreateAccountADV()
    {
        guiHandler.cAAdv.setCaller(null);
        guiHandler.cAAdv.hideKey(false);
        guiHandler.cAAdv.dispose();


    }

    void DisposeCreate()
    {
        guiHandler.createAccout.dispose();
    }

    void showMainLanding(String username)
    {
        guiHandler.username = username.toLowerCase();
        guiHandler.DisposeLogin();
        guiHandler.mainLanding.setVisible(true);
        guiHandler.mainLanding.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void DisposeMainLanding()
    {
        guiHandler.mainLanding.dispose();
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
                return guiHandler.login;
            case 1:
                return guiHandler.createAccout;
            case 2:
                return guiHandler.mainLanding;
            case 3:
                return guiHandler.cAAdv;
            default:
                return new JFrame();
        }
    }

    int showDialog(JFrame screen, String message, int type)
    {
        JFrame root;
        int status = -1;
        if (screen instanceof LogIn)
        {
           root = guiHandler.login;
        }
        else if(screen instanceof CreateAccout)
        {
            root = guiHandler.createAccout;
        }
        else if(screen instanceof CreateAccountAdv)
        {
            root = guiHandler.cAAdv;
        }
        else if(screen instanceof MainLanding)
        {
            root = guiHandler.mainLanding;
        }
        else if (screen instanceof NewEntry)
        {
            root = guiHandler.newEntry;
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
