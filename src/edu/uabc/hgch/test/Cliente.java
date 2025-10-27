package edu.uabc.hgch.test;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Cliente implements Runnable {
    private String serverAddress;
    private int port;
    private String username;
    private boolean correr;
    private Socket socket;
    private PrintWriter out;
    private boolean sesionIniciada;
    
    public Cliente(String username, String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.username = username;
        this.correr = true;
        this.sesionIniciada = false;
    }
    
    public void run() {  
        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) 
        {
            this.socket = socket;
            this.out = out;
            System.out.println("Conectado al servidor.");
            
            try {                
                out.println("username:" + username);
            } catch(Exception er) {
                correr = false;     
                er.printStackTrace();               
                try{socket.close();}catch(IOException f){}
            }
                
            verMensajes(serverIn).start();

            mostrarMenu().start();
            
            while(socket.isConnected() && correr)
                try{Thread.sleep(5000);}catch(Exception e){};
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Thread verMensajes(BufferedReader param_serverIn) {
        return new Thread(() -> {
            Socket socket = this.socket;
            BufferedReader serverIn = param_serverIn;
            String serverMessage;
            try {
                while ((serverMessage = serverIn.readLine()) != null) {
                    System.out.println(serverMessage);
                    
                    if(serverMessage.contains("Inicio de sesion exitoso")) {
                        sesionIniciada = true;
                    } else if(serverMessage.contains("Sesion cerrada") || 
                             serverMessage.contains("Cuenta eliminada")) {
                        sesionIniciada = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Conexion cerrada.");
                try{socket.close();}catch(IOException f){}
            }
        });
    }

    private Thread mostrarMenu() {
        return new Thread(() -> {
            Scanner input = new Scanner(System.in);
            
            while(correr){
                try {
                    Thread.sleep(500);
                    
                    if(!sesionIniciada) {
                        mostrarMenuPrincipal(input);
                    } else {
                        mostrarMenuUsuario(input);
                    }
                } catch(InterruptedException e) {
                    break;
                }
            }
        });
    }
    
    private void mostrarMenuPrincipal(Scanner input) {
        System.out.println("\nMenu principal");
        System.out.println("1) Iniciar sesion");
        System.out.println("2) Registrar");
        System.out.println("3) Salir");
        System.out.print("Seleccione una opcion: ");
        
        String opcion = input.nextLine().trim();
        System.out.println();
        
        switch(opcion) {
            case "1":
                iniciarSesion(input);
                break;
            case "2":
                registrarUsuario(input);
                break;
            case "3":
                correr = false;
                try { socket.close();} catch(IOException e) {e.printStackTrace();}
                System.exit(0);
                break;
            default:
                System.out.println("Opcion invalida.");
                break;
        }
    }
    
    private void mostrarMenuUsuario(Scanner input) {
        System.out.println("\nMenu de usuario");
        System.out.println("1) Mostrar informacion");
        System.out.println("2) Editar nombre");
        System.out.println("3) Editar apellido");
        System.out.println("4) Editar contrasena");
        System.out.println("5) Eliminar cuenta");
        System.out.println("6) Cerrar sesion");
        System.out.print("Seleccione una opcion: ");
        
        String opcion = input.nextLine().trim();
        System.out.println();
        
        switch(opcion) {
            case "1":
                out.println("Tipo:MostrarInfo");
                break;
            case "2":
                editarNombre(input);
                break;
            case "3":
                editarApellido(input);
                break;
            case "4":
                editarPassword(input);
                break;
            case "5":
                eliminarCuenta(input);
                break;
            case "6":
                cerrarSesion();
                break;
            case "7":
                enviarMensajeChat(input);
                break;
            default:
                System.out.println("Opcion invalida.");
                break;
        }
    }
    
    private void iniciarSesion(Scanner input) {
        System.out.print("Correo o usuario: ");
        String identificador = input.nextLine().trim();
        System.out.print("Contrasena: ");
        String password = input.nextLine().trim();
        
        String mensaje = "Tipo:InicioSesion,Identificador:" + identificador + ",Password:" + password;
        out.println(mensaje);
    }
    
    private void registrarUsuario(Scanner input) {
        System.out.print("Nombre: ");
        String nombre = input.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = input.nextLine().trim();
        System.out.print("Correo electronico: ");
        String correo = input.nextLine().trim();
        System.out.print("Nombre de usuario: ");
        String user = input.nextLine().trim();
        System.out.print("Contrasena: ");
        String password = input.nextLine().trim();
        
        String mensaje = "Tipo:RegistroUsuario,Nombre:" + nombre + ",Apellido:" + apellido + 
                        ",Correo:" + correo + ",Username:" + user + ",Password:" + password;
        out.println(mensaje);
    }
    
    private void editarNombre(Scanner input) {
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = input.nextLine().trim();
        
        String mensaje = "Tipo:EditarNombre,Nombre:" + nuevoNombre;
        out.println(mensaje);
    }
    
    private void editarApellido(Scanner input) {
        System.out.print("Nuevo apellido: ");
        String nuevoApellido = input.nextLine().trim();
        
        String mensaje = "Tipo:EditarApellido,Apellido:" + nuevoApellido;
        out.println(mensaje);
    }
    
    private void editarPassword(Scanner input) {
        System.out.print("Nueva contrasena: ");
        String nuevaPassword = input.nextLine().trim();
        
        String mensaje = "Tipo:EditarPassword,Password:" + nuevaPassword;
        out.println(mensaje);
    }
    
    private void eliminarCuenta(Scanner input) {
        System.out.print("¿Está seguro de eliminar su cuenta? (s/n): ");
        String confirmacion = input.nextLine().trim().toLowerCase();
        
        if(confirmacion.equals("s")) {
            out.println("Tipo:EliminarCuenta");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }
    
    private void cerrarSesion() {
        out.println("Tipo:CerrarSesion");
    }
    
    private void enviarMensajeChat(Scanner input) {
        System.out.print("Mensaje: ");
        String mensaje = input.nextLine().trim();
        
        if(!mensaje.isEmpty()) {
            out.println(mensaje);
        }
    }
}