package com.mycompany.proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class metodosArbol {

    private char[] jerarquia5 = {'(', ')', '{', '}', '[', ']'};
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
    public Nodo Arbol(String expresion) {
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
        int j = 0;
        Nodo actual = new Nodo();
        //a+b -> b+a
        //10+23 -> 32+01
        for (int i = expresion.length() - 1; i >= 0; i--) {

            switch (expresion.charAt(i)) {
                //le da prioridad a los parentesis
                case '(':
                    pila.add(String.valueOf(expresion.charAt(i)));
                    break;
                case ')':
                    //en caso que halles
                    pila.remove('(');
                    break;
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
            //mientras la pila noo este vacia
            while (!pila.isEmpty()) {

                String valorIzquierdo = pila.pop(); // quita de arriba de la pila y ugarda el valor
                String valorRaiz = valorIzquierdo; //solo copia el dato para mams claridad

                if (!esOperador(valorIzquierdo)) {
                    // Si el elemento no es un operador, crea un nodo con ese elemento y asigna como hijo izquierdo de 'actual'
                    System.out.println(valorIzquierdo);
                    actual.setNodoIzquierda(new Nodo(valorIzquierdo));
                } else {
                    // Si el elemento es un operador, asigna ese elemento como el actual o raiz en este casco
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
            }
            pilaNodo.add(actual);
            actual = new Nodo();
            //se activa cuando tengag cosas
            while (!pilaNodo.isEmpty() && pilaNodo.size() < 2) {
                String valorDerecho = pila.pop(); // Desapila el siguiente elemento
                String valorRaiz = valorDerecho; //solo copia el dato para mams claridad
                if (!esOperador(valorDerecho)) {
                    actual.setNodoDerecha(new Nodo(valorDerecho));
                } else {
                    actual.setActual(valorRaiz);
                }
                actual.setNodoIzquierda(pilaNodo.pop());
            }
            pilaNodo.add(actual);
        }
        //en este caso se agregaria para ponerse a la izquierda
        Arbol = actual;
        return Arbol;
    }

    private boolean esOperador(String caracter) {
        switch (caracter) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case "√":
            case "(":
            case ")":
                return true;
            default:
                return false;
        }
    }

    private boolean validarExpresion(String expresion) {
        for (int i = 0; i < expresion.length(); i++) {
            boolean encontrado = false;
            for (char caracter : permitidos) {
                if (expresion.charAt(i) == caracter) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("La expresión contiene caracteres no permitidos.");
                return false;
            }
        }
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
