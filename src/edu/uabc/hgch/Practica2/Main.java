package edu.uabc.hgch.Practica2;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Materia> materias = new ArrayList<>();

        int codigo, creditos;
        String nombre;
        Materia materia = null;
        
        int opcion = -1;
        while(true) {
            System.out.println("\n===========================================");
            System.out.println("Menu de opciones.");
            System.out.println("1) Agregar Materia.");
            System.out.println("2) Eliminar Materia.");
            System.out.println("3) Mostrar lista de Materias.");
            System.out.println("4) Ordenar por codigo.");
            System.out.println("5) Ordenar por nombre.");
            System.out.println("6) Ordenar por creditos.");
            System.out.println("0) Salir.");
            System.out.print("Ingrese una opcion: ");
            opcion = input.nextInt();

            switch(opcion) {
                case 1: // Agregar materia
                    input.nextLine();
                    System.out.println("\n1) Agregar Materia.");
                    System.out.print("Ingresa el nombre de la materia: ");
                    nombre = input.nextLine();
                    System.out.print("Ingresa el codigo de la materia: ");
                    codigo = input.nextInt();

                    boolean existe = false;
                    for (Materia m : materias) {
                        if (m.getCode() == codigo) {
                            existe = true;
                            break;
                        }
                    }
                    if (existe) {
                        System.out.println("Codigo ya existente.");
                        break;
                    }

                    System.out.print("Ingresa la cantidad de creditos de la materia: ");
                    creditos = input.nextInt();


                    materia = new Materia(nombre, codigo, creditos);
                    materias.add(materia);

                    System.out.println("\nMateria agregada:" + materia);
                    break;

                case 2: // Eliminar materia
                    System.out.println("\n2) Eliminar Materia.");
                    if(materias.isEmpty()) {
                        System.out.println("No hay materias para eliminar.");
                    }else {
                        for(int i = 0 ; i < materias.size() ; i++) {
                            System.out.println("\n" + materias.get(i));
                        }
                    }
                    System.out.print("Ingresa el codigo de la materia a eliminar: ");
                    codigo = input.nextInt();
        
                    // Verificar si el empleado existe
                    boolean eliminado = false;

                    for (int i = 0; i < materias.size(); i++) {
                        Materia mat = materias.get(i);
                        if (mat.getCode() == codigo) {
                            System.out.println("Materia eliminada: " + mat.getNombre() + " (" + mat.getCode() + ").\n");
                            materias.remove(i);
                            eliminado = true;
                            break;
                        }
                    }

                    if (!eliminado) {
                        System.out.println("Codigo no encontrado.\n");
                    }
                    break;

                
                case 3: // Mostrar lista de materias
                    System.out.println("Lista de Materias:");
                    if(materias.isEmpty()) {
                        System.out.println("La lista esta vacia.");
                    } else {
                        for(int i = 0 ; i < materias.size() ; i++) {
                            System.out.println("\n" + materias.get(i));
                        }
                    }
                    break;

                case 4: // Ordenar por codigo
                    Collections.sort(materias, Materia.cmpCode());
                    break;

                case 5: // Ordenar por nombre
                    Collections.sort(materias, Materia.cmpNombre());
                    break;

                case 6: // Ordenar por creditos
                    Collections.sort(materias, Materia.cmpCreditos());
                    break;

                case 0: // salir
                    materias.clear();
                    input.close();
                    System.exit(0);
                    break;

                default: // default
                    System.out.println("Opcion incorrecta.");
                    // break;
            }
        }
    }
}
