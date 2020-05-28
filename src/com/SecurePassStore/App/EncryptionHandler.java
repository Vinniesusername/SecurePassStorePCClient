package com.SecurePassStore.App;

//TODO implement methods
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionHandler
{
    private byte[] key = null;
    private String localKey = "";
    private SecretKeySpec secretKeySpec;
    private static EncryptionHandler encryptionHandler;

    private EncryptionHandler(byte[] k)
    {
        key = k;
        secretKeySpec = new SecretKeySpec(key, "AES" );
    }

    public static EncryptionHandler getInstance(byte[] key)
    {
        if(encryptionHandler == null)
            encryptionHandler = new EncryptionHandler(key);
        return encryptionHandler;

    }

    public byte[] encryptPassword(byte[] password)
    {
        byte[] encrypted = null;
        try
        {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encrypted = c.doFinal(password);
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
