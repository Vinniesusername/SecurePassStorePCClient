package com.SecurePassStore.App;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class LoginHandler
{

    private static DataHandler dataHandler = DataHandler.getInstance();
    private static Tools tool = new Tools();

    public static boolean addUser(String userName, char[] password, boolean localKey)
    {
        boolean added = false;
        if (dataHandler.contains(userName))
        {
            return added;
        }
        else
            {
            byte[] salt = tool.makeSalt();
            byte[] passwordHash = getPasswordHash(password, salt);
            added = dataHandler.addUser(userName, tool.byteArrayToHex(passwordHash), tool.byteArrayToHex(salt), localKey);
            }
        return added;
    }

    public static int checkUser(String name, char[] password) // 1 = true, 0 = false, 2 = username doesnt exist
    {
        int matched = 1;
        if(dataHandler.contains(name)) {
            byte[] passwordBytes = getPasswordHash(password, tool.hexStringToByteArray(dataHandler.getSalt(name)));
            byte[] passwordRecord = tool.hexStringToByteArray(dataHandler.getPassword(name));

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
        //TODO: update hash algorithm to something more secure before opening repo
        byte[] hashedPassword = null;
        byte[] passwordBytes = String.valueOf(password).getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.reset();
            md.update(salt);
            hashedPassword = md.digest(passwordBytes); //get hashed bytes
        } catch (NoSuchAlgorithmException e)
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
