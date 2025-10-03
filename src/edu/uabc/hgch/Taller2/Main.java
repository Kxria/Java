package edu.uabc.hgch.Taller2;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Integer> lista = new ArrayList<>();

        int opcion = -1;
        Integer dato;
        int index;

        while(true) {
            System.out.println("Menu de opciones.");
            System.out.println("1) Mostrar ArrayList.");
            System.out.println("2) Agregar valor.");
            System.out.println("3) Agregar valor en posicion.");
            System.out.println("4) Eliminar en base indice.");
            System.out.println("5) Eliminar en base valor.");
            System.out.println("6) Sumatoria.");
            System.out.println("0) Salir.");
            System.out.print("Ingrese una opcion: ");
            opcion = input.nextInt();

            switch(opcion) {
                case 1: // mostrar lista
                    System.out.println("Elementos del ArrayList:");
                    if(lista.isEmpty()) {
                        System.out.println("La lista esta vacia.");
                    } else {
                        for(int i = 0 ; i < lista.size() ; i++) {
                            System.out.println(i + ".- " + lista.get(i));
                        }
                    }
                    System.out.println("\nTamanio del ArrayList: " + lista.size() + "\n");
                    break;
                case 2: // agregar valor
                    System.out.print("Ingresa el valor a agregar: ");
                    dato = input.nextInt();
                    System.out.println("Dato capturado: " + dato + "\n");
                    lista.add(dato);

                    break;
                case 3: // agregar valor en posicion
                    System.out.print("Ingresa el valor a agregar: ");
                    dato = input.nextInt();
                    System.out.println("Dato capturado: " + dato + "\n");

                    System.out.print("Ingresa el indice: ");
                    index = input.nextInt();
                    
                    if (index >= 0 && index <= lista.size()) {
                        lista.add(index, dato);
                        System.out.println("Dato "+ dato +" agregado en [" + index + "].\n");
                    } else {
                        System.out.println("Indice invalido.\n");
                    }
                    break;
                case 4: // eliminar en base a indice
                    System.out.print("Ingresa el indice a eliminar: ");
                    index = input.nextInt();
                    System.out.println("indice capturado: " + index + "\n");

                    if(index >= 0 && index <lista.size()) {
                        System.out.println("Dato eliminado: " + lista.get(index));
                        lista.remove(index);
                    } else {
                        System.out.println("Indice invalido.\n");
                    }
                    break;
                case 5: // eliminar en base a valor
                    System.out.print("Ingresa el dato a eliminar: ");
                    dato = input.nextInt();
                    System.out.println("dato capturado: " + dato + "\n");

                    if(lista.remove(dato)) {
                        System.out.println("Dato eliminado.\n");
                    } else {
                        System.out.println("Dato invalido.\n");
                    }
                    break;
                case 6: // sumatoria
                    int suma = 0;
                    for(int x : lista) {
                        suma += x;
                    }

                    System.out.println("Sumatoria = " + suma + "\n");
                    break;
                case 0: // salir
                    lista.clear();
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
