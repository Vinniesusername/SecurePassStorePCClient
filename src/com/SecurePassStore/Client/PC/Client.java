package com.SecurePassStore.Client.PC;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.SecureRandom;

public class Client // client handles requests to the server program.
{

    //client setup
    private static Client handler = null; // one object following singleton pattern.
    private static Gui guiHandler = Gui.getInstance();
    private String userName = null;


    //socket setup
    private static final String[] protocols = new String[]{"TLSv1.3"};
    private static final String[] ciphers = new String[]{"TLS_AES_256_GCM_SHA384", "TLS_AES_128_GCM_SHA256"}; //could add more?
    public SSLSocket conn;
    public BufferedReader in = null;
    public PrintWriter out = null;
    public SecureRandom sr = new SecureRandom();
    public int sessionID;




    //temp, remove this at some point
    public String ksPath = "D:\\SPS\\keystore\\spsclient";
    final String keypass = "testkey";
    final String kspass = "testclient";




    private Client()
    {
        sessionID = genSessionID();
    }

    public void startup()
    {
        try
        {
            handler.connect();
            guiHandler.startup();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    boolean addUser(String user, String passwordHash, String salt, boolean localKey) {
        // magic method here server.addUser(args)

        return true;
    }

    String getPassword(String user) {
        String hashedPassword = null;
        // server.getPassword()
        return hashedPassword;
    }

    public static Client getInstance() {
        if (handler == null)
            handler = new Client();
        return handler;
    }


    public boolean contains(String username)
    {
        boolean flag = false;
        try {
            String q = "1;" + sessionID + ";" + username + ";null;null";
            handler.out.println(q);
            handler.out.flush();
            String re = "";
            while (re.equals(""))
                re = handler.in.readLine();
            String[] parts = re.split(";", 5);
            if(parts[0].equals("null"))
                return false; //change this later
            if(Integer.parseInt(parts[0])== 1)
                flag = true;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return flag;
    }

    public String getCSalt(String username)
    {
        String salt = "";
        String state = "4;" + sessionID + ";" + username + ";null;null";
        handler.out.println(state);
        String r = "";
        while(r != null && r.equals(""))
        {
            try
            {
                r = handler.in.readLine();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        String[] parts = r.split(";", 5);
        salt = parts[2];

        return salt;
    }

    public boolean addNewEntry(String userId, String username, String type, String encryptedPassword, String salt, String url) {
        // magic server method
        boolean added = false;
        return added;
    }

    public String[] getEntry(String type, String username) {
        //magic method
        String[] password = new String[2];
        return password;
    }

    public int connect() throws IOException // connects handler to server returns a status code
    {
        if (handler.conn != null) {
            try {
                handler.conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int status = -1;
        try {
            //key store/manager
            KeyManagerFactory keyfact = KeyManagerFactory.getInstance("PKIX");
            KeyStore ks = KeyStore.getInstance("pkcs12");
            ks.load(new FileInputStream(ksPath), kspass.toCharArray());
            keyfact.init(ks, keypass.toCharArray());
            //trust store
            TrustManagerFactory trstfact = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trstfact.init(ks);

            //socket
            SSLContext context = SSLContext.getInstance(protocols[0]);
            context.init(keyfact.getKeyManagers(), trstfact.getTrustManagers(), null);
            SSLSocketFactory sockfact = context.getSocketFactory();

            handler.conn = (SSLSocket) sockfact.createSocket("localhost", 4422);

            //enable only the protocols and ciphers that i want used
            handler.conn.setEnabledProtocols(protocols);
            handler.conn.setEnabledCipherSuites(ciphers);

            //set input and output streams of socket
            handler.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(handler.conn.getOutputStream())));
            handler.in = new BufferedReader(new InputStreamReader(handler.conn.getInputStream()));

            // write to socket to test it
            // write to socket to test it
            int randomRequestId =  genSessionID();

            handler.out.println("0;" + String.valueOf(randomRequestId) + ";null;null");
            handler.out.println();
            handler.out.flush();

            //read input from server to make sure the connection is working as intended
            String serverResponse = "";
            while(serverResponse != null &&serverResponse.equals(""))
                serverResponse = handler.in.readLine();
            String[] parts = serverResponse.split(";", 5);
            if(Integer.parseInt(parts[0]) == 0)
            {
                status = 0;
                System.out.println("Connected to server");
            }

            //check for error
            if (handler.out.checkError()) {
                throw new Exception("java.io.printwriter error");
            }
            //set up input stream



        } catch (Exception e) {
            e.printStackTrace();
            status = -2;
        } finally {
            handler.conn.close();

        }

        return status; // 0 = good, -1 no exceptions but didn't get the response, -2 exception
    }

    public int genSessionID() //generates a random request id that is used for debugging and logging
    {
        //TODO not consisit with docs. change it
        int n = sr.nextInt();
        n = n % 1000000; // keep it positive and relatively small
        return n;
    }
}
