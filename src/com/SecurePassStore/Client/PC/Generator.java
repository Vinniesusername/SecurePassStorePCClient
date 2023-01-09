package com.SecurePassStore.Client.PC;

import java.security.SecureRandom;

public class Generator //Generator is a class to return generated passwords
{
    public String generateNewPassword(int n, boolean lower, boolean upper, boolean numbers, boolean symbols)
    {
        String lowerLetterList = "abcdefghijklmnopqrstuvwxyz";
        String upperLetterList = lowerLetterList.toUpperCase();
        String usablenumbers = "123456789";
        String usablesymbols = "!@#$%^&*";

        SecureRandom gen =  new SecureRandom();
        String valid = "";
        StringBuilder password = new StringBuilder();

        if(lower)
        {
            valid += lowerLetterList;
        }
        if(upper)
        {
            valid += upperLetterList;
        }
        if(numbers)
        {
            valid += usablenumbers;
        }
        if(symbols)
        {
            valid += usablesymbols;
        }


        for(int x = 0; x < n; x++)
        {
            int randomNumber = (gen.nextInt() & Integer.MAX_VALUE) % valid.length(); // & to zero out the sign bit
            char next = pickChar(valid, randomNumber);
            password.append(next);
        }
        return password.toString();
    }
    private static char pickChar(String validChars, int randomN)
    {
        return validChars.charAt(randomN);
    }

    public String generateLocalKey()
    {
        //TODO: pick key to used to encrypt passwords alongside master password
        return "";
    }

}
