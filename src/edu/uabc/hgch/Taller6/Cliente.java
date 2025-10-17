package edu.uabc.hgch.Taller6;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Cliente implements Runnable
{
    private String serverAddress;
    private int port;
    private String username;
	private boolean correr;
	private Socket socket;
	
    public Cliente(String username,String serverAddress, int port)
    {
        this.serverAddress = serverAddress;
        this.port = port;
		this.username = username;
        this.correr = true;
    }
    
    public void run() 
    {  
        //try-with-resources
        //LOS ELEMENTOS ENTRE ( ... ) SE CIERRAN AL TERMINAR EL TRY
        //Socket,BufferedReader,PrintWriter
        try ( Socket socket = new Socket(serverAddress, port);
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) 
        {
            
            this.socket = socket;
            System.out.println("Conectado al servidor.");
            try
            {                
                //SE ENVIA AL SERVIDOR EL USERNAME				
                out.println("username:"+username);
            }catch(Exception er)
            {
                correr= false;     
                er.printStackTrace();               
                try{socket.close();}catch(IOException f){}
            }
                
            // THREAD PARA LECTURA DE MENSAJES DEL SERVIDOR
            verMensajes(serverIn).start();

            // ENVIO DE MENSAJES AL SERVIDOR	
            enviarMensajes(out).start();
            
            //MANTENER LA CONEXIÓN VIVA
            while(socket.isConnected() && correr)
                try{Thread.sleep(5000);}catch(Exception e){};
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
	
    private Thread verMensajes(BufferedReader param_serverIn)
    {
        return new Thread(() -> 
        {
            Socket socket = this.socket;
            BufferedReader serverIn = param_serverIn;
            String serverMessage;
            try 
            {
                //SE MUESTRA EL MENSAJE RECIBIDO EN EL CHAT
                while ((serverMessage = serverIn.readLine()) != null ) 
                    System.out.println(serverMessage);			
            } 
            catch (IOException e) 
            {
                e.printStackTrace();System.out.println("Conexión cerrada.");
                try{socket.close();}catch(IOException f){}
            }
        });
    }

    private Thread enviarMensajes(PrintWriter param_out)
    {
        return new Thread(() -> 
        {
            Socket socket = this.socket;
            PrintWriter out = param_out;
            Scanner input = new Scanner(System.in);
            String userInput = null;
            //boolean correr = true;
            while(correr)
            {
                //CAPTURA DE MENSAJE POR EL USUARIO
                userInput = input.nextLine();
                //SE ENVIA AL SERVIDOR
                out.println(userInput);             
            }
        });
    }
    
}
