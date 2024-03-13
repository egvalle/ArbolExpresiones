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

    private Nodo construirArbolExpresion(Stack<String> expresionPostfija) {
        //la pila debe contener caracteres
        Stack<String> pilaPostfija = expresionPostfija;
        Stack<Nodo> pilaNodo = new Stack<>();
//En el casoo de que se recorra de Izquierda a derecha queda de la siguiente manera        
//a+b -> b+a
        for (String dato : pilaPostfija) {
            //el primer valor deberia ser la raiz osea un operador
            if (!validar.esOperador(dato)) {
                //ccrea un nodo del operando
                pilaNodo.add(new Nodo(dato));
                // los siguiente operandos
            } else if (validar.esOperador(dato)) {
                Nodo raiz = new Nodo(dato);
                Nodo hijoDerecho = pilaNodo.pop();
                Nodo hijoIzquierdo = pilaNodo.pop();
                raiz.setNodoDerecha(hijoDerecho);
                raiz.setNodoIzquierda(hijoIzquierdo);
                pilaNodo.add(raiz);
            }
        }
        return pilaNodo.pop();
    }
}

/*
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
 */
