package edu.uabc.hgch.Practica4;

import java.util.concurrent.Semaphore;

public class HiloReloj implements Runnable {
    private Thread thread;
    private Reloj reloj;
    private Semaphore semaforo;
    private volatile boolean ejecutando;
    
    public HiloReloj(Reloj reloj, Semaphore semaforo) {
        this.reloj = reloj;
        this.semaforo = semaforo;
        this.ejecutando = true;
        thread = new Thread(this, "HiloReloj");
        thread.start();
    }
    
    @Override
    public void run() {
        while (ejecutando) {
            try {
                semaforo.acquire();
                reloj.incrementar();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("HiloReloj detenido: " + e);
            }
            semaforo.release();
        }
    }
    
    public void detener() {
        ejecutando = false;
        thread.interrupt();
    }
    
    public void esperar() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("Error al esperar: " + e);
        }
    }
}