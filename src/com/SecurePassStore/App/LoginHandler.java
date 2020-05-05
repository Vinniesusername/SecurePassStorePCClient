package com.SecurePassStore.App;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Array;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import com.SecurePassStore.App.DataHandler;


public class LoginHandler
{
    // this is temporary until database integration
    private static HashMap<String, byte[][]> userInfo = new HashMap<>();
    private static DataHandler dataHandler = DataHandler.getInstance();

    public static boolean addUser(String userName, char[] password)
    {
        boolean added = false;
        if (userInfo.containsKey(userName))
        {
            return added;
        }
        else
            {

            byte[][] details = new byte[2][1]; // holds salted password, and salt
            byte[] salt = getSalt();
            byte[] passwordHash = getPasswordHash(password, salt);
            details[0] = salt;
            details[1] = passwordHash;
            userInfo.put(userName, details);
            added = dataHandler.addUser(userName, byteArrayToHex(passwordHash), byteArrayToHex(salt));

            }
        return added;
    }

    public static int checkUser(String name, char[] password) // 1 = true, 0 = false, 2 = username doesnt exist
    {
        int matched = 1;
        if(userInfo.containsKey(name)) {
            byte[] passwordBytes = getPasswordHash(password, userInfo.get(name)[0]);

            for (int i = 0; i < userInfo.get(name)[1].length; i++)
            {
                if (userInfo.get(name)[1][i] != passwordBytes[i])
                {
                    matched = 0;
                }
            }
        }
        else
            matched = 2;
        return matched;
    }

    private static byte[] getSalt()
    {
        SecureRandom saltGen = new SecureRandom();
        byte[] salt = new byte[32];
        saltGen.nextBytes(salt);
        return salt;
    }

    private static byte[] getPasswordHash(char[] password, byte[] salt)
    {
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