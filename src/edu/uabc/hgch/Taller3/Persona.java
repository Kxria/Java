package edu.uabc.hgch.Taller3;
import java.util.Comparator;
import java.lang.Comparable;

public class Persona implements Comparable<Persona> {
    private String nombre;
    private int edad;

    Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }
    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return nombre + ", " + edad;
    }

    @Override
    public int compareTo(Persona otro) {
        int cmp = nombre.compareToIgnoreCase(otro.getNombre());
        if (cmp != 0) {
            return cmp;
        }
        // Si el nombre es igual, usar la edad como desempate
        return Integer.compare(this.edad, otro.getEdad());
    }

    public static Comparator<Persona> cmpEdad() {
        return new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                int result = Integer.compare(p1.getEdad(), p2.getEdad());
                if (result == 0) {
                    return p1.compareTo(p2); // usa tu compareTo para desempatar
                }
                return result;
            }
        };
    }

    public static Comparator<Persona> cmpNombre() {
        return new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return p1.compareTo(p2); // ya compara por nombre y edad
            }
        };
    }
}