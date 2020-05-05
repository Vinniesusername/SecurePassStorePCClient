package com.SecurePassStore.App;
import java.sql.*;

class DataHandler
{

    public Connection db;
    private static DataHandler handler = null;
    private String user = "vinnie";
    private String password = "testpass";

    private DataHandler()
    {

        db = connectToData();

    }

    private Connection connectToData()
    {
        Connection con = null;

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost/sps", user, password);
            System.out.println("connected to db");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.exit(-1);
        }

        return con;
    }

    boolean addUser(String user, String passwordHash, String salt)
    {
        boolean added = false;
        try
        {
            Statement state = handler.db.createStatement();
            String sql = "insert into users " +
                    "(username, password, salt) " +
                    "values ( '" + user + "' , '" + passwordHash + "' , '" + salt + "')";
            state.execute(sql);
            added = true;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }


        return added;
    }

    boolean checkUser(String user, String password)
    {
        return true;
    }
     public static  DataHandler getInstance()
     {
         if(handler == null)
             handler = new DataHandler();
         return handler;

     }

}
