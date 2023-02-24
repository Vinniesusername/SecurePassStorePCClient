package com.SecurePassStore.Client.PC;


import java.io.IOException;

public class SecurePassStore //entry point
{
    public static void main(String[] args) throws IOException
    {
        Gui g = Gui.getInstance();
        g.startup();

    }
}
