package edu.uabc.hgch.test;

import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
    private static List<ClientHandler> clientes = new ArrayList<>();
    private ServerSocket s_socket;
    private int port;
    private static TreeMap<String,String> alumnos = new TreeMap<String,String>();
    
    private static TreeSet<Usuario> usuarios = new TreeSet<>();
    private static final String ARCHIVO_USUARIOS = "Usuario2.txt";
    
    public Server(int port) {
        this.port = port;
        alumnos.put("1229948", "BETANCOURT JUAREZ EDGAR ALBERTO");
        
        deserializarUsuarios();
    }
    
    public void run() {
        ClientHandler tmp;
        try {
            s_socket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port +" "+s_socket.getInetAddress().getLocalHost().getHostAddress());

            while (true) {
                Socket clientSocket = s_socket.accept();
                clientSocket.setKeepAlive(false);
                tmp = new ClientHandler(clientSocket,clientes,alumnos,usuarios);	
                clientes.add(tmp);
                System.out.println("Nuevo cliente conectado: " + clientSocket);
                new Thread(tmp).start();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    public static synchronized void serializarUsuarios() {
        try {
            File archivo = new File(ARCHIVO_USUARIOS);
            
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
            
            FileOutputStream fos = new FileOutputStream(archivo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(usuarios);
            oos.close();
            fos.close();
            
        } catch (IOException e) {
            System.out.println("[SERVIDOR]: ERROR AL SERIALIZAR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    private static void deserializarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("[SERVIDOR]: Error al crear el archivo: " + e.getMessage());
            }
            return;
        }

        if (archivo.length() == 0) {
            return;
        }

        try (FileInputStream fis = new FileInputStream(ARCHIVO_USUARIOS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            usuarios = (TreeSet<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[SERVIDOR]: ERROR AL DESERIALIZAR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}