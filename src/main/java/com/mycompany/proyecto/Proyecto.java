/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyecto;

/**
 *
 * @author victo
 */
import java.util.*;
import javax.swing.JFrame;

public class Proyecto {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Nodo ArbolExpresion = new Nodo();
        Arbol arbol = new Arbol();
        Nodo Arbol = new Nodo();
        metodosArbol metodos = new metodosArbol();
        String expresion = "";
        String expresionNueva = "";
        Integer opcion;
        int contador = 0;
        String variable = "";
        String valor = "";
        //Nodo raiz = new Nodo();
        System.out.println("Bienvenido a Proyecto 1!");

        do {
            System.out.println("Seleccione una opcion: ");
            System.out.println("[1]\t Ingresar expresion");
            System.out.println("[2]\t Generar arbol");
            opcion = entrada.nextInt();
            switch (opcion) {
                case 1:
                    entrada.nextLine();
                    System.out.println("Ingrese una expresion:");
                    expresion = entrada.nextLine();
                    System.out.println("La expresion es : " + expresion);
                    System.out.println("Debe ingresar numeros para las variables: ");
                    verificarVariables(expresion);
                    expresionNueva = expresion;
                    while (contador < verificarVariables(expresion)) {
                        System.out.println("Debe ingresar la letra de la variable: ");
                        variable = entrada.nextLine();
                        System.out.println("Debe ingresar un numero a la variable: " + variable);
                        System.out.println("Debe ingresar un numero a la variable: " + variable );
                        valor = entrada.nextLine();
                        expresionNueva = colocarVariables(expresionNueva, variable, valor);
                        contador++;
                    }
                    System.out.println("La expresion es : " + expresionNueva);
                    break;
                case 2:
                    ArbolExpresion = arbol.ArbolExpresion(expresionNueva);
                    arbol.mostrarArbol(ArbolExpresion);
                    Pintar(ArbolExpresion);
                    Arbol = metodos.Arbol(expresionNueva);
                    metodos.mostrarArbol(Arbol);
                    Pintar(Arbol);
                    break;
            }
        } while (opcion != 3);
    }
//aqui muestra el arbolito

    public static void Pintar(Nodo Arbol) {
        JFrame frame = new JFrame("Árbol de Expresión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Construye el árbol de expresión (aquí asumiendo que ya tienes el método construirArbolExpresion)
        ArbolGrafico panel = new ArbolGrafico(Arbol);

        frame.add(panel);
        frame.setVisible(true);
    }

    //aqui revisa las variables ingresadas
    public static int verificarVariables(String expresion) {
        List<Character> variables = new ArrayList<>();
        int contador = 0;
        char[] noVariables = {'(', ')', '{', '}', '[', ']', '√', '^', '*', '/', '+', '-'};
        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);
            boolean esVariable = true;
            for (char noVariable : noVariables) {
                if (caracter == noVariable) {
                    esVariable = false;
                    break;
                }
            }
            if (esVariable && !variables.contains(caracter)) {
                variables.add(caracter);
                contador++;
            }
        }
        for (Character variable : variables) {
            System.out.print(variable + " -");
        }
        System.out.println();
        for(Character variable: variables) {
            System.out.print(variable+ " -");         
        }
        System.out.println();     
        return contador;
    }

    //asigna valores a las variables ingresadas
    public static String colocarVariables(String expresion, String caracter, String valor) {
        String expresionNueva = "";
        for (int i = 0; i < expresion.length(); i++) {
            if (expresion.charAt(i) == caracter.charAt(0)) {
                expresionNueva += valor;
            } else {
                expresionNueva += expresion.charAt(i);
            }
        }
        System.out.println("expresion nueuva: " + expresionNueva);
        return expresionNueva;
    }

}
