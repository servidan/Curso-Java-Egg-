/*
 * Sin licencia .
 * Uso para capacitación
 * 2021 Año de la Prevención y Lucha contra el COVID-19.

    20.Realizar un programa que lea 4 números (comprendidos entre 1 y 20) 
    e imprima el número ingresado seguido de tantos asteriscos como indique 
    su valor.

    Por ejemplo:

    5 *****
    3 ***
    11 ***********
    2 **

 */
package guiajavaintroduccion;

import java.util.Scanner;

/**
 *
 * @author Adrian E. Camus
 */
public class ej_20 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        int contador = 0;
        int num;

        while (contador < 4) {
            System.out.print("Ingrese un número: ");
            num = leer.nextInt();
            for (int i = 0; i <= num; i++) {
                if (i == 0) {
                    System.out.print(num);
                } else {
                    System.out.print("*");
                }
            }
            System.out.println("");
            contador = contador + 1;
        }

    }
        
        
    }

