package edu.uabc.hgch.Taller6;

/*
javac -d bin src/taller6/*.java
java -cp bin taller6.Main
*/

import java.util.*;

public class Main
{
    public static void main(String []args)
    {   
		// Thread server = new Thread( new Server(10000) );
		Thread cliente = new Thread( new Cliente("HugoC","192.168.1.4",10000) );
		
		// server.start();
		try{Thread.sleep(3000);}catch(Exception e){};
		cliente.start();
    }
}