package edu.uabc.hgch.Taller6;

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
                } else if(message.startsWith("Tipo:Saludo")) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("[SERVIDOR]: Hola soy Servidor");
                } else if(message.startsWith("Tipo:Registro")) {
                    String[] param = message.split(",");
                    String matricula = null;
                    String nombre = null;
                    
                    if(param.length >= 3) {
                        String tmp1 = param[1].replace("Matricula:","");
                        matricula = tmp1;
                        
                        String tmp2 = param[2].replace("Nombre:","");
                        nombre = tmp2;
                    }
                    
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    
                    if(matricula != null && nombre != null) {
                        String existe = alumnos.get(matricula);
                        if(existe != null) {
                            out.println("[SERVIDOR]: No se pudo realizar la operacion");
                        } else {
                            alumnos.put(matricula, nombre);
                            out.println("[SERVIDOR]: Alumno registrado");
                        }
                    } else {
                        out.println("[SERVIDOR]: No se pudo realizar la operacion");
                    }
                } else if(message.startsWith("Tipo:Consulta")) {
                    String[] param = message.split(",");
                    String matricula = null;
                    
                    if(param.length >= 2) {
                        String tmp = param[1].replace("Matricula:","");
                        matricula = tmp;
                    }
                    
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    
                    if(matricula != null) {
                        String nombre = alumnos.get(matricula);
                        out.println("[SERVIDOR]: " + nombre);
                    } else {
                        out.println("[SERVIDOR]: null");
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

// package edu.uabc.hgch.Taller6;
// /*
// javac -d bin src/taller6/*.java
// java -cp bin taller6.Main
// */


// import java.io.*;
// import java.net.*;
// import java.util.*;

// public class ClientHandler implements Runnable { 
//     private List<ClientHandler> clientes;
//     private TreeMap<String,String> alumnos;
//     private Socket socket;
//     private String username;
//     private BufferedReader in;

//     public ClientHandler(Socket socket,List<ClientHandler> clientes,TreeMap<String,String> alumnos) {
//         this.alumnos = alumnos;
//         this.clientes = clientes;
//         this.socket = socket;
//         this.username = "";
        
//         try {
//             //SE CREA UN BUFFER PARA LA RECEPCION DE MENSAJES DEL SERVIDOR
//             this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//         } catch(IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public void run() {
//         String message;

//         try {
//             //ESPERA PARA RECEPCION DE MENSAJES
//             while (socket.isConnected() && (message = in.readLine()) != null) {
//                 System.out.println("Mensaje recibido: " + message);

//                 if(message.startsWith("username:")) {
//                     String tmp = message.replace("username:","");
//                     username = tmp;

//                 } else if(message.startsWith("matricula:")) {
//                     String []param = message.split(":");
                    
//                     if(param.length >= 2) {
//                         String nombre = alumnos.get(param[1]);
                    
//                         if(nombre != null) {
//                             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                             //ENVIAR MENSAJE
//                             out.println("[SERVIDOR]: Tu nombre es "+nombre);
//                         }
//                     }
//                 } else if(message.startsWith("Tipo:Saludo")) {
//                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                     out.println("[SERVIDOR]: Hola soy Servidor");

//                 } else if(message.startsWith("Tipo:Registro")) {
//                     String[] param = message.split(",");
//                     String matricula = null;
//                     String nombre = null;
                    
//                     if(param.length >= 3) {
//                         String tmp1 = param[1].replace("Matricula:","");
//                         matricula = tmp1;
                        
//                         String tmp2 = param[2].replace("Nombre:","");
//                         nombre = tmp2;
//                     }
                    
//                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    
//                     if(matricula != null && nombre != null) {
//                         String existe = alumnos.get(matricula);
//                         if(existe != null) {
//                             out.println("[SERVIDOR]: No se pudo realizar la operacion");
//                         } else {
//                             alumnos.put(matricula, nombre);
//                             out.println("[SERVIDOR]: Alumno registrado");
//                         }
//                     }
//                 } else if(message.startsWith("Tipo:Consulta")) {
//                     String[] param = message.split(",");
//                     String matricula = null;
                    
//                     if(param.length >= 2) {
//                         String tmp = param[1].replace("Matricula:","");
//                         matricula = tmp;
//                     }
                    
//                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    
//                     if(matricula != null) {
//                         String nombre = alumnos.get(matricula);
//                         out.println("[SERVIDOR]: " + nombre);
//                     }
//                 } else { //DISTRIBUCION DE MENSAJES PARA LOS DEMAS CLIENTES
//                     distribuirMensaje(socket, message);
//                 }
//             }
//         } 
//         catch (IOException e) {
//             e.printStackTrace();
//             try{socket.close();} catch (IOException f) {}
//             System.out.println("Cliente desconectado: " + socket);
//         }
//     }

//     private void distribuirMensaje(Socket sender, String message) 
//     {
//         Socket socket;
//         for (ClientHandler client : clientes) 
//         {
//             socket = client.get_Socket();
//             if (socket != sender || true) 
//             {
//                 try 
//                 {
//                     //SE CREA UN BUFFER PARA TRANSMISION DE MENSAJES PARA CADA CLIENTE
//                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                     //ENVIAR MENSAJE
//                     out.println("["+username+"]: "+message);
//                 } 
//                 catch (IOException e) 
//                 {
//                     e.printStackTrace();
//                 }
//             }
//         }
//     }
    
//     public Socket get_Socket()
//     {
//         return socket;
//     }
    
//     public String get_username()
//     {
//         return new String(username);
//     }
// }