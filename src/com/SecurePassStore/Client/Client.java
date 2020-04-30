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
    }

    static void showLogin()
    {
        login.setVisible(true);
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

}
