package com.SecurePassStore.App;

import java.security.SecureRandom;
import java.util.Scanner; // remove once GUI is up

class GeneratePassword
{


    static String Generate()
    {
        String lowerLetterList = "abcdefghijklmnopqrstuvwxyz";
        String upperLetterList = lowerLetterList.toUpperCase();
        String numbers = "123456789";
        String symbols = "!@#$%^&*-_";

        SecureRandom gen =  new SecureRandom();
        Scanner input = new Scanner(System.in);
        String valid = "";
        StringBuilder password = new StringBuilder();

        System.out.println("input number of char in password");
        int n = Integer.parseInt(input.next());
        System.out.println("1 for just low letters, 2 for upper, 3 to add numbers, 4 to add symbols");
        int mode = Integer.parseInt(input.next());
        switch(mode)
        {
            case 1:
                valid = lowerLetterList;
                break;
            case 2:
                valid =  lowerLetterList + upperLetterList;
                break;
            case 3:
                valid = lowerLetterList + upperLetterList + numbers;
                break;
            case 4:
                valid = lowerLetterList + upperLetterList + numbers + symbols;
                break;
            default:
                System.out.println("An Error Occurred");
        }

        for(int x = 0; x < n; x++)
        {
            int randomNumber = (gen.nextInt() & Integer.MAX_VALUE) % valid.length();
            char next = pickChar(valid, randomNumber);
            password.append(next);
        }


        return password.toString();
    }

    private static char pickChar(String validChars, int randomN)
    {
        return validChars.charAt(randomN);
    }


}
