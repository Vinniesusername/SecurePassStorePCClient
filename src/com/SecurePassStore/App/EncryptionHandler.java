package com.SecurePassStore.App;

//TODO implement methods
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionHandler
{
    private char[] masterPassword = null;
    private String localKey = "";
    private int iterations = 10000;
    private SecretKeySpec factoryKey;
    private static EncryptionHandler encryptionHandler;
    private  Cipher cipher;


    private EncryptionHandler(char[] password, byte[] salt, int iterations)
    {
        masterPassword = password;
        factoryKey = getSercretKey(masterPassword, new byte[16], iterations);
        cipher = getCipher();

    }

    public static EncryptionHandler getInstance(char[] password, byte[] salt, int iterations)
    {
        if(encryptionHandler == null)
            encryptionHandler = new EncryptionHandler(password, salt, iterations);
        return encryptionHandler;

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

    public static SecretKeySpec getSercretKey(char[] masterPassword, byte[] salt, int iterations)
    {
        SecretKeyFactory factoryKey;
        SecretKeySpec spec;
        try
        {
            factoryKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey keyGen = factoryKey.generateSecret(new PBEKeySpec(
                    masterPassword, salt, iterations, 128));
            spec = new SecretKeySpec(keyGen.getEncoded(), "AES");
        }
        catch (Exception e)
        {
            System.out.println(e);
            spec = null;
        }

        return spec;
    }

    public byte[] encryptPassword(byte[] password, SecretKeySpec factoryKey)
    {
        byte[] encrypted = null;
        try
        {
            cipher.init(Cipher.ENCRYPT_MODE, factoryKey);
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


        return null;
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
