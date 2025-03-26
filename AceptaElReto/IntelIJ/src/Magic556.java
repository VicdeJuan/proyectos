import java.util.*;

class Carta implements Comparable{
    final public int valor;
    final public int coste;

    public Carta(int c){
        coste = c;
        valor = c;
    }

    @Override
    public int compareTo(Object o) {
        return (this.valor - ((Carta) o).valor);
    }
}

class Combo implements Comparable<Combo>{
    private Carta card; // Como objeto para pasar la referencia y no repetir cartas
    private int val;
    private int coste;
    private int numSubCombos;
    Combo subc1,subc2;

    public Combo(Carta i){
        if (i == null){
            System.out.println("ERROR GRAVE");
        }
        card = i;
        val = i.valor;
        coste = i.coste;
        numSubCombos = 1;
        subc1 = null;
        subc2 = null;
    }

    public Combo(Combo c, Carta i){
        this(c,new Combo(i));
    }

    public Combo(Combo c1, Combo c2){
        numSubCombos = c1.getNumSubCombos() + c2.numSubCombos;
        subc1 = c1;
        subc2 = c2;
        val =  c1.getVal() + c2.getVal();
        this.card = null;
        if (c1.getNumSubCombos() == 1){
            this.coste = c1.coste + c2.coste;
        } else{
            this.coste = 2*c1.coste + c2.coste;
        }
    }

    public int getVal(){
        return this.val;
    }

    public int getNumSubCombos() {
        return numSubCombos;
    }

    public int getCoste(){
        return this.coste;
    }

    private void _setCoste(int val){
        this.coste = val;
    }

    private void setCoste(){
        if (this.getNumSubCombos() == 1){
            this._setCoste(this.getVal());
        }else{
            this._setCoste(calculaCoste(this));
        }
    }
    private static int calculaCoste(Combo c){
        if (c == null)
            return 0;

        if (c.getNumSubCombos() == 1){
            return c.getVal();
        } else if (c.getNumSubCombos() == 2) {
            return calculaCoste(c.subc1)+calculaCoste(c.subc2);
        }else {
            return c.getVal() + calculaCoste(c.subc1) + calculaCoste(c.subc2);
        }
    }



    @Override
    public int compareTo(Combo c){
        return this.getVal() - c.getVal();
    }

    // Para comparar las referencias de las cartas ya introducidas.
    public boolean contains(Carta card){
        /*if ((card.valor == 3) && (this.val == 3)){
            System.out.println("Comparando treses");
        }*/
        if (subc1 != null && subc2 != null)
            // Comparo con == para comparar referencias y no valores.
            // Puede haber 2 cartas con el mismo coste pero que sean objetos diferentes
            return subc1.contains(card) || subc2.contains(card) || this.card == card;
        return this.card == card;
    }

    @Override
    public String toString(){
        if ((subc1 == null) && (subc2 == null))
            return "("+coste+","+getVal()+")";
        else
            return "{"+subc1 + " + " + subc2 + " = " + "("+coste+","+getVal()+")}";
    }
}
public class Magic556 {

    /*
    La estructura de datos a utilizar es una tabla (implementada como lista de listas) en la que cada elemento es un
    set ordenado de combos posibles de cartas.

    Las columnas representan costes de maná, las filas representan el número de cartas combinadas.

    De esta forma, abajo a la derecha de la tabla estará el conjunto de combos posibles de exactamente n cartas
    y, en ese set, habrá un combo de valor máximo. Compararemos el máximo de todos los combos de la última columna.

    Tal vez podría ahorrarme la lista de listas y podría y rodo en una única lista, pero no quiero. Me resulta más fácil
    pensar en formato tabla que en lista enlazada.



    Utilizo una lista enlazada porque se van a hacer muchas escrituras y no tantas lecturas (y se hace más eficiente
    que con un ArrayList).
     */
    public static void main(String[]args){

        Scanner sc = new Scanner(System.in);

        boolean keep_going = true;
        boolean log = false;

        while(keep_going) {
            String[] leido = sc.nextLine().split(" ");
            int mana = Integer.parseInt(leido[0]);
            if (mana == 0)
                break;
            int numCartas = Integer.parseInt(leido[1]);

            ArrayList<Carta> valores = new ArrayList<Carta>(numCartas);
            String[] valores_cartas = sc.nextLine().split(" ");

            for (int i = 0; i < numCartas; i++) {
                valores.add(new Carta(Integer.parseInt(valores_cartas[i])));
            }

            // Ordenamos los valores de maná.
            Collections.sort(valores);

            // Creamos la tabla, de momento vacía.
            LinkedList<LinkedList<LinkedList<Combo>>> tabla = new LinkedList<>();

            // Rellenamos la primera fila
            LinkedList<LinkedList<Combo>> fila = new LinkedList<>();
            // TODO
            for (int i = 0; i < mana; i++)
                fila.add(new LinkedList<Combo>());

            Combo solucion = new Combo(valores.getFirst());

            // Relleno la primera fila con los valores obtenidos.
            // añadiendo al set el combo de un único valor.
            for (Carta val : valores) {
                fila.get(val.valor - 1).add(new Combo(val));
            }

            tabla.add(fila);

            if(log) {
                System.out.println("Fila 0");
                for (List s : fila)
                    System.out.println("\t"+s);
            }

            // Iterativamente, rellanmos la fila 2 hasta la n de la mochila
            for (int fila_idx = 1; fila_idx < numCartas ; fila_idx++) {
                // k es la fila en la que estamos.

                LinkedList<LinkedList<Combo>> fila_anterior = tabla.get(fila_idx - 1);
                // Creo una nueva fila y la añado a la tabla.
                LinkedList<LinkedList<Combo>> fila_actual = new LinkedList<>();
                tabla.add(fila_actual);

                // Al lío:
                // OJO: col_idx representa el maná, no el índice de la columna. Para acceder a la columna, habrá que
                // restar 1.
                for (int col_idx = 1; col_idx <= mana; col_idx++) {


                    // Creamos y añadimos una celda nueva.
                    LinkedList<Combo> celda_actual = new LinkedList<>();
                    fila_actual.add(celda_actual);

                    // Recorremos todas las celdas anteriores, siguiendo el criterio de que col representa el maná y
                    // al acceder, se reta 1.
                    for (int col_prev = 1; col_prev <= col_idx ; col_prev++) {
                        LinkedList<Combo> celda_anterior = fila_anterior.get(col_prev-1);
                        if (celda_anterior.isEmpty()) continue;

                        // Creo todas las combinaciones de cartas y combos de la celda anterior
                        // Con  un poco de suerte, al utilizar Carta y no int, estoy pasando
                        // por referencia y no por valor, con lo que puedo comprobar si ya está.
                        for (Carta val : valores) {
                            if (val.valor > col_idx) break;
                            for (Combo c : celda_anterior) {
                                if (c.contains(val)) {
                                    //if (val == 3) System.out.println("BOL");
                                    continue; // Si ya tengo la carta, salto.
                                }
                                /*Si ya me he pasado del mana, termino el bucle porque los combos están ordenados en el set*/
                                if (c.getCoste() >= col_idx) break; //

                                Combo new_combo = new Combo(c, val);
                                if (new_combo.getCoste() <= col_idx) {
                                    if (solucion.getVal() < new_combo.getVal())
                                        solucion = new_combo;
                                    celda_actual.add(new_combo);
                                }
                            }
                        }
                    }


                }
                if(log) {
                    System.out.println("Fila "+fila_idx);
                    for (List s : fila_actual)
                        System.out.println("\t"+s);
                }
            }

            // SOLUCIÓN: Por el algoritmo de la mochila, el valor máximo de valor será el máximo combo que se encuentre
            // en la última posición de la tabla. Como es un SortedSet, puedo obtener el último elemento del set.

            System.out.println(solucion.getVal());
            /*Combo solucion = tabla.getLast().getLast().getLast();
            for (LinkedList<LinkedList<Combo>> lista : tabla) {
                Combo candidato = lista.getLast().getLast();
                if (solucion.compareTo(candidato) > 0) {
                    solucion = candidato;
                }
            }*/

        }
        sc.close();
    }
}
