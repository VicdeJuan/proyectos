import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Combinaciones {
    public Combinaciones(){
    }

    // Se actualiza el método para trabajar con long
    public List<Long> calculaCaminosVariables_eficiente(List<Integer> valores, List<Long> combis, int num, int exp) {
        if (exp < 0) return combis;
        long base = num;
        if (combis.isEmpty()){
            for (Integer val : valores)
                // Se convierte la potencia a long para evitar desbordamiento
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

    // Se modifica para trabajar con long en lugar de int para el número a convertir
    private List<Integer> unconvert(long num, int base, int numcombos){
        List<Integer> retval = new ArrayList<>();
        for (int i = 0; i < numcombos; i++) {
            retval.add((int)(num % base));
            num /= base;
        }
        return retval;
    }

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
            popped = combis.remove(0); // Se reemplaza removeFirst() por remove(0)
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

    public void calculaCaminosVariables_mat(List<Integer> valores, int numcombos) {
        List<Long> retval = calculaCaminosVariables_eficiente(valores, new ArrayList<>(), numcombos, numcombos - 1);
        for (long combinacion : retval){
            System.out.print(unconvert(combinacion, valores.size(), numcombos));
        }
    }
}

public class Main {

    public static void ejercicio2(){
        Combinaciones comb = new Combinaciones();
        List<Integer> valores = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            valores.add(i);
        }

        int numcombos = 10;

        System.out.println("Combinaciones totales:");
        long before1 = System.currentTimeMillis(), after1;
        comb.calculaCaminosVariables(valores, new ArrayList<>(), numcombos);
        after1 = System.currentTimeMillis();

        long before2 = System.currentTimeMillis(), after2;
        comb.calculaCaminosVariables_mat(valores, numcombos);
        after2 = System.currentTimeMillis();

        System.out.printf("\n\nLento: %d\nRapido: %d\n", after1 - before1, after2 - before2);
    }

    public static void ejercicio3(){
        String[] listaPalabras = {"cola", "limbo", "zapatos", "imposible"};
        System.out.println("Imprimir palabras con más de 5 caracteres:");
        Predicate<String> predicado = s -> s.length() > 5;
        filtrar(listaPalabras, predicado);
        System.out.print("\n");
        filtrar(listaPalabras, s -> s.compareTo("vaca") < 0);
    }

    private static void filtrar(String[] listaPalabras, Predicate<String> predicado) {
        System.out.println(
                Arrays.stream(listaPalabras)
                        .filter(predicado)
                        .collect(Collectors.joining(" ")));
        for (String s : listaPalabras)
            System.out.print((predicado.test(s) ? s + " " : ""));
    }

    public static void main(String[] args) {
        System.out.printf("Ejercicio2:");
        ejercicio2();

        // Para ejecutar ejercicio3, descomenta la siguiente línea:
        // System.out.printf("Ejercicio3:"); ejercicio3();
    }
}
