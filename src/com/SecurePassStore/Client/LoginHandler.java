package com.SecurePassStore.Client;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class LoginHandler
{

    private static final Client client = Client.getInstance();
    private static Tools tool = new Tools();

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
            byte[] passwordHash = getPasswordHash(password, salt);
            added = client.addUser(userName, tool.byteArrayToHex(passwordHash), tool.byteArrayToHex(salt), localKey);
            }
        return added;
    }

    public static int checkUser(String name, char[] password) // 1 = true, 0 = false, 2 = username doesnt exist
    {
        int matched = 1;
        if(client.contains(name)) {
            byte[] passwordBytes = getPasswordHash(password, tool.hexStringToByteArray(client.getSalt(name)));
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


    private static byte[] getPasswordHash(char[] password, byte[] salt)
    {
        int iterations = 10000; //TODO: may need to be adjusted before it's secure. look into it later
        int keyLength = 512;
        byte[] hashedPassword = null;

        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret(spec);
            hashedPassword = key.getEncoded( );
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {

            System.exit(1);
        }
        return hashedPassword;
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

}
