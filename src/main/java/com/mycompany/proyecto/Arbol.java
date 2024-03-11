package com.mycompany.proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Arbol {

    private char[] jerarquia5 = {'(', ')'};
    private char[] jerarquia4 = {'√', '^'};
    private char[] jerarquia3 = {'*', '/'};
    private char[] jerarquia2 = {'+', '-'};
    private char[] jerarquia1 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private char[] permitidos = {'(', ')', '{', '}', '[', ']', '√', '^', '*', '/', '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public Nodo Arbol = new Nodo();

    //alt scroll cambiar zoom
    //a+b-(c-d)+e
    //e+(d-c)-b+a
    //a+b-c
    //(c-b)+a
    public Nodo ArbolExpresion(String expresion) {
        if (validarExpresion(expresion)) {
            Arbol = construirArbolExpresion(expresion);
            System.out.println("Raíz del árbol de expresión: " + Arbol.getActual());
            mostrarArbol(Arbol);

            return Arbol;

        }
        return Arbol;
    }

    private Nodo construirArbolExpresion(String expresion) {
        //la pila debe contener caracteres
        Stack<String> pila = new Stack<>();
        Stack<Nodo> pilaNodo = new Stack<>();
        String numero = "";
        Nodo actual = new Nodo();
//En el casoo de que se recorra de Izquierda a derecha queda de la siguiente manera        
//a+b -> b+a
        //10+23 -> 32+01

        //con  la correciion el arbol se genera
        // a+b -> a+b
        for (int i = expresion.length() - 1; i >= 0; i--) {

            switch (expresion.charAt(i)) {
                //le da prioridad a los parentesis
                case '(':
                case ')':
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                case '√':
                    pila.add(String.valueOf(expresion.charAt(i)));
                    break;
                default:
                    if (Character.isDigit(expresion.charAt(i))) {
                        numero = expresion.charAt(i) + numero;
                        if (i == 0 || !Character.isDigit(expresion.charAt(i - 1))) {
                            pila.add(numero);
                            numero = "";
                        }
                    }
                    break;
            }
        }//fin ciclo for
        //mientras la pila noo este vacia
        while (!pila.isEmpty()) {
            if (pila.peek().equalsIgnoreCase("(")) {
                pila.pop();
                continue;
            }
            if (!pila.peek().equalsIgnoreCase(")")) {
                String valorIzquierdo = pila.pop(); // quita de arriba de la pila y guarda el valor
                String valorRaiz = valorIzquierdo; // solo copia el dato para mayor claridad
                if (!esOperador(valorIzquierdo)) {
                    // Si el elemento no es un operador, crea un nodo con ese elemento y asigna como hijo izquierdo de 'actual'
                    actual.setNodoIzquierda(new Nodo(valorIzquierdo));
                } else {
                    // Si el elemento es un operador, asigna ese elemento como el actual o raíz en este caso
                    actual.setActual(valorRaiz);
                    // Comprueba si hay más elementos en la pila para asignar el hijo derecho   
                    if (!pila.isEmpty()) {
                        String valorDerecho = pila.pop(); // Desapila el siguiente elemento
                        if (!esOperador(valorDerecho)) {
                            // Si el siguiente elemento no es un operador, crea un nodo con ese elemento y asigna como hijo derecho de 'actual'
                            actual.setNodoDerecha(new Nodo(valorDerecho));
                        }
                    }
                }
            } else {
                Arbol = actual;
                pilaNodo.add(actual);
                break;
            }

        }//fin ciclo while
        pila.clear();
        actual = new Nodo();
//agregamos el nodo a la pila de nodos        

//reseteamos el nodo
        //se activa cuando tengag cosas
        /*
            while (!pilaNodo.isEmpty()) {
                //vas a obtener una hoja del lado derecho
                String valorDerecho = pila.pop();
                if (!esOperador(valorDerecho)) {
                    actual.setNodoDerecha(new Nodo(valorDerecho));
                } else {
                    if (!pila.isEmpty()) {
                        String valorRaiz = pila.pop(); //solo copia el dato para mams claridad
                        if (esOperador(valorRaiz)) {
                            // Si el siguiente elemento no es un operador, crea un nodo con ese elemento y asigna como hijo derecho de 'actual'
                            actual.setActual(valorRaiz);
                        }
                    }
                }
                actual.setNodoIzquierda(pilaNodo.pop());
            }
            pilaNodo.add(actual);
         */
        //en este caso se agregaria para ponerse a la izquierda
       
        return Arbol;
    }
//evalua el caracter que te pase si coincide devuelve trueu

    private boolean esOperador(String caracter) {
        switch (caracter) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case "√":
                /*case "(":
            case ")":*/
                return true;
            default:
                return false;
        }
    }

    private boolean validarExpresion(String expresion) {
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

    public void mostrarArbol(Nodo nodo) {
        if (nodo != null) {
            System.out.println(" " + nodo.getActual() + " ");
            if (nodo.getNodoIzquierda() != null) {
                System.out.println("Subárbol izquierdo [<-]:");
                mostrarArbol(nodo.getNodoIzquierda());
            }
            if (nodo.getNodoDerecha() != null) {
                System.out.println("Subárbol derecho [->]:");
                mostrarArbol(nodo.getNodoDerecha());
            }
        }
    }
}
