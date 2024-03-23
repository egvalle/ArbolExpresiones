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

    private char[] permitidos = {'(', ')', '√', '^', '*', '/', '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    //asigna valores a las variables ingresadas

    public String colocarVariables(String expresion, String caracter, String valor) {
        //instanciamos la expresion nueva que vamos a retornar
        String expresionNueva = "";
        //recorremos
        for (int i = 0; i < expresion.length(); i++) {
            //si el caracter coincide coon la variable
            if (expresion.charAt(i) == caracter.charAt(0)) {
                //el valor que le pasamos lo sustituimoos
                expresionNueva += valor;
            } else {
                //si no coincide seguimoos armando la expresion
                expresionNueva += expresion.charAt(i);
            }
        }
        System.out.println("expresion nueuva: " + expresionNueva);
        return expresionNueva;
    }
    //aqui revisa las variables ingresadas

    //se encarga de validar las vas variables
    public int verificarVariables(String expresion) {
        //verificamos que lo que le estamos pasando sean variables en la expresion en casoo las halla
        int contador = 0;
        //colocamos los numeros por que tambien vamos a podoer pasarle numeros ya en la expresion
        char[] noVariables = {'(', ')', '√', '^', '*', '/', '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        //recorremoos
        for (int i = 0; i < expresion.length(); i++) {
            //guardamos el caracter en una variable de tipo char
            char caracter = expresion.charAt(i);
            //creamos una flag que nos diggag si es variable oo noo
            boolean esVariable = true;
            //si el caracter esta en los no permitidos entonces vas a retornar falso y saldras del bucle por que ya encontraste algo
            for (char noVariable : noVariables) {
                if (caracter == noVariable) {
                    esVariable = false;
                    break;
                }
            }
            //si es variable osea no esta en no variables
            if (esVariable) {
                //anadie al contador
                contador++;
            }
        }
        //el contador noos va a servir para pedir tantas veces el numero de datos a ingresar
        //Por ejemplo: a+b -> seria 2 variables osea 2 
        // ya luegoo en el while del menu va a iterar 2 vecces y nos pedira la letra de la variable y su valor para pasarlo a la otora funcion  
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
    public Stack<String> conversionPreorden(String expresion) {
        //la primerma condicion es recorrer de derecha a izquierda la expresion
        Stack<String> expresionPreOrden = new Stack<>();
        Stack<String> operadores = new Stack<>();
        String numero = "";

//        int contadorPrueba = 0;
        for (int i = expresion.length() - 1; i >= 0; i--) {

            //si es operador o empieza con un parentesis cerrado ya que lo estamos recorriendo al revez agregalo a la pila de operadores
            if (esOperador(String.valueOf(expresion.charAt(i))) || expresion.charAt(i) == ')') {
                operadores.add(String.valueOf(expresion.charAt(i)));
                //si encuentras el parentesis que abre entonces limpia la pila de operadores hasta el parentesis de cierre
            } else if (expresion.charAt(i) == '(') { //cuando encuentre un parentesis de cierre
                //recorre la pila mientras no este vacia  y que la cima de la pila sea distinta al parentesis de cierre
                while (!operadores.isEmpty() && !operadores.peek().equals(")")) {
                  //lo operadores los agregea a la pila que vamos a retornar como resultado 
                    expresionPreOrden.add(operadores.pop());
                }
                //cuando encuentres el parentesis de apertura sacalo
                operadores.pop();
                //si el caracter es un numero
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
        //dado que agregamos de derecha a izquierda lo que tenemos que hacer para fformatearlo bien es volver a leerlo al revez
        System.out.println("Expresion PreOrden  [R-I-D]");
        for (int i = expresionPreOrden.size() - 1; i >= 0; i--) {
            System.out.print(expresionPreOrden.get(i) + " ");
        }
        System.out.println();
        return expresionPreOrden;
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
        System.out.println("El resultado de evaluar la Notacion Polaca es: " + pilaOperadoresOperandos.pop());
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

    //evalua el caracter que te pase si coincide devuelve true
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
                System.out.println("La expresión contiene caracteres no permitidos."+ expresion.charAt(i));
                return false;
            }
        }
        //si todos se aceptaron devuelve true
        System.out.println("La expresión es válida.");
        return true;
    }
}
