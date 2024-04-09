/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyecto;

/**
 *
 * @author victo
 */
import java.util.*;
import javax.swing.JFrame;

public class Proyecto {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        //contiene las validaciones
        Validacion validar = new Validacion();
        //es lo que le mandos a pintar
        Nodo ArbolExpresion = new Nodo();
        //contiene los metodos del arbol
        Arbol arbol = new Arbol();
        //Pila que retorna validacion
        Stack<String> expresionPostfija = new Stack<String>();
        //En esta pila nos va a retornar la expresion polaca o preorden pero estara al reves por lo cual hay que leerla al reves para evaluarla en validacioon
        Stack<String> expresionPolaca = new Stack<String>();
        //expresionInicial
        String expresion = "";
        //la expresion formateada
        String expresionNueva = "";
        //indicar seleccion
        Integer opcion;
        //contador para iterrar hasta
        int contador = 0;
        //variables ingresadas
        String variable = "";
        //valoir dado a las variables
        String valor = "";
        //Nodo raiz = new Nodo();
        System.out.println("Bienvenido a Proyecto 1!");

        do {
            System.out.println("Seleccione una opcion: ");
            System.out.println("Operaciones (+,-,*,/,^,#)");
            System.out.println("[1]\t Ingresar expresion");
            System.out.println("[2]\t Generar arbol");
            System.out.println("[3]\t Salir");
            opcion = entrada.nextInt();
            switch (opcion) {
                case 1:
                    entrada.nextLine();
                    System.out.println("Ingrese una expresion:");
                    expresion = entrada.nextLine();
                    if (validar.validarExpresion(expresion) == false) {
                        break;
                    }
                    validar.verificarVariables(expresion);
                    expresionNueva = expresion;
                    while (contador < validar.verificarVariables(expresion)) {
                        System.out.println("Ingrese la letra de la variable al que desea asignar un valor: ");
                        variable = entrada.nextLine();
                        System.out.println("Debe ingresar un numero a la variable: " + variable);
                        valor = entrada.nextLine();
                        //aqui sustituimos los valores ya sean a b ...
                        expresionNueva = validar.colocarVariables(expresionNueva, variable, valor);
                        contador++;
                    }
                    //preorodenRID
                    //inordenIRD
                    //postorden IDR
                     expresionPostfija = validar.conversionPostorden(expresionNueva);
                    //asignamos la pila de postOrden a el arbool para coonstruirse
                    ArbolExpresion = arbol.ArbolExpresion(expresionPostfija);
                    //ya con el arbol construido podemos recorrerlo
                    System.out.println("Expresion InOrden   [I-R-D]");
                    arbol.recorrerInorden(ArbolExpresion);
                    System.out.println();
                    /*
                    System.out.println("Expresion PreOrdene   [R-I-D]");
                    arbol.recorrerPreorden(ArbolExpresion);
                    System.out.println();
                     */
//retornamos una pila al reves de validacion
                    //expresionPolaca = validar.conversionPreorden(expresionNueva);
                    System.out.println("Expresion PreOrden   [R-I-D]");
                    arbol.recorrerPreorden(ArbolExpresion);
                    System.out.println();

//aqui tengo que pasarle la pila a funcion para evaluar [recordar]
                    String resultado = validar.resultadoNotacionPolaca(ArbolExpresion);
                    System.out.println("El resultado de evaluar la Notación Polaca es: " + resultado);

                    break;
                case 2:
                    Pintar(ArbolExpresion);
                    break;
            }
        } while (opcion != 3);
    }
//aqui muestra el arbolito
////(12+1)-(3-2)+5
    //pruebas //(12+3)-(5-3)+3

    public static void Pintar(Nodo Arbol) {
        JFrame frame = new JFrame("Árbol de Expresión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Construye el árbol de expresión (aquí asumiendo que ya tienes el método construirArbolExpresion)
        ArbolGrafico panel = new ArbolGrafico(Arbol);

        frame.add(panel);
        frame.setVisible(true);
    }

}
