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

    public static boolean checkUser(String name, char[] password)
    {
        boolean matched = true;
        if(userInfo.containsKey(name))
        {
            byte[] passwordBytes = getPasswordHash(password, userInfo.get(name)[0]);

            for (int i = 0; i < userInfo.get(name)[1].length; i++)
            {
                if (userInfo.get(name)[1][i] != passwordBytes[i])
                {
                    matched = false;
                }
            }


        } else
            {
            System.out.println("username doesnt exisit");
            matched = false;
        }
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
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
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
}
