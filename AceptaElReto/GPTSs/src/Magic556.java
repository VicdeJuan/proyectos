import java.util.*;

class Combo implements Comparable<Combo> {
    private Integer card; // Referencia a la carta original (para evitar repeticiones)
    private int val;
    private int coste;
    private int numSubCombos;
    Combo subc1, subc2;

    // Constructor para una carta individual.
    public Combo(Integer i) {
        if (i == null) {
            System.out.println("ERROR GRAVE");
        }
        this.card = i;
        this.val = i;
        this.coste = i;
        this.numSubCombos = 1;
        this.subc1 = null;
        this.subc2 = null;
    }

    // Constructor que combina un Combo con una carta (se delega al de dos Combos).
    public Combo(Combo c, Integer i) {
        this(c, new Combo(i));
    }

    // Constructor que combina dos Combos.
    public Combo(Combo c1, Combo c2) {
        // El número de cartas del combo es la suma de las cartas de cada componente.
        this.numSubCombos = c1.getNumSubCombos() + c2.getNumSubCombos();
        // El valor es la suma de los valores.
        this.val = c1.getVal() + c2.getVal();
        // No hay carta "original" en un combo compuesto.
        this.card = null;
        // Se asignan las subcombinaciones (para el método contains).
        this.subc1 = c1;
        this.subc2 = c2;
        // El coste se calcula como la suma de los costes de cada componente.
        this.coste = c1.getCoste() + c2.getCoste();
    }

    public Integer getVal() {
        return this.val;
    }

    public int getNumSubCombos() {
        return this.numSubCombos;
    }

    public int getCoste() {
        return this.coste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combo other = (Combo) o;
        // Aquí se consideran iguales a los combos que tengan el mismo valor, número de cartas y coste.
        return this.val == other.val && this.numSubCombos == other.numSubCombos && this.coste == other.coste;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, numSubCombos, coste);
    }

    @Override
    public int compareTo(Combo other) {
        // Primero se comparan por valor; si son iguales, por número de cartas; y luego por coste.
        int cmp = Integer.compare(this.val, other.val);
        if (cmp == 0) {
            cmp = Integer.compare(this.numSubCombos, other.numSubCombos);
            if (cmp == 0) {
                cmp = Integer.compare(this.coste, other.coste);
            }
        }
        return cmp;
    }

    // Método para comprobar si el combo contiene una carta determinada.
    public boolean contains(Integer card) {
        if (this.card != null && this.card.equals(card)) return true;
        if (subc1 != null && subc1.contains(card)) return true;
        if (subc2 != null && subc2.contains(card)) return true;
        return false;
    }
}

public class Magic556 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Se procesan los casos hasta que se lea un mana = 0.
        while (sc.hasNextLine()) {
            String linea = sc.nextLine().trim();
            if (linea.isEmpty())
                continue;
            String[] leido = linea.split(" ");
            int mana = Integer.parseInt(leido[0]);
            if (mana == 0)
                break;
            int numCartas = Integer.parseInt(leido[1]);

            ArrayList<Integer> valores = new ArrayList<>(numCartas);
            String[] valores_cartas = sc.nextLine().split(" ");
            for (int i = 0; i < numCartas; i++) {
                valores.add(Integer.parseInt(valores_cartas[i]));
            }
            // Ordenamos los valores de maná.
            Collections.sort(valores);

            // Creamos la tabla: lista de filas, donde cada fila es una lista de TreeSet de Combos.
            LinkedList<LinkedList<TreeSet<Combo>>> tabla = new LinkedList<>();

            // Rellenamos la primera fila (índices 0 a mana-1)
            LinkedList<TreeSet<Combo>> fila = new LinkedList<>();
            for (int i = 0; i < mana; i++) {
                fila.add(new TreeSet<>());
            }
            // Inicializamos la solución con el primer combo.
            Combo solucion = new Combo(valores.get(0));

            // Rellenamos la primera fila con los combos formados por cada carta individual.
            for (Integer val : valores) {
                if (val <= mana) {
                    Combo combo = new Combo(val);
                    fila.get(val - 1).add(combo);
                    if (combo.getVal() > solucion.getVal()) {
                        solucion = combo;
                    }
                }
            }
            tabla.add(fila);

            // Se generan filas adicionales, combinando combos anteriores.
            for (int fila_idx = 1; fila_idx < numCartas; fila_idx++) {
                LinkedList<TreeSet<Combo>> fila_anterior = tabla.get(fila_idx - 1);
                LinkedList<TreeSet<Combo>> fila_actual = new LinkedList<>();
                tabla.add(fila_actual);

                // Las columnas representan costes de maná de 1 a mana.
                for (int col_idx = 1; col_idx <= mana; col_idx++) {
                    TreeSet<Combo> celda_actual = new TreeSet<>();
                    fila_actual.add(celda_actual);

                    // Se recorren celdas de la fila anterior en columnas menores a la actual.
                    for (int col_prev = 0; col_prev < col_idx - 1; col_prev++) {
                        TreeSet<Combo> celda_anterior = fila_anterior.get(col_prev);
                        // Se intenta combinar cada combo de la celda anterior con cada carta.
                        for (Combo c : celda_anterior) {
                            for (Integer val : valores) {
                                if (val > col_idx)
                                    break;
                                // Se evita repetir cartas en el combo.
                                if (c.contains(val))
                                    continue;
                                // Si ya se ha alcanzado o superado el mana, no se combinan.
                                if (c.getCoste() >= col_idx)
                                    break;
                                Combo new_combo = new Combo(c, val);
                                if (new_combo.getCoste() <= col_idx) {
                                    if (new_combo.getVal() > solucion.getVal())
                                        solucion = new_combo;
                                    celda_actual.add(new_combo);
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(solucion.getVal());
        }
        sc.close();
    }
}
