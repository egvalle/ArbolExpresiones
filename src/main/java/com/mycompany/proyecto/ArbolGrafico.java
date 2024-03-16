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

import javax.swing.*;
import java.awt.*;

public class ArbolGrafico extends JPanel {

 

    private final Nodo arbol;

    public ArbolGrafico(Nodo arbol) {
        this.arbol = arbol;
        //cambiando el coolor del foondo
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //cambiando el color del coontenido de los nodos
        g.setColor(Color.RED);
        pintar(g, getWidth() / 2, 20, arbol);
    }

    private void pintar(Graphics g, int x, int y, Nodo nodo) {
        if (nodo == null) {
            return; 
         //Si ya no hay nodos termina
        }
        // Cálculo del espacio extra para los nodos completos
        int Ajuste = nodosConAmbosHijos(nodo) * 25;
//aqui dibuja el circulo que encierra los operandos y ooperadores
        g.drawOval(x + 3, y - 5, 40, 40);
        //darle color a las lineas
        g.setColor(new Color(139, 69, 19)); // Color cafe en rgb
        // Dibujando el contenido del nodo
        g.drawString(nodo.getActual(), x + 12, y + 18);

        if (nodo.getNodoIzquierda() != null) {
            //aqui dibuja la linea
            g.drawLine(x + 15, y + 15, x - 50 - Ajuste + 15, y + 50 + 15);
            g.setColor(Color.GREEN);
            pintar(g, x - 50 - Ajuste, y + 50, nodo.getNodoIzquierda());
        }
        if (nodo.getNodoDerecha() != null) {
            //aqui dibuja la linea
            g.drawLine(x + 15, y + 15, x + 50 + Ajuste + 15, y + 50 + 15);
            g.setColor(Color.GREEN);
            pintar(g, x + 50 + Ajuste, y + 50, nodo.getNodoDerecha());
        }
    }

    public int nodosConAmbosHijos(Nodo nodo) {
        //si el nodo es nulo no retornes nada
        if (nodo == null) {
            return 0;
        } else {
            //si no instancia un contador para  ver cuantos nodos si tienen ambas hojas
            int contador = 0;
            //si tiene ambas incrementa 1
            if (nodo.getNodoIzquierda() != null && nodo.getNodoDerecha() != null) {
                contador = 1;
            }
            //
            contador += nodosConAmbosHijos(nodo.getNodoIzquierda()) + nodosConAmbosHijos(nodo.getNodoDerecha());
            return contador;
        }
    }
}
