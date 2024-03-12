/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author victo
 */
public class Validacion {

    private char[] permitidos = {'(', ')', '{', '}', '[', ']', '√', '^', '*', '/', '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    //asigna valores a las variables ingresadas

    public String colocarVariables(String expresion, String caracter, String valor) {
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
    //aqui revisa las variables ingresadas

    //se encarga de validar las vas variables
    public int verificarVariables(String expresion) {
        List<Character> variables = new ArrayList<>();
        int contador = 0;
        char[] noVariables = {'(', ')', '√', '^', '*', '/', '+', '-','0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
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
        return contador;
    }
    //conversion de inorden a postoorden

    //realiza la conversion de la expresion pero tambien retorna ya una pila de string para convertir a nodo en arbol
    public Stack<String> conversion(String expresion) {
        //la primerma condicion es recorrer de izquierda a derecha la expresion
        Stack<String> expresionPostfija = new Stack<>();
        Stack<String> operadores = new Stack<>();
        String numero = "";
//        int contadorPrueba = 0;
        for (int i = 0; i < expresion.length(); i++) {

//si es un numero
            if (Character.isDigit(expresion.charAt(i))) {
                //concatenar la cadena de numeros
                numero += expresion.charAt(i);
                //si no es un digito corta y mandalo a la pila
                if (i == expresion.length() - 1 || !Character.isDigit(expresion.charAt(i + 1))) {
//aniade al rsultado el numero 
                    expresionPostfija.add(numero);
                    numero = "";
                }
                //si es un operador o abre un parentesis manda la expresion a la pila de operadores
            } else if (esOperador(String.valueOf(expresion.charAt(i))) || expresion.charAt(i) == '(') {
                operadores.add(String.valueOf(expresion.charAt(i)));
                //si encuentras el cierre entonces
            } else if (expresion.charAt(i) == ')') { //cuando encuentre un parentesis de cierre
                //recorre la pila mientras no este vacia  y que la cima de la pila sea distinta al parentesis de apertura
                while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
                    expresionPostfija.add(operadores.pop());
                }
                //cuando encuentres el parentesis de apertura sacalo
                operadores.pop();
            }
        }
        //por ultimo supongamos el caso (a+b)-(c-d)
        //como al haber parentesis se va a truncar hasta el abierto quedara el - en la pila recorreremos lo ultimo que tengag la pila affuera del bucle para obtener la raiz
        while (!operadores.isEmpty()) {
            expresionPostfija.add(operadores.pop());
        }
        System.out.println("Expresion postfija ");
        for (String expresiones : expresionPostfija) {
            System.out.print(expresiones + " ");
        }
        System.out.println();
        return expresionPostfija;
    }

    //evalua el caracter que te pase si coincide devuelve trueu
    public boolean esOperador(String caracter) {
        switch (caracter) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case "√":
                return true;
            default:
                return false;
        }
    }

    public boolean validarExpresion(String expresion) {
        //recorrer el arbol
        for (int i = 0; i < expresion.length(); i++) {
            //usamos una bandera iniciada en falso para saber si ya encotnramos alguna coincidencia
            boolean encontrado = false;
            //recorremos los caracteres que deseamos que se acepten
            for (char caracter : permitidos) {
                //si coincide retorna  true y vuelve a empezar
                if (expresion.charAt(i) == caracter) {
                    encontrado = true;
                    break;
                }
            }
            //si no lo encontraste no es una expresion valida
            if (!encontrado) {
                System.out.println("La expresión contiene caracteres no permitidos.");
                return false;
            }
        }
        //si todos se aceptaron devuelve true
        System.out.println("La expresión es válida.");
        return true;
    }
}
