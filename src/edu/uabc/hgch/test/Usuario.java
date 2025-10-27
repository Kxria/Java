package edu.uabc.hgch.test;

import java.lang.Comparable;
import java.io.Serializable;

public class Usuario implements Comparable<Usuario>, Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre, apellido, correo, username, password;

    public Usuario(String nombre, String apellido, String correo, String username, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.username = username;
        this.password = password;
    }

    public Usuario(String cadena, String password) {
        this.correo = cadena;
        this.username = cadena;
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getCorreo() {
        return correo;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Usuario otro = (Usuario) obj;
        
        if (!password.equals(otro.password)) {
            return false;
        }
        
        return (username.equals(otro.username) || username.equals(otro.correo) || 
                correo.equals(otro.username) || correo.equals(otro.correo));
    }

    @Override
    public int compareTo(Usuario otro) {
        int comparacionUsername = this.username.compareTo(otro.username);
        if (comparacionUsername != 0) {
            return comparacionUsername;
        }
        
        int comparacionCorreo = this.correo.compareTo(otro.correo);
        if (comparacionCorreo != 0) {
            return comparacionCorreo;
        }
        
        return this.password.compareTo(otro.password);
    }
}