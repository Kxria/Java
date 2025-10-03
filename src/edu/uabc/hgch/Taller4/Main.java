package edu.uabc.hgch.Taller4;

import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        TreeMap<String, Asiento> asientosMapeados = new TreeMap<>();
        Asiento[][] sala = new Asiento[12][15];

        String codigos[];
        String entrada;

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 15; j++) {
                sala[i][j] = new Asiento(i, j);
                char letra = (char) ('A' + i);
                String clave = letra + Integer.toString(j + 1);
                asientosMapeados.put(clave, sala[i][j]);
            }
        }

        int opcion = -1;

        while(true) {
            System.out.println("\n===========================================");
            System.out.println("Menu de opciones.");
            System.out.println("1) Consultar");
            System.out.println("2) Reservar");
            System.out.println("3) Cancelar reserva");
            System.out.println("0) Salir");
            System.out.print("Ingrese una opcion: ");
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1: // Consultar
                    System.out.println();
                    mostrarSalas(sala);
                    break;

                case 2: // Reservar
                    boolean valido = true;
                    
                    System.out.println("2) Reservar\n");
                    mostrarSalas(sala);
                    System.out.print("Ingrese el asiento a reservar: ");
                    entrada = input.nextLine().trim().toUpperCase();

                    codigos = entrada.split(", ");

                    for (String codigo : codigos) {
                        codigo = codigo.trim();

                        if (!verificar(codigo)) {
                            valido = false;
                            break;
                        }
                        Asiento asiento = asientosMapeados.get(codigo);
                        if (asiento.getEstado()) {
                            valido = false;
                            break;
                        }
                    }

                    if (!valido) {
                        System.out.println("Error al asignar.");
                    } else {
                        for (String codigo : codigos) {
                            codigo = codigo.trim();
                            if (codigo.isEmpty()) {
                                continue;
                            }
                            Asiento asiento = asientosMapeados.get(codigo);
                            asiento.reservar();
                            System.out.println("Asiento " + codigo + " reservado.");
                        }
                    }
                    break;

                case 3: // Cancelar reserva
                        System.out.println("3) Cancelar reserva\n");
                        mostrarSalas(sala);
                        System.out.print("Ingrese el asiento a cancelar su reserva: ");
                        entrada = input.nextLine().trim().toUpperCase();

                        codigos = entrada.split(", ");
                        boolean validoCancelar = true;
                        String errorCancelar = "";

                        for (String codigo : codigos) {
                            codigo = codigo.trim();
                            if (!verificar(codigo)) {
                                validoCancelar = false;
                                errorCancelar = "Código inválido: " + codigo;
                                break;
                            }
                            Asiento asiento = asientosMapeados.get(codigo);
                            if (!asiento.getEstado()) {
                                validoCancelar = false;
                                errorCancelar = "El asiento '" + codigo + "' no está reservado.";
                                break;
                            }
                        }

                        if (!validoCancelar) {
                            System.out.println("Error al cancelar reserva. " + errorCancelar);
                        } else {
                            for (String codigo : codigos) {
                                codigo = codigo.trim();
                                Asiento asiento = asientosMapeados.get(codigo);
                                asiento.cancelar();
                                System.out.println("Reserva de " + codigo + " cancelada.");
                            }
                        }
                        break;

                case 0: // Salir
                    input.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("\nOpcion invalida");
                    break;
            }
        }
    }

    private static void mostrarSalas(Asiento[][] sala) {
        for (int i = 0; i < 15; i++) {
            if (i >= 9) {
                System.out.print("  " + (i + 1));
            } else {
                System.out.print("   " + (i + 1));
            }
        }
        System.out.println("");

        for (int i = 0; i < 12; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 15; j++) {
                System.out.print(sala[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private static boolean verificar(String asiento) {
        char letra;

        if (asiento.length() < 2 || asiento.length() > 3) {
            return false;
        }
        letra = asiento.charAt(0);
        if (letra < 'A' || letra > 'L') {
            return false;
        }

        try {
            int num = Integer.parseInt(asiento.substring(1));
            return num >= 1 && num <= 15;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}