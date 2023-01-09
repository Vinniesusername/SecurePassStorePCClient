package com.SecurePassStore.Client.PC.Client;

//TODO implement methods

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class EncryptionHandler //encryptionHandler is the class that handles all hashing and encryption
{
    private byte[] masterPasswordBytes = null;
    private String localKey = "";
    public int iterations;

    public int keyLength;

    private SecretKeySpec key;
    private static EncryptionHandler encryptionHandler;
    private  Cipher cipher;
    private static final LoginHandler lh = LoginHandler.getInstance();
    Generator g = new Generator();
    private static char[] masterPassword = null;
    private static final Tools tool = new Tools();

    public void startUp(char[] mp)
    {
        masterPassword = mp;

    }

    private EncryptionHandler()
    {
        cipher = getCipher();
        iterations = 10000;
        keyLength = 512;

    }

    public static EncryptionHandler getInstance()
    {
        if(encryptionHandler == null)
        {
            encryptionHandler = new EncryptionHandler();


        }
        return encryptionHandler;


    }

    public byte[] getPasswordHash(char[] password, byte[] salt)
    {
        //TODO: may need to be adjusted before it's secure. look into it later

        byte[] hashedPassword = null;

        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret(spec);
            hashedPassword = key.getEncoded( );
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {

            System.exit(1);
        }
        return hashedPassword;
    }

    public SecretKeySpec getKey(byte[] salt)
    {
        byte[] key;
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
        byte[] salt = tool.makeSalt();
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

    public byte[] decryptPassword(byte[] password, byte[] salt)
    {
        byte[] decrypted = null;

        SecretKeySpec key = getKey(salt);

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
