package com.SecurePassStore.Client.PC;

import javax.swing.*;
import java.awt.*;

public class Gui //Gui handles all user interface classes and is used to pass information between frames.
{
    public static Gui handler = null;
    private final LogIn login = LogIn.getInstance();
    private final CreateAccout createAccout = CreateAccout.getInstance();
    private final MainLanding mainLanding = MainLanding.getInstance();
    private final CreateAccountAdv cAAdv = new CreateAccountAdv();
    private final NewEntry newEntry = NewEntry.getInstance();
    private char[] masterPassword; // storing master password in RAM, never to be sent to server!
    private String username = null;

    public String version = "SPS Version 0.3 java";


    private Gui() {}
    public static Gui getInstance()
    {
        if(handler == null)
            handler = new Gui();
        return handler;
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
            handler.masterPassword = info;
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
        if(handler.masterPassword == null)
        {
            //TODO: handle this case. throw exception?
            System.out.println("password not there");
        }
        return handler.masterPassword;
    }

    public String getUsername()
    {
        return handler.username;
    }




    public void startup()
    {
        showLogin();
        if(showDialog(handler.login,"This is only intended to be used for educational purposes. do not attempt to use this program " +
                "to handle your personal accounts. do you agree?", 1) != 0)
        {
            System.exit(0);
        }
    }

    void showLogin()
    {
        handler.login.setVisible(true);
        handler.login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void DisposeLogin()
    {
        handler.login.dispose();
    }

    void showCreateAccount()
    {
        handler.createAccout.setVisible(true);
        handler.createAccout.setAlwaysOnTop(true);
        handler.createAccout.requestFocus();
    }

    void ShowCreateAccountADV(CreateAccout caller)
    {
        handler.cAAdv.setVisible(true);
        handler.cAAdv.setAlwaysOnTop(true);
        handler.cAAdv.setCaller(caller);
    }

    void ShowCreateAccountADV(NewEntry caller)
    {

        handler.cAAdv.hideKey(true);
        handler.cAAdv.setVisible(true);
        handler.cAAdv.setAlwaysOnTop(true);
        handler.cAAdv.setCaller(caller);
    }


    void showNewEntry()
    {
        handler.newEntry.setVisible(true);
    }

    void disposeNewEntry()
    {

        handler.newEntry.dispose();
    }

    void disposeCreateAccountADV()
    {
        handler.cAAdv.setCaller(null);
        handler.cAAdv.hideKey(false);
        handler.cAAdv.dispose();


    }

    void DisposeCreate()
    {
        handler.createAccout.dispose();
    }

    void showMainLanding(String username)
    {
        handler.username = username.toLowerCase();
        handler.DisposeLogin();
        handler.mainLanding.setVisible(true);
        handler.mainLanding.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void DisposeMainLanding()
    {
        handler.mainLanding.dispose();
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
                return handler.login;
            case 1:
                return handler.createAccout;
            case 2:
                return handler.mainLanding;
            case 3:
                return handler.cAAdv;
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
           root = handler.login;
        }
        else if(screen instanceof CreateAccout)
        {
            root = handler.createAccout;
        }
        else if(screen instanceof CreateAccountAdv)
        {
            root = handler.cAAdv;
        }
        else if(screen instanceof MainLanding)
        {
            root = handler.mainLanding;
        }
        else if (screen instanceof NewEntry)
        {
            root = handler.newEntry;
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
