package edu.uabc.hgch.Practica5;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable { 
    private List<ClientHandler> clientes;
    private TreeMap<String,String> alumnos;
    private TreeSet<Usuario> usuarios;
    private Socket socket;
    private String username;
    private BufferedReader in;
    private Usuario usuarioActual;

    public ClientHandler(Socket socket, List<ClientHandler> clientes, TreeMap<String,String> alumnos, TreeSet<Usuario> usuarios) {
        this.alumnos = alumnos;
        this.clientes = clientes;
        this.socket = socket;
        this.usuarios = usuarios;
        this.username = "";
        this.usuarioActual = null;
        
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String message;

        try {
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
                
                } else if(message.startsWith("Tipo:RegistroUsuario")) {
                    registrarUsuario(message);
                    
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
                            out.println("[SERVIDOR]: Usuario registrado");
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
                } else if(message.startsWith("Tipo:InicioSesion")) {
                    iniciarSesion(message);
                    
                } else if(message.startsWith("Tipo:MostrarInfo")) {
                    mostrarInformacion();
                    
                } else if(message.startsWith("Tipo:EditarNombre")) {
                    editarNombre(message);
                    
                } else if(message.startsWith("Tipo:EditarApellido")) {
                    editarApellido(message);
                    
                } else if(message.startsWith("Tipo:EditarPassword")) {
                    editarPassword(message);
                    
                } else if(message.startsWith("Tipo:EliminarCuenta")) {
                    eliminarCuenta();
                    
                } else if(message.startsWith("Tipo:CerrarSesion")) {
                    cerrarSesion();
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
            try{socket.close();} catch (IOException f) {}
            System.out.println("Cliente desconectado: " + socket);
        }
    }

    private void registrarUsuario(String message) {
        try {
            String[] param = message.split(",");
            
            if(param.length < 6) {
                enviarRespuesta("[SERVIDOR]: Error de registro");
                return;
            }
            
            String nombre = param[1].replace("Nombre:", "").trim();
            String apellido = param[2].replace("Apellido:", "").trim();
            String correo = param[3].replace("Correo:", "").trim();
            String user = param[4].replace("Username:", "").trim();
            String password = param[5].replace("Password:", "").trim();
            
            for (Usuario u : usuarios) {
                if (u.getCorreo().equals(correo)) {
                    enviarRespuesta("[SERVIDOR]: Error: Correo ya registrado");
                    return;
                }
                if (u.getUsername().equals(user)) {
                    enviarRespuesta("[SERVIDOR]: Error: Usuario ya registrado");
                    return;
                }
            }
            
            Usuario nuevo = new Usuario(nombre, apellido, correo, user, password);
            usuarios.add(nuevo);
            Server.serializarUsuarios();
            enviarRespuesta("[SERVIDOR]: Usuario registrado");
            
        } catch(Exception e) {
            e.printStackTrace();
            enviarRespuesta("[SERVIDOR]: Error al registrar usuario");
        }
    }
    
    private void iniciarSesion(String message) {
        try {
            String[] param = message.split(",");
            
            if(param.length < 3) {
                enviarRespuesta("[SERVIDOR]: Error de inicio de sesion");
                return;
            }
            
            String identificador = param[1].replace("Identificador:", "").trim();
            String password = param[2].replace("Password:", "").trim();
            
            Usuario temp = new Usuario(identificador, password);
            Usuario encontrado = null;
            
            for (Usuario u : usuarios) {
                if (u.equals(temp)) {
                    encontrado = u;
                    break;
                }
            }
            
            if (encontrado != null) {
                usuarioActual = encontrado;
                username = usuarioActual.getUsername();
                enviarRespuesta("[SERVIDOR]: Bienvenido " + username);
            } else {
                enviarRespuesta("[SERVIDOR]: Usuario inexistente");
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            enviarRespuesta("[SERVIDOR]: Error al iniciar sesion");
        }
    }
    
    private void mostrarInformacion() {
        if(usuarioActual == null) {
            enviarRespuesta("[SERVIDOR]: Debe iniciar sesion primero");
            return;
        }
        
        StringBuilder info = new StringBuilder();
        info.append("[SERVIDOR]: Informacion del usuario:\n");
        info.append("Nombre: ").append(usuarioActual.getNombre()).append("\n");
        info.append("Apellido: ").append(usuarioActual.getApellido()).append("\n");
        info.append("Correo: ").append(usuarioActual.getCorreo()).append("\n");
        info.append("Usuario: ").append(usuarioActual.getUsername());
        
        enviarRespuesta(info.toString());
    }
    
    private void editarNombre(String message) {
        if(usuarioActual == null) {
            enviarRespuesta("[SERVIDOR]: Debe iniciar sesion primero");
            return;
        }
        
        try {
            String[] param = message.split(",");
            
            if(param.length < 2) {
                enviarRespuesta("[SERVIDOR]: Error en formato");
                return;
            }
            
            String nuevoNombre = param[1].replace("Nombre:", "").trim();
            usuarioActual.setNombre(nuevoNombre);
            Server.serializarUsuarios();
            enviarRespuesta("[SERVIDOR]: Nombre actualizado");
            
        } catch(Exception e) {
            enviarRespuesta("[SERVIDOR]: Error al actualizar nombre");
            e.printStackTrace();
        }
    }
    
    private void editarApellido(String message) {
        if(usuarioActual == null) {
            enviarRespuesta("[SERVIDOR]: Debe iniciar sesion primero");
            return;
        }
        
        try {
            String[] param = message.split(",");
            
            if(param.length < 2) {
                enviarRespuesta("[SERVIDOR]: Error en formato");
                return;
            }
            
            String nuevoApellido = param[1].replace("Apellido:", "").trim();
            usuarioActual.setApellido(nuevoApellido);
            Server.serializarUsuarios();
            enviarRespuesta("[SERVIDOR]: Apellido actualizado");
            
        } catch(Exception e) {
            enviarRespuesta("[SERVIDOR]: Error al actualizar apellido");
            e.printStackTrace();
        }
    }
    
    private void editarPassword(String message) {
        if(usuarioActual == null) {
            enviarRespuesta("[SERVIDOR]: Debe iniciar sesion primero");
            return;
        }
        
        try {
            String[] param = message.split(",");
            
            if(param.length < 2) {
                enviarRespuesta("[SERVIDOR]: Error en formato");
                return;
            }
            
            String nuevaPassword = param[1].replace("Password:", "").trim();
            usuarioActual.setPassword(nuevaPassword);
            Server.serializarUsuarios();
            enviarRespuesta("[SERVIDOR]: Contrasena actualizada");
            
        } catch(Exception e) {
            enviarRespuesta("[SERVIDOR]: Error al actualizar contrasena");
            e.printStackTrace();
        }
    }
    
    private void eliminarCuenta() {
        if(usuarioActual == null) {
            enviarRespuesta("[SERVIDOR]: Debe iniciar sesion primero");
            return;
        }
        
        usuarios.remove(usuarioActual);
        Server.serializarUsuarios();
        enviarRespuesta("[SERVIDOR]: Cuenta eliminada");
        usuarioActual = null;
        username = "";
    }
    
    private void cerrarSesion() {
        if(usuarioActual == null) {
            enviarRespuesta("[SERVIDOR]: No hay sesion activa");
            return;
        }
        
        enviarRespuesta("[SERVIDOR]: Sesion cerrada exitosamente");
        usuarioActual = null;
        username = "";
    }
    
    private void enviarRespuesta(String mensaje) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
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