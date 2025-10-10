package edu.uabc.hgch.Practica4;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Reloj reloj = new Reloj();
        Semaphore semaforo = new Semaphore(1, true);
        HiloReloj hiloReloj = new HiloReloj(reloj, semaforo);

        
        int opcion = -1;
        
        while (opcion != 0) {
            System.out.println("\nMenu de opciones");
            System.out.println("1) Ver hora");
            System.out.println("2) Configurar");
            System.out.println("0) Salir");
            System.out.print("Ingrese una opcion: ");
            
            try {
                opcion = input.nextInt();
                input.nextLine();
                
                switch (opcion) {
                    case 1:
                        System.out.println("\n1) Ver hora");
                        System.out.println("Presione ENTER para regresar al menu anterior\n");
                        
                        Thread tread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    while (true) {
                                        System.out.println(reloj);
                                        Thread.sleep(1000);
                                    }
                                } catch (InterruptedException e) {
                                }
                            }
                        });
                        
                        tread.start();
                        input.nextLine();
                        tread.interrupt();
                        break;
                        
                    case 2:
                        try {
                            System.out.println("\n2) Configurar reloj");
                            
                            semaforo.acquire();
                            
                            System.out.print("Ingrese las horas (0-23): ");
                            int horas = input.nextInt();
                            
                            System.out.print("Ingrese los minutos (0-59): ");
                            int minutos = input.nextInt();
                            
                            System.out.print("Ingrese los segundos (0-59): ");
                            int segundos = input.nextInt();
                            input.nextLine();
                            
                            if (horas >= 0 && horas < 24 && minutos >= 0 && minutos < 60 && segundos >= 0 && segundos < 60) {
                                reloj.configurar(horas, minutos, segundos);
                                System.out.println("Reloj configurado a: " + reloj.toString());
                            } else {
                                System.out.println("Error: Hora invalida.");
                            }
                            
                            semaforo.release();
                        } catch (InterruptedException e) {
                            System.out.println("Error al configurar: " + e);
                        } catch (Exception e) {
                            System.out.println("Error: Ingrese un numero.");
                            input.nextLine();
                            semaforo.release();
                        }
                        break;
                        
                    case 0:
                        hiloReloj.detener();
                        hiloReloj.esperar();
                        input.close();
                        System.exit(0);
                        break;
                        
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un numero valido.");
                input.nextLine();
            }
        }
    }
}