package edu.uabc.hgch.Taller6;
/*
javac -d bin src/taller6/*.java
java -cp bin taller6.Main
*/

import java.util.concurrent.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable
{ 
	//LISTA PARA ALMACENAR LOS CLIENTES CONECTADOS
    private static List<ClientHandler> clientes = new ArrayList<>();
    private ServerSocket s_socket;
    private int port;
	private static TreeMap<String,String> alumnos = new TreeMap<String,String>();
    
    public Server(int port)
    {
        this.port = port;
		alumnos.put("1229948", "BETANCOURT JUAREZ EDGAR ALBERTO");

    }
    
    public void run()
    {
        ClientHandler tmp;
        try 
        {
			//SERVIDOR
			//ESTABLECER PUERTO QUE ESCUCHARA PETICIONES
            s_socket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port +" "+s_socket.getInetAddress().getLocalHost().getHostAddress());

            while (true) 
            {
				//ESPERAR CONEXION CON CLIENTE
                Socket clientSocket = s_socket.accept();
				clientSocket.setKeepAlive(false);
				//CREAR MANEJADOR DE CLIENTE
				tmp = new ClientHandler(clientSocket,clientes,alumnos);	
				//AGREGARLO A LA LISTA DE CLIENTES CONECTADOS
                clientes.add(tmp);
                System.out.println("Nuevo cliente conectado: " + clientSocket);
				//CREAR THREAD PARA MANEJAR LA COMUNICACION ENTRE
				//EL SERVIDOR Y EL CLIENTE
                new Thread(tmp).start();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }        
    }
}