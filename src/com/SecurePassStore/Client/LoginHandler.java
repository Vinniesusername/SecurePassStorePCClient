package com.SecurePassStore.Client;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class LoginHandler //loginHandler handles operations for the Login Frame
{
    private static LoginHandler lh;

    private LoginHandler()
    {

    }

    private static final Client client = Client.getInstance();
    private static final Tools tool = new Tools();
    private static final EncryptionHandler eh = EncryptionHandler.getInstance();

    public static boolean addUser(String userName, char[] password, boolean localKey)
    {
        boolean added = false;
        if (client.contains(userName))
        {
            return added;
        }
        else
            {
            byte[] salt = tool.makeSalt();
            byte[] passwordHash = eh.getPasswordHash(password, salt);
            added = client.addUser(userName, tool.byteArrayToHex(passwordHash), tool.byteArrayToHex(salt), localKey);
            }
        return added;
    }

    public static int checkUser(String name, char[] password) // 1 = true, 0 = false, 2 = username doesnt exist
    {
        int matched = 1;
        if(client.contains(name)) {
            byte[] passwordBytes = eh.getPasswordHash(password, tool.hexStringToByteArray(client.getSalt(name)));
            byte[] passwordRecord = tool.hexStringToByteArray(client.getPassword(name));

            for (int i = 0; i < passwordBytes.length; i++)
            {
                if (passwordRecord[i] != passwordBytes[i])
                {
                    matched = 0;
                    break;
                }
            }
        }
        else
            matched = 2;
        return matched;
    }




    public static boolean validPasswordCheck(char[] password)
    {
        boolean valid = false;
        boolean numbers = false;
        boolean upper = false;
        boolean lower = false;
        boolean symbols = false;
        String passwordString = new String(password);

        for(int i =0; i < passwordString.length(); i++)
        {

            if(Character.isDigit(passwordString.charAt(i)))
                numbers = true;
            else if(Character.isUpperCase(passwordString.charAt(i)))
                upper = true;
            else if(Character.isLowerCase(passwordString.charAt(i)))
                lower = true;
            else if(tool.isSymbol(passwordString.charAt(i)))
                symbols = true;
            else
                return false;
        }
        if(numbers && upper && lower && symbols)
            valid = true;

        return valid;
    }

    public static LoginHandler getInstance()
    {
        if(lh == null)
        {
            lh = new LoginHandler();
        }

        return lh;
    }

}
