import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Combinaciones {
    public Combinaciones(){
    }

    // Método que utiliza longs para calcular combinaciones (versión eficiente)
    public List<Long> calculaCaminosVariables_eficiente(List<Integer> valores, List<Long> combis, int num, int exp) {
        if (exp < 0) return combis;
        long base = num;
        if (combis.isEmpty()){
            for (Integer val : valores)
                combis.add(val * (long) Math.pow(base, exp));
            return calculaCaminosVariables_eficiente(valores, combis, num, exp - 1);
        } else {
            List<Long> new_list = new ArrayList<>();
            for (int new_v : valores)
                for (long val : combis)
                    new_list.add(new_v * (long) Math.pow(base, exp) + val);
            return calculaCaminosVariables_eficiente(valores, new_list, num, exp - 1);
        }
    }

    // Método auxiliar para convertir un número a su representación en la base indicada
    private List<Integer> unconvert(long num, int base, int numcombos){
        List<Integer> retval = new ArrayList<>();
        for (int i = 0; i < numcombos; i++) {
            retval.add((int)(num % base));
            num /= base;
        }
        return retval;
    }

    // Método recursivo que genera combinaciones utilizando List (ArrayList)
    public void calculaCaminosVariables(List<Integer> valores, List<List<Integer>> combis, int numcombos){
        _calcula(valores, combis, numcombos);
        System.out.println(combis);
    }

    public void _calcula(List<Integer> valores, List<List<Integer>> combis, int num){
        if (num <= 0)
            return;
        List<Integer> toadd, popped;

        if (combis.isEmpty()){
            for (int val : valores){
                toadd = new ArrayList<>();
                toadd.add(val);
                combis.add(toadd);
            }
            num--;
        }
        int lim = combis.size();
        for (int i = 0; i < lim; i++) {
            // Se reemplaza removeFirst() por remove(0) para trabajar con List
            popped = combis.remove(0);
            System.out.println(popped);
            for (int val : valores) {
                toadd = new ArrayList<>();
                toadd.addAll(popped);
                toadd.add(val);
                combis.add(toadd);
            }
        }
        _calcula(valores, combis, num - 1);
    }

    // Método que utiliza longs para calcular combinaciones y luego convierte cada valor a un array de dígitos
    public void calculaCaminosVariables_mat(List<Integer> valores, int numcombos) {
        List<Long> retval = calculaCaminosVariables_eficiente(valores, new ArrayList<>(), numcombos, numcombos - 1);
        for (long combinacion : retval){
            System.out.print(unconvert(combinacion, valores.size(), numcombos));
        }
    }

    // NUEVO: Método que calcula combinaciones utilizando únicamente arrays.
    // Cada combinación se representa como un array de enteros.
    public int[][] calculaCaminosVariables_array(int[] valores, int numcombos) {
        int n = valores.length;
        // Número total de combinaciones: n^(numcombos)
        int total = (int) Math.pow(n, numcombos);
        int[][] result = new int[total][numcombos];
        // Para cada número de 0 a total-1, convertimos a la base 'n'
        for (int i = 0; i < total; i++) {
            int num = i;
            for (int j = numcombos - 1; j >= 0; j--) {
                result[i][j] = valores[num % n];
                num /= n;
            }
        }
        return result;
    }
}

public class Main {

    /**
     * Prueba que utiliza ArrayList para generar combinaciones.
     */
    public static void ejercicio2(){
        Combinaciones comb = new Combinaciones();
        List<Integer> valores = new ArrayList<>();
        int[] vals = new int[3];
        for (int i = 0; i < 3; i++) {
            valores.add(i);
            vals[i] = i;
        }
        int numcombos = 8;

        System.out.println("Combinaciones totales (ArrayList):");
        long before1 = System.currentTimeMillis();
        comb.calculaCaminosVariables(valores, new ArrayList<>(), numcombos);
        long after1 = System.currentTimeMillis();

        long before2 = System.currentTimeMillis();
        comb.calculaCaminosVariables_mat(valores, numcombos);
        long after2 = System.currentTimeMillis();

        System.out.printf("\n\nLento (ArrayList): %d ms\nRapido (longs): %d ms\n", after1 - before1, after2 - before2);

        long before3 = System.currentTimeMillis(),after;
        int[][] combos = comb.calculaCaminosVariables_array(vals , numcombos);
        // Imprime cada combinación
        for (int[] combo : combos) {
            System.out.println(Arrays.toString(combo));
        }
        long after3 = System.currentTimeMillis();

        System.out.printf("\n\nLento (ArrayList): %d ms\nRapido (longs): %d ms\nArrays: %d\n", after1 - before1, after2 - before2,after3-before3);
    }

    /**
     * Prueba de filtrado de palabras utilizando arrays.
     */
    public static void ejercicio3(){
        String[] listaPalabras = {"cola", "limbo", "zapatos", "imposible"};
        System.out.println("Imprimir palabras con más de 5 caracteres:");
        Predicate<String> predicado = s -> s.length() > 5;
        filtrar(listaPalabras, predicado);
        System.out.print("\n");
        filtrar(listaPalabras, s -> s.compareTo("vaca") < 0);
    }

    private static void filtrar(String[] listaPalabras, Predicate<String> predicado) {
        // Versión funcional con streams
        System.out.println(
                Arrays.stream(listaPalabras)
                        .filter(predicado)
                        .collect(Collectors.joining(" ")));
        // Versión iterativa
        for (String s : listaPalabras)
            System.out.print((predicado.test(s) ? s + " " : ""));
    }

    /**
     * NUEVO: Prueba que utiliza únicamente arrays para generar combinaciones.
     * Se usa un número pequeño de posiciones para evitar una salida excesiva.
     */
    public static void ejercicio4(){
        Combinaciones comb = new Combinaciones();
        int[] valores = {0, 1, 2, 3};  // Array de valores
        int numcombos = 3;  // Número de dígitos en cada combinación (pequeño para evitar gran cantidad de salidas)


    }

    public static void main(String[] args) {
        System.out.println("Ejercicio2:");
        ejercicio2();
        System.out.println("\n\nEjercicio3:");
        ejercicio3();
    }
}
