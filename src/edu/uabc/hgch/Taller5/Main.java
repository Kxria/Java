package edu.uabc.hgch.Taller5;

import java.io.*;
import java.util.*;

public class Main {

    private static TreeSet<Usuario> usuarios = new TreeSet<>();
    private static Scanner input = new Scanner(System.in);
    private static Usuario usuarioActual = null;
    private static final String ARCHIVO = "Usuario.txt";

    public static void main(String[] args) {
        deserializar();

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

        int opcion_main;
        try {
            opcion_main = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: ingrese un número válido.");
            return;
        }

        System.out.println();

        switch (opcion_main) {
            case 1:
                iniciarSesion();
                break;
            case 2:
                registrarUsuario();
                break;
            case 3:
                guardarUsuarios();
                input.close();
                System.out.println("Saliendo del sistema...");
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

        int opcion_user;
        try {
            opcion_user = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: ingrese un número válido.");
            return;
        }

        System.out.println();

        switch (opcion_user) {
            case 1:
                System.out.println("Nombre: " + usuarioActual.getNombre());
                System.out.println("Apellido: " + usuarioActual.getApellido());
                System.out.println("Correo: " + usuarioActual.getCorreo());
                System.out.println("Usuario: " + usuarioActual.getUsername());
                break;

            case 2:
                System.out.print("Nuevo nombre: ");
                usuarioActual.setNombre(input.nextLine());
                System.out.println("Nombre actualizado.");
                guardarUsuarios();
                break;

            case 3:
                System.out.print("Nuevo apellido: ");
                usuarioActual.setApellido(input.nextLine());
                System.out.println("Apellido actualizado.");
                guardarUsuarios();
                break;

            case 4:
                System.out.print("Nueva contraseña: ");
                usuarioActual.setPassword(input.nextLine());
                System.out.println("Contraseña actualizada.");
                guardarUsuarios();
                break;

            case 5:
                usuarios.remove(usuarioActual);
                usuarioActual = null;
                System.out.println("Cuenta eliminada exitosamente.");
                guardarUsuarios();
                break;

            case 6:
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
            System.out.println("Error de inicio de sesión.");
        }
    }

    private static void registrarUsuario() {
        System.out.print("Nombre: ");
        String nombre = input.nextLine();
        System.out.print("Apellido: ");
        String apellido = input.nextLine();
        System.out.print("Correo electrónico: ");
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
        guardarUsuarios();
    }

    private static void guardarUsuarios() {
        try (FileOutputStream fos = new FileOutputStream(ARCHIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(usuarios);
            System.out.println("(Usuarios guardados en " + ARCHIVO + ")");
        } catch (IOException e) {
            System.out.println("ERROR AL SERIALIZAR: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void deserializar() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            return;
        }

        try (FileInputStream fis = new FileInputStream(ARCHIVO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            usuarios = (TreeSet<Usuario>) ois.readObject();
            System.out.println("Usuarios cargados correctamente desde " + ARCHIVO);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR AL DESERIALIZAR: " + e.getMessage());
        }
    }
}