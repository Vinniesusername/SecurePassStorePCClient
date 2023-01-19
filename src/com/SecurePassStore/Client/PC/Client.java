package com.SecurePassStore.Client.PC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client // client handles requests to the server program.
{


    private static Client handler = null; //client instance for handling everything
    public Socket conn = null;
    public PrintWriter out = null;
    public BufferedReader in = null;

    private int port = 4422;


    private Client()
    {
        handler = Client.getInstance();
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

     public void connectToServer(String q) //q is the query being sent from client to server
     {
         String statement = "";



         try {
             handler.conn = new Socket(InetAddress.getLocalHost(), handler.port);
             handler.out = new PrintWriter(conn.getOutputStream(), true);
             handler.in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }

         try
         {
             handler.out.println(q);
             System.out.print(handler.in.readLine());
         }
         catch (Exception e)
         {
             System.out.println(e);
         }

     }
}
