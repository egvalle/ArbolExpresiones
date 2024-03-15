package com.mycompany.proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Arbol {

    /*
    private char[] jerarquia5 = {'(', ')'};
    private char[] jerarquia4 = {'√', '^'};
    private char[] jerarquia3 = {'*', '/'};
    private char[] jerarquia2 = {'+', '-'};
    private char[] jerarquia1 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
     */
    public Nodo Arbol = new Nodo();
    Validacion validar = new Validacion();

    //alt scroll cambiar zoom
    //a+b-(c-d)+e
    //e+(d-c)-b+a
    //a+b-c
    //(c-b)+a
    //ver de inffija a postfija consejo del inge
    public Nodo ArbolExpresion(Stack<String> expresionPostFija) {
        Arbol = construirArbolExpresion(expresionPostFija);
        System.out.println("Raíz del árbol de expresión: " + Arbol.getActual());
        return Arbol;
    }
    
    private Nodo construirArbolExpresion(Stack<String> expresionPostFija) {
        Stack<Nodo> pilaNodo = new Stack<>();
        Stack<String> pilaNodoTemporal = new Stack<>(); // Pila temporal para almacenar los nodos dentro del paréntesis

        for (String dato : expresionPostFija) {
            if (dato.equals("+") || dato.equals("-") || dato.equals("*") || dato.equals("/")) {
                Nodo raiz = new Nodo(dato);
                raiz.setNodoDerecha(pilaNodo.pop());
                raiz.setNodoIzquierda(pilaNodo.pop());
                pilaNodo.push(raiz);
            } else if (dato.equals("(")) {
                // Se encontró un paréntesis abierto, se inicia una nueva pila para los nodos dentro del paréntesis
                pilaNodoTemporal.clear();
                // Se sigue extrayendo elementos hasta encontrar el paréntesis de cierre
                while (!pilaNodo.peek().getActual().equals(")")) {
                    pilaNodoTemporal.push(pilaNodo.pop().getActual());
                }
                // Se quita el paréntesis de cierre de la pila principal
                pilaNodo.pop();
                // Se construye el árbol de expresiones dentro del paréntesis
                Nodo subArbol = construirArbolExpresion(pilaNodoTemporal);
                // Se agrega el subárbol a la pila principal
                pilaNodo.push(subArbol);
            } else {
                pilaNodo.push(new Nodo(dato));
            }
        }

        return pilaNodo.pop();
    }
    
}
