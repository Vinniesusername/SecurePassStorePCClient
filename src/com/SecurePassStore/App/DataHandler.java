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
            System.out.println(e + "connect to db");
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
            System.out.println(e + "|| add user");
        }
        finally
        {
            //do something
        }


        return added;
    }

    String getPassword(String user)
    {
        String hashedPassword = null;
        String q = "SELECT password FROM users WHERE username= ?";

       try
       {
           PreparedStatement state = db.prepareStatement(q);
           state.setString(1, user);
           ResultSet results = state.executeQuery();
           results.next();
           hashedPassword = results.getString(1);
       }
       catch(Exception e)
        {
            System.out.println(e + " password");
        }


        return hashedPassword;
    }

     public static  DataHandler getInstance()
     {
         if(handler == null)
             handler = new DataHandler();
         return handler;

     }

     public boolean  contains(String username)
     {
         //TODO: this function
         boolean contains =  false;
         String sql = "Select 1 from users where username = ?";
         try
         {
            PreparedStatement state = db.prepareStatement(sql);
            state.setString(1, username);
            ResultSet results = state.executeQuery();
            contains = results.next();

         }
         catch (Exception e)
         {
             System.out.println(e + "||| contains");
         }
         return contains;
     }

     public String getSalt(String username)
     {
         String salt = "";
         String q = "SELECT salt FROM users WHERE username=?";
         try
         {
             PreparedStatement state = db.prepareStatement(q);
             state.setString(1, username);
             ResultSet results = state.executeQuery();
             results.next();
             salt = results.getString(1);

         }
         catch (Exception e)
         {
             System.out.println(e + " salt") ;
         }
         return salt;
     }


     public void closeQuite()
     {
         try
         {
             db.close();
         }
         catch (Exception e)
         {
             System.out.println(e + " close");
         }
     }

}
