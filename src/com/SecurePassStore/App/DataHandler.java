package com.SecurePassStore.App;
import java.sql.*;

public class DataHandler
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

        return con;
    }

    boolean addUser(String user, String passwordHash, String salt, boolean localKey)
    {
        boolean added = false;
        try
        {
            final String sqlInsert = "INSERT INTO USERS (USERNAME, PASSWORD, SALT, LOCALKEY) VALUES (?,?,?,?)";
            PreparedStatement state = handler.db.prepareStatement(sqlInsert);
            state.setString(1, user);
            state.setString(2, passwordHash);
            state.setString(3, salt);
            state.setBoolean(4, localKey);
            state.executeUpdate();
            added = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
            e.printStackTrace();
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
             e.printStackTrace();
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
            e.printStackTrace();
         }
         return salt;
     }

     public boolean addNewEntry(String userId, String username, String type, String encryptedPassword, String salt,  String url)
     {
         boolean added = false;
         try
         {
             final String sqlInsert = "INSERT INTO ENTRYS (USERID, USERNAME, TYPE, PASSWORD, SALT,  URL) VALUES (?,?,?,?,?,?)";
             PreparedStatement state = handler.db.prepareStatement(sqlInsert);
             state.setString(1, userId);
             state.setString(2, username);
             state.setString(3, type);
             state.setString(4, encryptedPassword);
             state.setString(5, salt);
             state.setString(6, url);
             state.executeUpdate();
             added = true;

         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

         return added;
     }

     public String[] getEntry(String type, String username)
     {
         String[] password = new String[2];

         try {
             final String sqlInsert = "SELECT password, salt FROM ENTRYS WHERE TYPE = ? AND USERID = ?";
             PreparedStatement state = handler.db.prepareStatement(sqlInsert);
             state.setString(1, type);
             state.setString(2, username);
             ResultSet rs = state.executeQuery();
             rs.next();
             password[0] = rs.getString(1);
             password[1] = rs.getString(2);
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         return password;
     }

     public void closeQuite()
     {
         try
         {
             db.close();
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
     }

}
