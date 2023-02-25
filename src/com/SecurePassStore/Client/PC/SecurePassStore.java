package com.SecurePassStore.Client.PC;


import java.io.IOException;

public class SecurePassStore //entry point
{
    public static void main(String[] args) throws IOException
    {
        Client c = Client.getInstance();
        c.startup();

    }
}
