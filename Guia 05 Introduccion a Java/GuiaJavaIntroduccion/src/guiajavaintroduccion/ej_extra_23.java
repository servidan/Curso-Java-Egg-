/*
 * Sin licencia.
 * Uso para capacitación
 * 2021 Año de la Prevención y Lucha contra el COVID-19.
    
    Realizar un programa que complete un vector con los N primeros números de 
    la sucesión de Fibonacci. Recordar que la sucesión de Fibonacci es la 
    sucesión de los siguientes números: 1, 1, 2, 3, 5, 8, 13, 21, 34, ...
    
    Donde  cada  uno  de  los  números  se  calcula  sumando  los  dos  
    anteriores  a  él.  Por ejemplo: La sucesión del número 2 se calcula 
    sumando (1+1) Análogamente, la sucesión del número 3 es (1+2),
    Y la del 5 es (2+3),Y así sucesivamente...La sucesión de Fibonacci 
    se puede formalizar de acuerdo a la siguiente fórmula: 
    
    Fibonacci n= Fibonacci n-1+ Fibonacci n-2 para todo n>1
    Fibonacci n = 1 para todo n<=1
    
    Por  lo  tanto,  si  queremos  calcular  el  término  “n”  debemos  
    escribir  una  función  que reciba como parámetro el valor de “n” y que 
    calcule la serie hasta llegar a ese valor.Para  conocer  más  acerca  de  
    la  serie  de  Fibonacci  consultar  el  siguiente 
    link: https://quantdare.com/numeros-de-fibonacci/

 */
package guiajavaintroduccion;

import java.util.Scanner;

/**
 *
 * @author Adrian E. Camus
 */
public class ej_extra_23 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int n, m, num, fibonacci;
        String fibo = "";
        // n - 1 = n
        // n - 2 = m
        Scanner scan = new Scanner(System.in);
        n = 1;
        m = 1;
        System.out.println("Ingrese hasta donde quiere imprimir");
        num = scan.nextInt();
        for (int i = 0; i < num; i++) {
            if (i == 0) {
                n = 1;
                m = 0;
                fibonacci = n + m;
                m = n;
                n = fibonacci;
            } else {
                fibonacci = n + m;
                m = n;
                n = fibonacci;
            }
            if (i < (num - 1)) {
                fibo += Integer.toString(fibonacci) + " - ";
            } else {
                fibo += Integer.toString(fibonacci);
            }
        }
        System.out.println("Primeros "+num+" numeros de Fibonacci:");
        System.out.println(fibo);
        scan.close();
            
    }
    
}
