package edu.uabc.hgch.Taller4;

public class Asiento {
    int x, y;
    boolean estado;

    public Asiento(int x, int y) {
        this.x = x;
        this.y = y;
        this.estado = false;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getEstado() {
        return estado;
    }

    public void reservar() {
        this.estado = true;
    }

    public void cancelar() {
        this.estado = false;
    }

    public String toString() {
        return estado ? "[X]" : "[ ]";
    }
}