package com.SecurePassStore.Sever;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConHandler
{
    public Connection mysqldb; //connection to database
    private static ConHandler handler = null;



    private ConHandler()
    {
        String user = "vinnie";
        String password = "testpass";
        mysqldb = connectToData(user, password);
    }

    private Connection connectToData(String user, String pass)
    {
        Connection con = null;

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost/sps", user, pass);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

        return con;
    }

    public static ConHandler getInstance()
    {
        if (handler == null)
            handler = new ConHandler();
        return handler;
    }

    public void closeQuite()
    {
        try
        {
            handler.mysqldb.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
