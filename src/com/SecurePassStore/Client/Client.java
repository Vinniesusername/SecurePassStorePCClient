package com.SecurePassStore.Client;
import java.sql.*;

public class Client
{


    private static Client handler = null;


    private Client()
    {


    }


    boolean addUser(String user, String passwordHash, String salt, boolean localKey)
    {
        // magic method here server.addUser(args)

        return true;
    }

    String getPassword(String user)
    {
        String hashedPassword = null;
        // server.getPassword()
        return hashedPassword;
    }

     public static Client getInstance()
     {
         if(handler == null)
             handler = new Client();
         return handler;

     }

     public boolean  contains(String username)
     {
         // magic method server.contains()
        return true;
     }

     public String getSalt(String username)
     {
         // magic method server.getSalt()
         String salt = "";
         return salt;
     }

     public boolean addNewEntry(String userId, String username, String type, String encryptedPassword, String salt,  String url)
     {
         // magic server method
         boolean added = false;
         return added;
     }

     public String[] getEntry(String type, String username)
     {
         //magic method
         String[] password = new String[2];
         return password;
     }


}
