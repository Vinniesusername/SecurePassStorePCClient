package com.SecurePassStore.Client;

public class Client
{
    static LogIn login = new LogIn();
    static CreateAccout createAccout = new CreateAccout();

    public static void main(String[] args)
    {
        showLogin();
    }

    public static void showLogin()
    {
        login.setVisible(true);
    }

    public static void DisposeLogin()
    {
        login.dispose();
    }

    public static void showCreateAccount()
    {
        createAccout.setVisible(true);
        createAccout.toFront();
        createAccout.requestFocus();

    }

    public static void DisposeCreate()
    {
        createAccout.dispose();
    }

}
