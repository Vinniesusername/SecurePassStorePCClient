package com.SecurePassStore.App;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class LoginHandler
{

    private static DataHandler dataHandler = DataHandler.getInstance();

    public static boolean addUser(String userName, char[] password, boolean localKey)
    {
        boolean added = false;
        if (dataHandler.contains(userName))
        {
            return added;
        }
        else
            {
            byte[] salt = makeSalt();
            byte[] passwordHash = getPasswordHash(password, salt);
            added = dataHandler.addUser(userName, byteArrayToHex(passwordHash), byteArrayToHex(salt), localKey);
            }
        return added;
    }

    public static int checkUser(String name, char[] password) // 1 = true, 0 = false, 2 = username doesnt exist
    {
        int matched = 1;
        if(dataHandler.contains(name)) {
            byte[] passwordBytes = getPasswordHash(password, hexStringToByteArray(dataHandler.getSalt(name)));
            byte[] passwordRecord = hexStringToByteArray(dataHandler.getPassword(name));

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

    public static byte[] makeSalt()
    {
        SecureRandom saltGen = new SecureRandom();
        byte[] salt = new byte[32];
        saltGen.nextBytes(salt);
        return salt;
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

    private static String byteArrayToHex(byte[] byteArray)
    {
        StringBuilder password = new StringBuilder();
        for(byte b: byteArray)
        {
            password.append(String.format("%02x", b));
        }
        return password.toString();
    }

    private static byte[] hexStringToByteArray(String hexString)
    {
        int size = hexString.length();
        if(size == 0)
        {
           System.exit(-1);
        }
        byte[] byteArray = new byte[size /2];
        for(int i = 0; i < size; i+= 2)
        {
            byteArray[i/2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) +
                    Character.digit(hexString.charAt(i+1), 16));

        }

        return byteArray;
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
            else if(isSymbol(passwordString.charAt(i)))
                symbols = true;
            else
                return false;
        }
        if(numbers && upper && lower && symbols)
            valid = true;

        return valid;
    }


    private static boolean isSymbol(char c)
    {
        boolean x = false;
        String symbols = "!@#$%^&*";
        if(symbols.indexOf(c) > -1)
            x = true;
        return x;
    }
}
