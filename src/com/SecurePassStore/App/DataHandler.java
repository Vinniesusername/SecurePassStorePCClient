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

            final String sqlInsert = "INSERT INTO USERS (USERNAME, PASSWORD, SALT) VALUES (?,?,?)";
            PreparedStatement state = handler.db.prepareStatement(sqlInsert);
            state.setString(1, user);
            state.setString(2, passwordHash);
            state.setString(3, salt);
            state.executeUpdate();
            added = true;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            try{db.close();}catch (Exception e){System.out.println(e);}
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

     public void closeQuite()
     {
         try
         {
             db.close();
         }
         catch (Exception e)
         {
             System.out.println(e);
             
         }


     }

}
