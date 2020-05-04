package com.SecurePassStore.App;

import com.SecurePassStore.Client.Client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;


public class Security
{
    // this is temporary until database integration
    private static HashMap<String, byte[][]> userInfo = new HashMap<>();

    public static boolean addUser(String userName, char[] password)
    {

        if (userInfo.containsKey(userName))
        {
            return false;
        }
        else
            {

            byte[][] details = new byte[2][1]; // holds salted password, and salt
            byte[] salt = getSalt();
            byte[] passwordHash = getPasswordHash(password, salt);
            details[0] = salt;
            details[1] = passwordHash;
            userInfo.put(userName, details);
            System.out.println(byteArrayToHex(details[1]));

            }
        return true;
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
        byte[] salt = new byte[16];
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
        return new byte[0];
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
