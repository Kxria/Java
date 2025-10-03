package edu.uabc.hgch.Practica1;
import java.util.Scanner;

public class Practica1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double ancho, alto, profundo;

        Caja []cajas = new Caja[5];
        for(int i = 0 ; i < 5 ; i++) {
            System.out.print("Ingrese el ancho de la caja[" + i + "]: ");
            ancho = input.nextDouble();

            System.out.print("Ingrese el alto de la caja[" + i + "]: ");
            alto = input.nextDouble();

            System.out.print("Ingrese el profundo de la caja[" + i + "]: ");
            profundo = input.nextDouble();

            cajas[i] = new Caja(ancho, alto, profundo);
            System.out.println();
        }

        int opt = -1;
        while(true) {
            System.out.println("======================================================\nMenu de opciones.\n");
            System.out.println("1) Mostrar orden por ancho.");
            System.out.println("2) Mostrar orden por alto.");
            System.out.println("3) Mostrar orden por profundo.");
            System.out.println("4) Mostrar orden por volumen.");
            System.out.println("0) Salir.");
            System.out.print("Ingresa la opcion a elegir: ");

            opt = input.nextInt();

            switch(opt) {
                case 1:

                    Caja aux = new Caja(0, 0, 0);
                    for(int i = 0 ; i < 5 ; i++) {
                        for(int j = i+1 ; j < 5 ; j++) {
                            if(cajas[i].getAncho() > cajas[j].getAncho()) {
                                aux = cajas[i];
                                cajas[i] = cajas[j];
                                cajas[j] = aux;
                            }
                        }
                    }

                    System.out.println("Caja[n] (Ancho, Alto, Profundidad, Volumen)\n");
                    for(int i = 0 ; i < 5 ; i++) {
                        System.out.printf("Caja[%d] (%.2f, %.2f, %.2f, %.2f)\n", i, cajas[i].getAncho(), cajas[i].getAlto(), cajas[i].getProfundo(), cajas[i].volumen());
                    }
                    break;
                case 2:
                    Caja aux1 = new Caja(0, 0, 0);
                    for(int i = 0 ; i < 5 ; i++) {
                        for(int j = i+1 ; j < 5 ; j++) {
                            if(cajas[i].getAlto() > cajas[j].getAlto()) {
                                aux1 = cajas[i];
                                cajas[i] = cajas[j];
                                cajas[j] = aux1;
                            }
                        }
                    }

                    System.out.println("Caja[n] (Ancho, Alto, Profundidad, Volumen)\n");
                    for(int i = 0 ; i < 5 ; i++) {
                        System.out.printf("Caja[%d] (%.2f, %.2f, %.2f, %.2f)\n", i, cajas[i].getAncho(), cajas[i].getAlto(), cajas[i].getProfundo(), cajas[i].volumen());
                    }
                    break;
                case 3:
                    Caja aux2 = new Caja(0, 0, 0);
                    for(int i = 0 ; i < 5 ; i++) {
                        for(int j = i+1 ; j < 5 ; j++) {
                            if(cajas[i].getProfundo() > cajas[j].getProfundo()) {
                                aux2 = cajas[i];
                                cajas[i] = cajas[j];
                                cajas[j] = aux2;
                            }
                        }
                    }

                    System.out.println("Caja[n] (Ancho, Alto, Profundidad, Volumen)\n");
                    for(int i = 0 ; i < 5 ; i++) {
                        System.out.printf("Caja[%d] (%.2f, %.2f, %.2f, %.2f)\n", i, cajas[i].getAncho(), cajas[i].getAlto(), cajas[i].getProfundo(), cajas[i].volumen());
                    }
                    break;
                case 4:
                    Caja aux3 = new Caja(0, 0, 0);
                    for(int i = 0 ; i < 5 ; i++) {
                        for(int j = i+1 ; j < 5 ; j++) {
                            if(cajas[i].volumen() > cajas[j].volumen()) {
                                aux3 = cajas[i];
                                cajas[i] = cajas[j];
                                cajas[j] = aux3;
                            }
                        }
                    }

                    System.out.println("Caja[n] (Ancho, Alto, Profundidad, Volumen)\n");
                    for(int i = 0 ; i < 5 ; i++) {
                        System.out.printf("Caja[%d] (%.2f, %.2f, %.2f, %.2f)\n", i, cajas[i].getAncho(), cajas[i].getAlto(), cajas[i].getProfundo(), cajas[i].volumen());
                    }
                    break;
                case 0:
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
        }
    }
}
