/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto;
//probandoo commits
/**
 *
 * @author victo
 */
import javax.swing.*;
import java.awt.*;

public class ArbolGrafico extends JPanel {
   
    private Nodo arbol;

    public ArbolGrafico(Nodo arbol) {
        this.arbol = arbol;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arbol != null) {
            dibujarNodo(arbol, getWidth() / 2, 30, g);
        }
    }

    private void dibujarNodo(Nodo nodo, int x, int y, Graphics g) {
        // Dibujar el nodo actual
        g.drawOval(x - 15, y - 15, 30, 30);
        g.drawString(String.valueOf(nodo.getActual()), x - 5, y + 5);

        // Dibujar nodos hijos
        if (nodo.getNodoIzquierda() != null) {
            dibujarNodo(nodo.getNodoIzquierda(), x - 100, y + 50, g);
            g.drawLine(x, y, x - 100, y + 50);
        }
        if (nodo.getNodoDerecha() != null) {
            dibujarNodo(nodo.getNodoDerecha(), x + 100, y + 50, g);
            g.drawLine(x, y, x + 100, y + 50);
        }
    }


}
