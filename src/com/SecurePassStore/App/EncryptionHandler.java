package com.SecurePassStore.App;

//TODO implement methods
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;

public class EncryptionHandler
{
    private byte[] masterPasswordBytes = null;
    private String localKey = "";
    private int iterations = 10000;
    private SecretKeySpec key;
    private static EncryptionHandler encryptionHandler;
    private  Cipher cipher;
    byte[] salt = new byte[1];
    Generator g = new Generator();


    private EncryptionHandler(char[] password)
    {
        key = getKey(password);
        cipher = getCipher();


    }

    public static EncryptionHandler getInstance(char[] password)
    {
        if(encryptionHandler == null)
            encryptionHandler = new EncryptionHandler(password);
        return encryptionHandler;

    }

    public static SecretKeySpec getKey(char[] password)
    {
        byte[] key = null;
        SecretKeySpec keySpec = null;
        try
        {
            KeySpec k = new PBEKeySpec(password);
            SecretKeyFactory sf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = sf.generateSecret(k).getEncoded();
            keySpec = new SecretKeySpec(key, "AES");
        }
        catch(Exception e)
        {
            System.out.println(e);
            return keySpec;
        }
        return keySpec;
    }
    public static Cipher getCipher()
    {
        Cipher c;
        try
        {
            c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        }
        catch (Exception e)
        {
            System.out.println(e);
            c = null;
        }

        return c;

    }


    public byte[] encryptPassword(byte[] password)
    {
        byte[] encrypted = null;
        try
        {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(password);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return encrypted;
    }

    public byte[] decryptPassword(byte[] password)
    {
        byte[] decrypted = null;

        try
        {
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(password);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }


        return decrypted;
    }

    public byte[] encryptPassword(byte[] password, String accKey)
    {
        return null;
    }

    public byte[] decryptPassword(byte[] password, String accKey)
    {

        return null;
    }


}
