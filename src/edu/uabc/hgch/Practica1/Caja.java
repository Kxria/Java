package edu.uabc.hgch.Practica1;

class Caja {
    private double ancho;
    private double alto;
    private double profundo;
    
    public Caja(double ancho,double alto, double profundo) {
        this.ancho = ancho;
        this.alto = alto;
        this.profundo = profundo;
    }

    public double getAncho() {
        return ancho;
    }
    public double getAlto() {
        return alto;
    }
    public double getProfundo() {
        return profundo;
    }

    public double volumen() {
        return ancho * alto * profundo;
    }

    public double superficie() {
        return 2 * ((alto * ancho) + (alto * profundo) + (ancho * profundo));
    }   

    public double porcentaje(Caja x) {
        return (superficie()/x.superficie()) * 100;
    }
}