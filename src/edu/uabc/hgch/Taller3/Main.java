package edu.uabc.hgch.Taller3;

import java.util.Scanner;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        TreeSet<Persona> lista = new TreeSet<>();

        String nombre;
        int edad;
        Persona persona = null;
        
        int opcion = -1;
        
        while(true) {
            System.out.println("\n===========================================");
            System.out.println("Menu de opciones.");
            System.out.println("1) Registrar persona.");
            System.out.println("2) Personas registradas.");
            System.out.println("3) Rango.");
            System.out.println("0) Salir.");
            System.out.print("Ingrese una opcion: ");
            opcion = input.nextInt();

            switch(opcion) {
                case 1: // Registrar persona
                    input.nextLine();
                    System.out.println("\n1) Registrar persona.");

                    System.out.print("Ingrese el nombre de la persona: ");
                    nombre = input.nextLine();
                    
                    System.out.print("Ingrese la edad de la persona: ");
                    edad = input.nextInt();
                    persona = new Persona(nombre, edad);
                    
                    if (lista.contains(persona)) {
                        System.out.println("La persona ya existe.");
                    } else {
                        lista.add(persona);
                        System.out.println("Persona registrada exitosamente.");
                    }

                    break;

                case 2: // Personas registradas
                    System.out.println("\n2) Personas registradas.");
                    
                    if (lista.isEmpty()) {
                        System.out.println("No hay personas registradas.");
                    } else {
                        int indice = 1;
                        for (Persona p : lista) {
                            System.out.println(indice + ". " + p);
                            indice++;
                        }
                    }
                    break;
                
                case 3: // Rango
                    if (lista.isEmpty()) {
                        System.out.println("No hay personas registradas.");
                        break;
                    }

                    input.nextLine();

                    // Pedir rango de iniciales
                    System.out.print("Ingrese la inicial mínima del nombre: ");
                    char inicialMin = input.nextLine().toUpperCase().charAt(0);

                    System.out.print("Ingrese la inicial máxima del nombre: ");
                    char inicialMax = input.nextLine().toUpperCase().charAt(0);

                    // Pedir rango de edades
                    System.out.print("Ingrese la edad mínima: ");
                    int edadMin = input.nextInt();

                    System.out.print("Ingrese la edad máxima: ");
                    int edadMax = input.nextInt();

                    // Validación del rango de edades
                    if (edadMin > edadMax) {
                        System.out.println("El rango de edades es inválido.");
                        break;
                    }

                    // Ajustar inicial máxima
                    char siguienteInicial = (char)(inicialMax + 1);

                    // Conjunto ordenado por nombre
                    TreeSet<Persona> conjuntoPersonas = new TreeSet<>(Persona.cmpNombre());
                    conjuntoPersonas.addAll(lista);

                    //  Primer filtrado por nombre
                    Persona inicioNombre = new Persona(String.valueOf(inicialMin), 1);
                    Persona finNombre    = new Persona(String.valueOf(siguienteInicial), 200);

                    TreeSet<Persona> filtradoPorNombre = (TreeSet<Persona>) conjuntoPersonas.subSet(inicioNombre, true, finNombre, false);

                    System.out.println("\nFiltrado por nombre:");
                    for (Persona p : filtradoPorNombre) {
                            System.out.println(p);
                        }

                    // Segundo filtrado por edad
                    TreeSet<Persona> filtradoPorNombreYEdad = new TreeSet<>(Persona.cmpEdad());
                    filtradoPorNombreYEdad.addAll(filtradoPorNombre);

                    Persona inicioEdad = new Persona(String.valueOf(inicialMin), edadMin);
                    Persona finEdad    = new Persona(String.valueOf(siguienteInicial), edadMax);

                    filtradoPorNombreYEdad = (TreeSet<Persona>)filtradoPorNombreYEdad.subSet(inicioEdad, true, finEdad, true);

                    // Mostrar resultados
                    System.out.println("\nFiltrado por nombre y edad:");
                    if (filtradoPorNombreYEdad.isEmpty()) {
                        System.out.println("No hay personas en ese rango.");
                    } else {
                        for (Persona p : filtradoPorNombreYEdad) {
                            System.out.println(p);
                        }
                    }
                    break;
                
                case 0: // Salir
                    input.close();
                    System.exit(0);
                    break;

                default: // default
                    System.out.println("Opcion incorrecta.");
                    break;
            }
        }
    }
}
