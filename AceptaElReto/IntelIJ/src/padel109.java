import java.util.HashMap;
import java.util.Scanner;
import java.util.SortedMap;

public class padel109 {



    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, Integer> resultados = new HashMap<>();

        String[] result;
        String categoria = "";
        int set_anf = 0, set_visit = 0;
        categoria = sc.nextLine();
        while(!categoria.equals("FIN")){
            while (!categoria.equals("FIN")) {
                result = sc.nextLine().split(" ");
                if (result[0].equals("FIN"))
                    break;
                set_anf = Integer.parseInt(result[1]);
                set_visit = Integer.parseInt(result[3]);

                // 2 puntos por ganar, 1 por perder
                if (set_anf > set_visit) {
                    set_anf = 2;
                    set_visit = 1;
                } else {
                    set_anf = 1;
                    set_visit = 2;
                }

                if (resultados.containsKey(result[0]))
                    set_anf += resultados.get(result[0]);
                if (resultados.containsKey(result[2]))
                    set_visit += resultados.get(result[2]);

                resultados.put(result[0], set_anf);
                resultados.put(result[2], set_visit);

            }
            // Se han leido todos los partidos, así que se puede calcular.
            String ganador = "";
            int max = Integer.MIN_VALUE;
            boolean empate = false;
            for (String key : resultados.keySet()) {
                if (resultados.get(key) == max) {
                    empate = true;
                }else if (resultados.get(key) > max) {
                    ganador = key;
                    max = resultados.get(key);
                    empate = false;
                }
            }
            if (empate) {
                System.out.println("EMPATE");
            } else
                System.out.println(ganador);
            resultados.clear();
            categoria = sc.nextLine();
        }
        sc.close();
    }
}
