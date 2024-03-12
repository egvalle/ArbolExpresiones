/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto;

//primmer commit
//probandoo commits
/**
 *
 * @author victo
 */
import javax.swing.*;
import java.awt.*;

public class ArbolGrafico extends JPanel {

    //(12+1)-(3-2)+5
    private static final int DIAMETRO = 30;
    private static final int RADIO = DIAMETRO / 2;
    private static final int ANCHO = 50;
    //private static final int ANCHOPARANOCHOCAR = 50;

    private Nodo arbol;

    public ArbolGrafico(Nodo arbol) {
        this.arbol = arbol;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arbol != null) {
            dibujarNodo(arbol, getWidth() / 2, 20, g);
        }
    }

    private void dibujarNodo(Nodo nodo, int x, int y, Graphics g) {
    
        // Dibujar el nodo actual
        g.drawOval(x - 15, y - 15, 25, 25);
        g.drawString(String.valueOf(nodo.getActual()), x - 5, y + 5);

        // Definir la distancia horizontal entre los nodos hijos
        final int horizontalOffset = 200;

        // Dibujar nodo izquierdo si existe
        if (nodo.getNodoIzquierda() != null) {
            dibujarNodo(nodo.getNodoIzquierda(), x - horizontalOffset, y + 50, g);
            g.drawLine(x, y, x - horizontalOffset, y + 50);
        }

        // Dibujar nodo derecho si existe
        if (nodo.getNodoDerecha() != null) {
            dibujarNodo(nodo.getNodoDerecha(), x + horizontalOffset, y + 50, g);
            g.drawLine(x, y, x + horizontalOffset, y + 50);
        }
    }

}
