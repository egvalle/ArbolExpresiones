# Proyecto 1, Programación 3
**Integrantes:**

Victor Alfredo Macario Enriquez 7690-22-5042

Edwin Geovany Valle Benito

Marvin Geobany Reyna Ortega 7690-22-8291


## Descripcion de Proyecto

Consiste en realizar una aplicación que reciba en una caja de texto una expresión matemática limitada en sumas, restas, multiplicaciones, divisiones, potencias y raices. Un ejemplo es a + b – (c – b) + e. La aplicación debe tener la capacidad de validar los caracteres permitidos para evitar excepciones, si la expresión ingresada contiene variables el siguiente paso es solicitar los valores de dichas variables. Luego mostrar los recorridos del árbol y evaluar el recorrido postorden, este recorrido como notación polaca o postfija para que investigue cómo evaluarlo.

### Clases del Proyecto en Java

* nodo.java

* ArbolGrafico.java

* Proyecto.java

* Validacion.java

* Arbol.java


### nodo.java

La clase "Nodo" representa un nodo en una estructura de árbol binario la cual se creo con lo siguientes atributos los cuales incian con el valor almacenado en el nodo y las referencias al nodo hijo izquierdo y al nodo hijo derecho.

```java
public class Nodo {

    private String actual;
    private Nodo nodoIzquierda;
    private Nodo nodoDerecha;

    public Nodo(String actual){
        this.actual = actual;
        this.nodoIzquierda = null;
        this.nodoDerecha = null;
    }
```

Seguimos con los constructores por lo que se agrega un constructor vacio para instanciar un nodo vacio.

```java
public Nodo(){
        
    }

    public String getActual() {
        return actual;
    }

    public Nodo getNodoIzquierda() {
        return nodoIzquierda;
    }

    public Nodo getNodoDerecha() {
        return nodoDerecha;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }
    
    public void setNodoIzquierda(Nodo nodoIzquierda) {
        this.nodoIzquierda = nodoIzquierda;
    }

    public void setNodoDerecha(Nodo nodoDerecha) {
        this.nodoDerecha = nodoDerecha;
    }
    
}
```
### ArbolGrafico.java

La clase "Arbol Grafico" se utilizará para representar en forma grafica un arbol binario para lo cual se utilizara un JPanel para permitir su integración en una interfaz gráfica para el usuario.

```java
public class ArbolGrafico extends JPanel {

    private static final int DIAMETRO = 30;
    private static final int RADIO = DIAMETRO / 2;
    private static final int ANCHO = 50;

    private final Nodo arbol;
```
Siendo esta última linea el arbol binario que se va a representar gráficamente.

Siguiendo nuestro código en esta parte hará que se establezca un color de fondo para el panel

```java
public ArbolGrafico(Nodo arbol) {
        this.arbol = arbol;
        setBackground(Color.WHITE);
```

El método de paintComponent es el que dibuja el arbol binario y este se invoca cuando el panel necesita ser redibujado. Existe un objeto de tipo "Graphics" el cual se utiliza para especificar el contexto en el que se realizará el dibujo de los nodos en el arbol.

```java
 protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //cambiando el color del contenido de los nodos
        g.setColor(Color.RED);
        pintar(g, getWidth() / 2, 20, arbol);
    }
```
Este es un metodo auxiliar recursivo para dibujar los nodos en el arbol, el parametro Graphics g es como se menciono anteriormente por lo que veremos cual es el arbol que se va a dibujar  x, y y el nodo, que es el nodo actual que se va a dibujar.

```java
    private void pintar(Graphics g, int x, int y, Nodo nodo) {
        if (nodo == null) {
            return; 
        }
        int Ajuste = nodosConAmbosHijos(nodo) * (ANCHO / 2); // Cálculo del espacio extra para nodos con ambos hijos

        g.drawOval(x + 3, y - 5, 40, 40); // Dibuja el círculo que encierra el nodo
        g.setColor(new Color(139, 69, 19)); // Establece el color de las líneas
        g.drawString(nodo.getActual(), x + 12, y + 18); // Dibuja el contenido del nodo

        if (nodo.getNodoIzquierda() != null) {
            g.drawLine(x + RADIO, y + RADIO, x - ANCHO - Ajuste + RADIO, y + ANCHO + RADIO); // Dibuja la línea al nodo hijo izquierdo
            g.setColor(Color.GREEN); // Establece el color del nodo hijo izquierdo
            pintar(g, x - ANCHO - Ajuste, y + ANCHO, nodo.getNodoIzquierda()); // Dibuja el nodo hijo izquierdo recursivamente
        }
        if (nodo.getNodoDerecha() != null) {
            g.drawLine(x + RADIO, y + RADIO, x + ANCHO + Ajuste + RADIO, y + ANCHO + RADIO); // Dibuja la línea al nodo hijo derecho
            g.setColor(Color.GREEN); // Establece el color del nodo hijo derecho
            pintar(g, x + ANCHO + Ajuste, y + ANCHO, nodo.getNodoDerecha()); // Dibuja el nodo hijo derecho recursivamente
        }
    }
```

El método auxiliar recursivo que cuenta el numero de nodos con ambos hijos no nulos. Se utiliza el parametro Nodo que es el nodoc acual que se esta evaluando y nos regresara el numero de nodos con ambos hijos no nulos.

```java
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
```

### Proyecto.java

La clase Proyecto es la clase principal que contiene el método main y maneja la interacción con el usuario detallando cada linea dentro del código

```java
public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        //contiene las validaciones
        Validacion validar = new Validacion();
        //es lo que le enviamos a pintar
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
        //contador para iterar hasta ....
        int contador = 0;
        //variables ingresadas
        String variable = "";
        //valor dado a las variables
        String valor = "";
        //Nodo raiz = new Nodo();
        System.out.println("Bienvenido a Proyecto 1!");
```

Este fragmento de código representa un bucle do-while que muestra un menú de opciones al usuario y ejecuta diferentes acciones según la opción seleccionada. Aquí está la explicación de cada parte del código:

Bucle do-while: Este bucle se ejecuta al menos una vez y luego verifica la condición en la parte inferior del bucle para determinar si debe continuar ejecutándose.

Dentro del Case 1: 
* El usuario puede ingresar una expresión matemática.
* Se valida la expresión ingresada.
* Se solicita al usuario que ingrese valores para las variables presentes en la expresión.
* Se realiza la sustitución de las variables por sus valores correspondientes en la expresión.
* Se convierte la expresión a notación postfija.
* Se construye el árbol de expresión a partir de la expresión postfija.
* Se muestra la expresión en orden inorden (I-R-D) recorriendo el árbol.
* Se convierte la expresión a notación prefija (preorden).

```java
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

expresionPostfija = validar.conversionPostorden(expresionNueva);
 //asignamos la pila de postOrden a el arbool para coonstruirse
    ArbolExpresion = arbol.ArbolExpresion(expresionPostfija);
    //ya con el arbol construido podemos recorrerlo
    System.out.println("Expresion InOrden   [I-R-D]");
    arbol.recorrerInorden(ArbolExpresion);
    System.out.println();
    //retornamos una pila al reves de validacion
    expresionPolaca = validar.conversionPreorden(expresionNueva);
    validar.resultadoNotacionPolaca(expresionPolaca);
```

Dentro del Case 2: Se llama a una función Pintar() que, presumiblemente, tiene la funcionalidad de representar gráficamente el árbol de expresión generado. Esto puede ser útil para visualizar la estructura del árbol.

En public static void Pintar (Nodo Arbol) es responsable de visualizar un árbol de expresión de manera gráfica en una ventana. Toma como entrada el nodo raíz del árbol (Nodo Arbol) y crea una nueva ventana JFrame para mostrar el árbol. Luego, crea un objeto de la clase ArbolGrafico pasando el nodo raíz como parámetro para construir la representación gráfica del árbol. Finalmente, agrega este panel a la ventana y la hace visible para que el árbol de expresión sea mostrado al usuario.

```java
public static void Pintar(Nodo Arbol) {
        JFrame frame = new JFrame("Árbol de Expresión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Construye el árbol de expresión (aquí asumiendo que ya tienes el método construirArbolExpresion)
        ArbolGrafico panel = new ArbolGrafico(Arbol);

        frame.add(panel);
        frame.setVisible(true);
    }
```

### Arbol.java

Esta clase Arbol se encarga de construir y manipular un árbol de expresión a partir de una expresión postfija. Contiene métodos para construir el árbol de expresión, recorrerlo en orden (inorden) y almacenar el nodo raíz del árbol. La clase también utiliza un objeto de la clase Validacion para validar expresiones y operadores.

private Nodo construirArbolExpresion(Stack<String> expresionPostfija): Este método toma una expresión en notación postfija (también conocida como notación polaca inversa) como entrada en forma de una pila de cadenas (expresionPostfija). Luego, construye y devuelve un árbol de expresión correspondiente a esa expresión postfija. El método recorre la expresión postfija y crea nodos para los operandos y operadores encontrados. Cuando encuentra un operador, crea un nuevo nodo con ese operador como valor y asigna los dos nodos superiores de la pila como sus hijos izquierdo y derecho. Finalmente, devuelve el nodo raíz del árbol de expresión construido.

```java
private Nodo construirArbolExpresion(Stack<String> expresionPostfija) {
        Stack<String> pilaPostfija = expresionPostfija; // Pila que contiene la expresión postfija
        Stack<Nodo> pilaNodo = new Stack<>(); // Pila de nodos del árbol de expresión

        // Recorriendo la expresión postfija para construir el árbol de expresión
        for (String dato : pilaPostfija) {
            // Si el dato no es un operador, se agrega como un nodo de operando a la pila de nodos
            if (!validar.esOperador(dato)) {
                pilaNodo.add(new Nodo(dato));
            } else if (validar.esOperador(dato)) {
                // Si el dato es un operador, se construye un nodo de operador y se agregan sus hijos
                Nodo raiz = new Nodo(dato);
                Nodo hijoDerecho = pilaNodo.pop();
                Nodo hijoIzquierdo = pilaNodo.pop();
                raiz.setNodoDerecha(hijoDerecho);
                raiz.setNodoIzquierda(hijoIzquierdo);
                pilaNodo.add(raiz);
            }
        }
        return pilaNodo.pop(); // Se retorna el nodo raíz del árbol de expresión
    }
```
public void recorrerInorden(Nodo nodo): Este método realiza un recorrido inorden en el árbol binario cuyo nodo raíz se pasa como argumento (nodo). En un recorrido inorden, primero se visita el subárbol izquierdo, luego el nodo actual y finalmente el subárbol derecho. Este método imprime los valores de los nodos en orden inorden, lo que significa que los nodos se imprimirán en el orden correcto de la expresión matemática original si el árbol representa una expresión matemática.

```java
public void recorrerInorden(Nodo nodo) {
        if (nodo != null) {
            if (nodo.getNodoIzquierda() != null) {
                recorrerInorden(nodo.getNodoIzquierda());
            }
            System.out.print(nodo.getActual() + " ");
            if (nodo.getNodoDerecha() != null) {
                recorrerInorden(nodo.getNodoDerecha());
            }
```

### Validacion.java

La clase realiza una serie de validaciones para garantizar que las expresiones procesadas sean válidas y contengan solo caracteres permitidos. incluyendo varios metodos y atributos que se detallan a continuacion junto con el código:

```java
public class Validacion {

    private char[] permitidos = {'(', ')', '{', '}', '[', ']', '√', '^', '*', '/', '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    //asigna valores a las variables ingresadas

Esta función Java llamada colocarVariables toma una expresión, un caracter y un valor como parámetros y devuelve una nueva expresión donde todas las ocurrencias del caracter en la expresión original se sustituyen por el valor proporcionado.

    public String colocarVariables(String expresion, String caracter, String valor) {
        //instanciamos la expresion nueva que vamos a retornar
        String expresionNueva = "";
        //recorremos
        for (int i = 0; i < expresion.length(); i++) {
            //si el caracter coincide con la variable
            if (expresion.charAt(i) == caracter.charAt(0)) {
                //el valor que le pasamos lo sustituimoos
                expresionNueva += valor;
            } else {
                //si no coincide seguimos creando la expresion
                expresionNueva += expresion.charAt(i);
            }
        }
        System.out.println("expresion nueuva: " + expresionNueva);
        return expresionNueva;
    }

Esta función llamada verificarVariables toma una expresión como parámetro y devuelve el número de variables presentes en esa expresión. La función utiliza una estrategia de contar todos los caracteres que no están en una lista predefinida de caracteres que no se consideran variables.

    //aqui se revisa las variables ingresadas

    //se encarga de validar las variables
    public int verificarVariables(String expresion) {
        //verificamos que lo que le estamos pasando sean variables en la expresion en caso las halla
        int contador = 0;
        //colocamos los numeros por que tambien vamos a podoer pasarle numeros ya en la expresion
        char[] noVariables = {'(', ')', '√', '^', '*', '/', '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        //recorremoos
        for (int i = 0; i < expresion.length(); i++) {
            //guardamos el caracter en una variable de tipo char
            char caracter = expresion.charAt(i);
            //creamos una flag que nos diga si es variable o no
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
        //el contador nos va a servir para pedir tantas veces el numero de datos a ingresar
        //Por ejemplo: a+b -> seria 2 variables osea 2 
        // ya luegoo en el while del menu va a iterar 2 vecces y nos pedira la letra de la variable y su valor para pasarlo a la otora funcion  
        return contador;
    }

Esta función, llamada conversionPostorden, realiza la conversión de una expresión matemática en notación infija a notación postfija (también conocida como notación polaca inversa). La notación postfija es útil para evaluar expresiones matemáticas de manera eficiente utilizando una pila.    
    //conversion de inorden a postoorden

    //realiza la conversion de la expresion pero tambien retorna ya una pila de string para convertir a nodo en arbol
    public Stack<String> conversionPostorden(String expresion) {
        //la primera condicion es recorrer de izquierda a derecha la expresion
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
                // Si estamos en el último carácter de la expresión o el carácter siguiente no es un dígito,
// significa que el número actual ha terminado y debe agregarse a la pila expresionPostfija.
                if (i == expresion.length() - 1 || !Character.isDigit(expresion.charAt(i + 1))) {
//añade al resultado el numero 
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
        //como al haber parentesis se va a truncar hasta el abierto quedara el - en la pila recorreremos lo ultimo que tenga la pila afuera del bucle para obtener la raiz
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

La función conversionPreorden convierte una expresión matemática de notación infija a notación preorden (también conocida como notación polaca). Esta notación se caracteriza por tener los operadores antes de sus operandos.

    //realiza la conversion de inorden a preorden
    public Stack<String> conversionPreorden(String expresion) {
        //la primera condicion es recorrer de derecha a izquierda la expresion
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
                  //lo operadores los agrega a la pila que vamos a retornar como resultado 
                    expresionPreOrden.add(operadores.pop());
                }
                //cuando encuentres el parentesis de apertura sacalo
                operadores.pop();
                //si el caracter es un numero
            } else if (Character.isDigit(expresion.charAt(i))) {
                //concatenar la cadena de numeros
                numero = expresion.charAt(i) + numero;
                //si no es un digito corta y mandalo a la pila
                // Si estamos en el primer carácter de la expresión o el carácter anterior no es un dígito,
                // significa que el número actual ha terminado y debe agregarse a la pila expresionPreOrden.
                if (i == 0 || !Character.isDigit(expresion.charAt(i - 1))) {
//añade al resultado el numero 
                    expresionPreOrden.add(numero);
                    numero = "";
                }
                //si es un operador o abre un parentesis manda la expresion a la pila de operadores
            }
        }
        //por ultimo supongamos el caso (a+b)-(c-d)
        //como al haber parentesis se va a truncar hasta el abierto quedara el - en la pila recorreremos lo ultimo que tenga la pila afuera del bucle para obtener la raiz
        while (!operadores.isEmpty()) {
            expresionPreOrden.add(operadores.pop());
        }
        //dado que agregamos de derecha a izquierda lo que tenemos que hacer para formatearlo bien es volver a leerlo al revez
        System.out.println("Expresion PreOrden  [R-I-D]");
        for (int i = expresionPreOrden.size() - 1; i >= 0; i--) {
            System.out.print(expresionPreOrden.get(i) + " ");
        }
        System.out.println();
        return expresionPreOrden;
    }
    
La función resultadoNotacionPolaca calcula el resultado de una expresión matemática dada en notación polaca (postfija) utilizando una pila para llevar a cabo las operaciones.  

    //como estamos trabajando tanto con raices o division puede devolvernos valores double
    public void resultadoNotacionPolaca(Stack<String> expresionPila) {
        Stack<String> pilaOperadoresOperandos = new Stack<>();
        for (String dato : expresionPila) {
            if (!esOperador(dato)) {
                //crea un nodo del operando
                pilaOperadoresOperandos.add(dato);
                // los siguiente operandos
            } else if (esOperador(dato)) {
                //obtenemos el operador que vamos a evaluar
                String Operador = dato;
                //otorgamos valor al operando derecho
                Double operandoDerecho = Double.valueOf(pilaOperadoresOperandos.pop());
                //otorgamos valor al operando izquierdo
                Double operandoIzquierdo = Double.valueOf(pilaOperadoresOperandos.pop());
                //obtenemos el resultadoo de evaluar la operacion y poosteriormente la convertimos a string para guardarlo en la pila
                String resultado = String.valueOf(realizarOperacion(Operador, operandoDerecho, operandoIzquierdo));
                pilaOperadoresOperandos.add(resultado);
            }
        }
        System.out.println("El resultado de evaluar la Notacion Polaca es: " + pilaOperadoresOperandos.pop());
    }

La función realizarOperacion toma un operador, un operando derecho y un operando izquierdo, y realiza la operación matemática correspondiente según el operador proporcionado. Este método se utiliza en el contexto de la evaluación de expresiones en notación polaca.

// aqui evaluamos los operadores  y retornamos el resultadoo

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
                // el primero a ^ operandoDerecha
                // por ultimo ^OperandoDerecha/Operando Izquierda
                return Math.pow(operandoDerecha, 1.0 / operandoIzquierda);
        }
        //si ninguno coincide retorna 
        return 0.0;
    }

La función esOperador determina si un carácter dado es un operador matemático válido. Toma una cadena que representa un carácter como entrada y devuelve true si ese carácter es un operador (+, -, *, /, ^, o √), y false en caso contrario.

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

La función validarExpresion verifica si una expresión dada contiene únicamente caracteres permitidos. Devuelve true si todos los caracteres de la expresión están permitidos y false si se encuentra al menos un caracter no permitido.

    public boolean validarExpresion(String expresion) {
        //recorrer el arbol
        for (int i = 0; i < expresion.length(); i++) {
            //usamos una bandera iniciada en falso para saber si ya encontramos alguna coincidencia
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
```

Se adjunta video de prueba del funcionamiento del codigo.

https://github.com/egvalle/ArbolExpresiones/assets/104967229/2170cf20-e91f-4731-a001-b9ca83c329b0
