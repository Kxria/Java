package edu.uabc.hgch.Taller6;
/*
javac -d bin src/taller6/*.java
java -cp bin taller6.Main
*/


import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable { 
    private List<ClientHandler> clientes;
	private TreeMap<String,String> alumnos;
    private Socket socket;
	private String username;
    private BufferedReader in;

    public ClientHandler(Socket socket,List<ClientHandler> clientes,TreeMap<String,String> alumnos) {
		this.alumnos = alumnos;
        this.clientes = clientes;
        this.socket = socket;
        this.username = "";
        
        try {
            //SE CREA UN BUFFER PARA LA RECEPCION DE MENSAJES DEL SERVIDOR
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String message;

        try {
            //ESPERA PARA RECEPCION DE MENSAJES
            while (socket.isConnected() && (message = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + message);

                if(message.startsWith("username:")) {
                    String tmp = message.replace("username:","");
                    username = tmp;
                } else if(message.startsWith("matricula:")) {
					String []param = message.split(":");
					
                    if(param.length >= 2) {
						String nombre = alumnos.get(param[1]);
					
                        if(nombre != null) {
							PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
							//ENVIAR MENSAJE
							out.println("[SERVIDOR]: Tu nombre es "+nombre);
							
						}
					}
				} else { //DISTRIBUCION DE MENSAJES PARA LOS DEMAS CLIENTES
                    distribuirMensaje(socket, message);
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
            try{socket.close();} catch (IOException f) {}
            System.out.println("Cliente desconectado: " + socket);
        }
    }

    private void distribuirMensaje(Socket sender, String message) 
    {
        Socket socket;
        for (ClientHandler client : clientes) 
        {
            socket = client.get_Socket();
            if (socket != sender || true) 
            {
                try 
                {
                    //SE CREA UN BUFFER PARA TRANSMISION DE MENSAJES PARA CADA CLIENTE
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    //ENVIAR MENSAJE
                    out.println("["+username+"]: "+message);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
    }
	
	public Socket get_Socket()
	{
		return socket;
	}
	
	public String get_username()
	{
		return new String(username);
	}
}