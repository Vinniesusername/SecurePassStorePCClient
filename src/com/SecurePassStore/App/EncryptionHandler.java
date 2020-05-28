package com.SecurePassStore.App;
//TODO implement methods
public class EncryptionHandler
{
    private byte[] key = null;
    private String localKey = "";

    public EncryptionHandler(byte[] k)
    {
        key = k;
    }

    public byte[] encryptPassword(byte[] password)
    {
        return null;
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
