package com.SecurePassStore.App;

//TODO implement methods
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class EncryptionHandler
{
    private byte[] masterPasswordBytes = null;
    private String localKey = "";
    private static int iterations = 10000;
    private SecretKeySpec key;
    private static EncryptionHandler encryptionHandler;
    private  Cipher cipher;
    private static LoginHandler lh = new LoginHandler();
    Generator g = new Generator();
    private static char[] masterPassword = null;

    public void startUp(char[] mp)
    {
        masterPassword = mp;

    }


    private EncryptionHandler()
    {
        cipher = getCipher();

    }

    private static byte[] makeSalt()
    {
        SecureRandom saltGen = new SecureRandom();
        byte[] salt = new byte[32];
        saltGen.nextBytes(salt);
        return salt;
    }

    public static EncryptionHandler getInstance()
    {
        if(encryptionHandler == null)
            encryptionHandler = new EncryptionHandler();
        return encryptionHandler;

    }

    public static SecretKeySpec getKey(byte[] salt)
    {
        byte[] key = String.valueOf(masterPassword).getBytes();
        SecretKeySpec keySpec = null;
        try
        {
            KeySpec k = new PBEKeySpec(masterPassword, salt, iterations, 128);
            SecretKeyFactory sf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = sf.generateSecret(k).getEncoded();
            keySpec = new SecretKeySpec(key, "AES");
        }
        catch(Exception e)
        {
            System.out.println(e);

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


    public byte[][] encryptPassword(byte[] password)
    {
        byte[][] encrypted = new byte[2][1];
        byte[] salt = makeSalt();
        SecretKeySpec key = getKey(salt);
        try
        {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted[0] = cipher.doFinal(password);
            encrypted[1] = salt;
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
