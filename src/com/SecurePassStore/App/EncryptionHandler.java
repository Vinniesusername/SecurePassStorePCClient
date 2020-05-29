package com.SecurePassStore.App;

//TODO implement methods
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionHandler
{
    private byte[] masterPasswordBytes = null;
    private String localKey = "";
    private int iterations = 10000;
    private SecretKeySpec secretKeySpecKey;
    private static EncryptionHandler encryptionHandler;
    private  Cipher cipher;


    private EncryptionHandler(char[] password)
    {
        masterPasswordBytes = String.valueOf(password).getBytes();
        secretKeySpecKey = new SecretKeySpec(masterPasswordBytes, "AES");
        cipher = getCipher();

    }

    public static EncryptionHandler getInstance(char[] password)
    {
        if(encryptionHandler == null)
            encryptionHandler = new EncryptionHandler(password);
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


    public byte[] encryptPassword(byte[] password)
    {
        byte[] encrypted = null;
        try
        {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpecKey);
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
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpecKey);
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
