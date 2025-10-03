package edu.uabc.hgch.Practica3;

import java.util.TreeSet;
import java.util.Scanner;

public class Main {
    private static TreeSet<Usuario> usuarios = new TreeSet<>();
    private static Scanner input = new Scanner(System.in);
    private static Usuario usuarioActual = null;
    
    public static void main(String[] args) {
        while (true) {
            if (usuarioActual == null) {
                mostrarMenuPrincipal();
            } else {
                mostrarMenuUsuario();
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\nSistema principal");
        System.out.println("1) Iniciar sesión");
        System.out.println("2) Registrar");
        System.out.println("3) Salir");
        System.out.print("Seleccione una opción: ");

        int opcion_main = input.nextInt();
        input.nextLine();
        System.out.println();
        
        switch (opcion_main) {
            case 1: // iniciar sesion
                iniciarSesion();
                break;

            case 2: // registro
                registrarUsuario();
                break;

            case 3: // salir
                input.close();
                System.exit(0);
                break;

            default:
                System.out.println("Opción inválida.");
                break;
        }
    }

    private static void mostrarMenuUsuario() {
        System.out.println("\nBienvenido, " + usuarioActual.getUsername());
        System.out.println("1) Mostrar información");
        System.out.println("2) Editar nombre");
        System.out.println("3) Editar apellido");
        System.out.println("4) Editar contraseña");
        System.out.println("5) Eliminar cuenta");
        System.out.println("6) Cerrar sesión");
        System.out.print("Seleccione una opción: ");

        int opcion_user = input.nextInt();
        input.nextLine();
        System.out.println();

        switch (opcion_user) {
            case 1: // mostrar info
                System.out.println("Nombre: " + usuarioActual.getNombre());
                System.out.println("Apellido: " + usuarioActual.getApellido());
                System.out.println("Correo: " + usuarioActual.getCorreo());
                System.out.println("Usuario: " + usuarioActual.getUsername());
                break;

            case 2: // editar nombre
                System.out.print("Nuevo nombre: ");
                usuarioActual.setNombre(input.nextLine());
                System.out.println("Nombre actualizado.");
                break;

            case 3: // editar apellido
                System.out.print("Nuevo apellido: ");
                usuarioActual.setApellido(input.nextLine());
                System.out.println("Apellido actualizado.");
                break;

            case 4: // editar contrasena
                System.out.print("Nueva contraseña: ");
                usuarioActual.setPassword(input.nextLine());
                System.out.println("Contraseña actualizada.");
                break;

            case 5:
                usuarios.remove(usuarioActual);
                System.out.println("Cuenta eliminada exitosamente.");
                usuarioActual = null;
                break;

            case 6: // cerrar sesion
                System.out.println("Sesión cerrada...");
                usuarioActual = null;
                break;

            default:
                System.out.println("Opción inválida.");
                break;
        }
    }

    private static void iniciarSesion() {
        System.out.print("Correo electrónico o Nombre de Usuario: ");
        String identificador = input.nextLine();
        System.out.print("Contraseña: ");
        String password = input.nextLine();

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
            System.out.println("Inicio de sesión exitoso.");
        } else {
            System.out.println("Error de inicio de sesion");
        }
    }

    private static void registrarUsuario() {
        System.out.print("Nombre: ");
        String nombre = input.nextLine();
        System.out.print("Apellido: ");
        String apellido = input.nextLine();
        System.out.print("Correo electronico: ");
        String correo = input.nextLine();
        System.out.print("Nombre de usuario: ");
        String username = input.nextLine();
        System.out.print("Contraseña: ");
        String password = input.nextLine();

        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo) || u.getUsername().equals(username)) {
            System.out.println("Error: Correo o Nombre de Usuario ya registrados.");
            System.out.print("¿Desea volver a capturar los datos? (s/n): ");
            String opcion = input.nextLine().trim().toLowerCase();
            
            if (opcion.equals("s")) {
                registrarUsuario();
            } else {
                System.out.println("Registro cancelado. Regresando al menú principal.");
            }

            return;
            }
        }

        Usuario nuevo = new Usuario(nombre, apellido, correo, username, password);
        usuarios.add(nuevo);
        System.out.println("Usuario registrado exitosamente.");
    }
}