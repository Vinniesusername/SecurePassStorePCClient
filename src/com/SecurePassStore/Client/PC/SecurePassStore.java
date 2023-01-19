package com.SecurePassStore.Client.PC;


public class SecurePassStore //entry point
{
    public static void main(String[] args)
    {

        Client hand = Client.getInstance();
        hand.connectToServer("null");

    }
}
