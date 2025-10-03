package edu.uabc.hgch.Practica2;

import java.util.Comparator;

public class Materia implements Comparable<Materia> {
    private int code, creditos;
    private String nombre;

    public Materia(String nombre, int code, int creditos) {
        this.nombre = nombre;
        this.code = code;
        this.creditos = creditos;
    }

    public String getNombre() {
        return nombre;
    }
    public int getCode() {
        return code;
    }
    public int getCreditos() {
        return creditos;
    }

    @Override
    public String toString() {
        return "Materia: " + nombre + "\nCode: " + code + "\nCreditos: " + creditos;
    }

    @Override
	public int compareTo(Materia otro) {
		if(code != otro.getCode())
            return nombre.compareToIgnoreCase(otro.getNombre());
		return 1;
	}

    public static Comparator<Materia> cmpCode() {
        return new Comparator<Materia>() {
			@Override
			public int compare(Materia p1, Materia p2) {
				int result = Double.compare( p1.getCode() , p2.getCode() );
				if(result == 0)
					return p1.compareTo(p2);
				return result;
			}
		};
    }

    public static Comparator<Materia> cmpNombre() {
        return new Comparator<Materia>() {
			@Override
			public int compare(Materia p1, Materia p2)  {
				return p1.compareTo(p2);
			}
		};
    }

    public static Comparator<Materia> cmpCreditos() {
        return new Comparator<Materia>() {
			@Override
			public int compare(Materia p1, Materia p2) {				
				int result = Double.compare( p1.getCreditos() , p2.getCreditos() );
				if(result == 0)
					return p1.compareTo(p2);
				return result;
			}
		};  
    }
}
