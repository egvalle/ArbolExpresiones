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
        char[] noVariables = {'(', ')', '√', '^', '*', '/', '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
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
    public Stack<String> conversionPostorden(String expresion) {
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
        System.out.println("Expresion PostOrden [I-D-R]");
        for (String expresiones : expresionPostfija) {
            System.out.print(expresiones + " ");
        }
        System.out.println();
        return expresionPostfija;
    }

    //realiza la conversion de inorden a preorden
    public void conversionPreorden(String expresion) {
        //la primerma condicion es recorrer de derecha a izquierda la expresion
        Stack<String> expresionPreOrden = new Stack<>();
        Stack<String> operadores = new Stack<>();
        String numero = "";

//        int contadorPrueba = 0;
        for (int i = expresion.length() - 1; i >= 0; i--) {

            if (esOperador(String.valueOf(expresion.charAt(i))) || expresion.charAt(i) == ')') {
                operadores.add(String.valueOf(expresion.charAt(i)));
                //si encuentras el cierre entonces
            } else if (expresion.charAt(i) == '(') { //cuando encuentre un parentesis de cierre
                //recorre la pila mientras no este vacia  y que la cima de la pila sea distinta al parentesis de apertura
                while (!operadores.isEmpty() && !operadores.peek().equals(")")) {
                    expresionPreOrden.add(operadores.pop());
                }
                //cuando encuentres el parentesis de apertura sacalo
                operadores.pop();
            } else if (Character.isDigit(expresion.charAt(i))) {
                //concatenar la cadena de numeros
                numero = expresion.charAt(i) + numero;
                //si no es un digito corta y mandalo a la pila
                if (i == 0 || !Character.isDigit(expresion.charAt(i - 1))) {
//aniade al rsultado el numero 
                    expresionPreOrden.add(numero);
                    numero = "";
                }
                //si es un operador o abre un parentesis manda la expresion a la pila de operadores
            }
        }
        //por ultimo supongamos el caso (a+b)-(c-d)
        //como al haber parentesis se va a truncar hasta el abierto quedara el - en la pila recorreremos lo ultimo que tengag la pila affuera del bucle para obtener la raiz
        while (!operadores.isEmpty()) {
            expresionPreOrden.add(operadores.pop());
        }
        System.out.println("Expresion PreOrden  [R-I-D]");
        for (int i = expresionPreOrden.size() - 1; i >= 0; i--) {
            System.out.print(expresionPreOrden.get(i) + " ");
        }
        System.out.println();
    }

    //como estamos trabajando tanto con raices o division puede devolvernos valores double
    public void resultadoNotacionPolaca(Stack<String> expresionPila) {
        Stack<String> pilaOperadoresOperandos = new Stack<>();
        for (String dato : expresionPila) {
            if (!esOperador(dato)) {
                //ccrea un nodo del operando
                pilaOperadoresOperandos.add(dato);
                // los siguiente operandos
            } else if (esOperador(dato)) {
                //obtenemos el operadoor que vamooos a evaluar
                String Operador = dato;
                //otorgamos valor al operando derecho
                Double operandoDerecho = Double.valueOf(pilaOperadoresOperandos.pop());
                //otorgamos valor al operando izquierdo
                Double operandoIzquierdo = Double.valueOf(pilaOperadoresOperandos.pop());
                //obtenemos el resultadoo de evaluar lao operacion y poosteriormente la convertimos a string para guardarlo en la pila
                String resultado = String.valueOf(realizarOperacion(Operador, operandoDerecho, operandoIzquierdo));
                pilaOperadoresOperandos.add(resultado);
            }
        }
        System.out.println("El resultado de evaluar la notacion postFija es: " + pilaOperadoresOperandos.pop());
    }
// aqui evaluamos los opoeradores  y retornamos el resultadoo

    private double realizarOperacion(String operador, double operandoDerecha, double operandoIzquierda) {
        switch (operador) {
            case "+":
                return operandoDerecha + operandoIzquierda;
            case "-":
                return operandoDerecha - operandoIzquierda;
            case "*":
                return operandoDerecha * operandoIzquierda;
            case "/":
                return operandoDerecha / operandoIzquierda;
            case "^":
                return Math.pow(operandoIzquierda, operandoDerecha);
            case "√":
                //para utilizar mayor exactitud usamos 1.0 para permitir decimales
                //dado que java si cuenta con una funcion raiz pero limitada al indice 2
                //usamos x^1/2 que seria lo mismo que 2√x^1
                // el segundo parametro representa a 1/ operando -> operando^-1
                // el primero a ^ operandoDrecha
                // por ultimo ^OperandoDerecha/Operando Izquierda
                return Math.pow(operandoDerecha, 1.0 / operandoIzquierda);
        }
        //si ninguno coincide retorna 
        return 0.0;
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
