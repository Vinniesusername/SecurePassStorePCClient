package com.SecurePassStore.Client.PC;

import java.security.SecureRandom;

public class Tools //collection of functions needed else where in the program
{
    public  String byteArrayToHex(byte[] byteArray)
    {
        StringBuilder password = new StringBuilder();
        for(byte b: byteArray)
        {
            password.append(String.format("%02x", b));
        }
        return password.toString();
    }

    public byte[] hexStringToByteArray(String hexString)
    {
        int size = hexString.length();
        if(size == 0)
        {
            System.exit(-1);
        }
        byte[] byteArray = new byte[size /2];
        for(int i = 0; i < size; i+= 2)
        {
            byteArray[i/2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) +
                    Character.digit(hexString.charAt(i+1), 16));

        }

        return byteArray;
    }
    public byte[] makeSalt() //Generate 32 Bytes of salt. used in all encryption
    {
        SecureRandom saltGen = new SecureRandom();
        byte[] salt = new byte[32];
        saltGen.nextBytes(salt);
        return salt;
    }

    public boolean isSymbol(char c) //the list of symbols that are allowed in a password
    {
        boolean x = false;
        String symbols = "!@#$%^&*";
        if(symbols.indexOf(c) > -1)
            x = true;
        return x;
    }
}
