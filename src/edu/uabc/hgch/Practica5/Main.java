package edu.uabc.hgch.Practica5;

import java.util.*;

public class Main {
    public static void main(String []args) {   
		// Thread server = new Thread( new Server(10000) );
		Thread cliente = new Thread( new Cliente("HugoC","10.32.77.30",10000) );
		
		// server.start();
		try{Thread.sleep(3000);}catch(Exception e){};
		cliente.start();
    }
} 