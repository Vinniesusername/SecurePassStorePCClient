package com.SecurePassStore.App;

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
            System.out.println("username is already registered");
            return false;
        }
        else
            {

            byte[][] details = new byte[16][16]; // holds salted password, and salt
            byte[] salt = getSalt();
            byte[] passwordHash = getPasswordHash(password, salt);
            details[0] = salt;
            details[1] = passwordHash;
            userInfo.put(userName, details);

            }
        return true;
    }

    public static boolean checkUser(String name, char[] password)
    {
        if(userInfo.containsKey(name))
        {
            byte[] passwordBytes = getPasswordHash(password, userInfo.get(name)[0]);
            boolean matched = true;
            for (int i = 0; i < userInfo.get(name)[1].length; i++)
            {
                if (userInfo.get(name)[1][i] != passwordBytes[i])
                {
                    matched = false;
                }
            }
            return matched;

        } else
            {
            System.out.println("username doesnt exisit");
        }
        return false;
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
}
