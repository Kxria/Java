package edu.uabc.hgch.Practica4;

public class Reloj {
    private int horas, minutos, segundos;
    
    public Reloj() {
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
    }

    public int getHoras() {
        return horas;
    }
    
    public int getMinutos() {
        return minutos;
    }
    
    public int getSegundos() {
        return segundos;
    }
    
    public void configurar(int horas, int minutos, int segundos) {
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
    }
    
    public void incrementar() {
        segundos++;
        if (segundos >= 60) {
            segundos = 0;
            minutos++;
            if (minutos >= 60) {
                minutos = 0;
                horas++;
                if (horas >= 24) {
                    horas = 0;
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}