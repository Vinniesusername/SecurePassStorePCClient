package com.SecurePassStore.Client.PC;


public class LoginHandler //loginHandler handles operations for the Login Frame
{
    private static LoginHandler lh;

    private LoginHandler() {}

    private static final Client client = Client.getInstance();
    private static final Tools tool = new Tools();
    private static final EncryptionHandler eh = EncryptionHandler.getInstance();

    public static boolean addUser(String userName, char[] password, boolean localKey)
    {
        boolean added = false;
        if (client.contains(userName))
        {
            return added;
        }
        else
            {
            byte[] salt = tool.makeSalt();
            byte[] passwordHash = eh.getPasswordHash(password, salt);
            added = client.addUser(userName, tool.byteArrayToHex(passwordHash), tool.byteArrayToHex(salt), localKey);
            }
        return added;
    }

    public int checkUser(String name, char[] password) // 1 = true, 0 = false, 2 = username doesn't exist
    {
        int added = 0;
        byte[] salt = client.getCSalt(name).getBytes();
        byte[] passwordHash = eh.getPasswordHash(password, salt);


        String state = "3;" + client.sessionID + ";" + name + ";" + String.valueOf(passwordHash) + ";null";
        client.out.println(state);
        client.out.flush();
        String re = "";
        while(re != null && re.equals(""))
        {
            try
            {
                re = client.in.readLine();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        String[] parts = re.split(";", 5);
        if(Integer.parseInt(parts[0]) == 3)
        {
            added = 1;
        }
        else if(!Boolean.parseBoolean(parts[4]))
        {
            added = 2;
        }

        return added;
    }




    public static boolean validPasswordCheck(char[] password)
    {
        boolean valid = false;
        boolean numbers = false;
        boolean upper = false;
        boolean lower = false;
        boolean symbols = false;
        String passwordString = new String(password);

        for(int i =0; i < passwordString.length(); i++)
        {

            if(Character.isDigit(passwordString.charAt(i)))
                numbers = true;
            else if(Character.isUpperCase(passwordString.charAt(i)))
                upper = true;
            else if(Character.isLowerCase(passwordString.charAt(i)))
                lower = true;
            else if(tool.isSymbol(passwordString.charAt(i)))
                symbols = true;
            else
                return false;
        }
        if(numbers && upper && lower && symbols)
            valid = true;

        return valid;
    }

    public static LoginHandler getInstance()
    {
        if(lh == null)
        {
            lh = new LoginHandler();
        }

        return lh;
    }

}
