package edu.uabc.hgch.Taller1; 

import java.util.Scanner;

/*** 
    Estando en la carpeta que contiene la carpeta src (tomar en cuenta sus rutas y paquetes)
    Compilar: javac -d bin -sourcepath src src/edu/uabc/HGCH/Taller1/*.java
    Ejecutar: java -cp bin edu.uabc.HCGH.Taller1.Taller1
*/ 

public class Taller1 {
    public static void main(String []args) {
        Scanner input = new Scanner(System.in);

        int anio;           
        System.out.print("Captura el ano: "); 
        anio = input.nextInt();
        System.out.println("El ano capturado es: " + anio); 
        
        if (anio % 4 == 0) {
            if (anio % 100 == 0) {
                if (anio % 400 == 0) {
                    System.out.println(anio + " es bisiesto.");
                } else {
                    System.out.println(anio + " no es bisiesto.");
                }
            } else {
                System.out.println(anio + " es bisiesto.");
            }
        } else {
            System.out.println(anio + " no es bisiesto.");
        }

        Caja caja1;
        double alto, ancho, profundo;

        System.out.printf("\n\nAhora la caja"); 
        System.out.printf("\nCaptura el alto (cm): "); 
        alto = input.nextDouble();

        System.out.printf("Captura el ancho (cm): "); 
        ancho = input.nextDouble();

        System.out.printf("Captura el profundo (cm): "); 
        profundo = input.nextDouble();

        caja1 = new Caja(alto, ancho, profundo);
        
        Caja caja2;

        System.out.printf("\n\nAhora la caja2"); 
        System.out.printf("\nCaptura el alto (cm): "); 
        alto = input.nextDouble();

        System.out.printf("Captura el ancho (cm): "); 
        ancho = input.nextDouble();

        System.out.printf("Captura el profundo (cm): "); 
        profundo = input.nextDouble();
        
        caja2 = new Caja(alto,ancho,profundo);

        System.out.println("\n==================================\nDATOS\n==================================");
        System.out.printf("\nVolumen de caja1: %.2f cm^3", caja1.volumen() );
        System.out.printf("\nSuperficie de caja1: %.2f cm^3", caja1.superficie() );

        System.out.printf("\n\nVolumen de caja2: %.2f cm^3", caja2.volumen() );
        System.out.printf("\nSuperficie de caja2: %.2f cm^3", caja2.superficie() );

        System.out.printf("\n\nPorcentaje caja1 parametro caja2: %.2f%%", caja1.porcentaje(caja2));
        System.out.printf("\nPorcentaje caja1 parametro caja2: %.2f%%", caja2.porcentaje(caja1));
        
        input.close();
    }
}

//DEFINICION DE LA CLASE CAJA
class Caja {
    //ATRIBUTOS
    private double ancho;
    private double alto;
    private double profundo;
    
    //CONSTRUCTOR
    public Caja(double ancho,double alto, double profundo) {
        this.ancho = ancho; //SE USA THIS. PARA DIFERENCIAR EL ATRIBUTO DEL PARAMETRO DEL CONSTRUCTOR
        this.alto = alto;
        this.profundo = profundo;
    }

    //METODOS
    public double volumen() {
        return ancho * alto * profundo;
    }

    public double superficie() {
        double sup1 = alto * ancho;
        double sup2 = alto * profundo;
        double sup3 = ancho * profundo;
        double superficie = 2 * (sup1 + sup2 + sup3);

        return superficie;
    }   

    public double porcentaje(Caja x) {
        return (superficie()/x.superficie()) * 100;
    } 
}