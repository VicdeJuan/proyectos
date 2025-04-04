package TLE;

import java.util.*;

/**
 * https://aceptaelreto.com/problem/statement.php?id=687&cat=43
 *
 * Está incompleto. 
 */
class Grafo687{
    int[][] matriz;
    int num_nodos;
    int max_val;
    int how_many_max_val;


    public Grafo687(int n){
        num_nodos = n;
        matriz = new int[n][n];
        how_many_max_val = 0;
    }

    public int addArista(int origen, int destino){
        origen--;destino--;
        matriz[origen][destino]++;

        if (matriz[origen][destino]>max_val) {
            max_val = matriz[origen][destino];
            how_many_max_val = 1;
        } else if (matriz[origen][destino] == max_val){
            how_many_max_val++;
        }


        return matriz[origen][destino];
    }

    private void updateHowManyMaxVal(){
        how_many_max_val=0;
        for (int i = 0; i < num_nodos; i++)
            for (int j = 0; j < num_nodos; j++)
                if (matriz[i][j] == max_val)
                    how_many_max_val++;
    }

    public int removeArista(){
        for (int i = 0; i < num_nodos; i++) {
            for (int j = 0; j < num_nodos; j++) {
                if (matriz[i][j] == max_val) {
                    matriz[i][j]--;
                    how_many_max_val--;
                    if(how_many_max_val == 0) {
                        max_val--;
                        updateHowManyMaxVal();
                    }
                    return max_val;
                }
            }
        }
        return 0;
    }

    public boolean tieneCiclos(){
        for (int i = 0; i < num_nodos; i++)
            if (tieneCiclos(i)) return true;
        return false;
    }
    private HashSet<Integer> vecinos(int i,HashSet<Integer> conjunto){
        if (conjunto.contains(i)) return conjunto;
        for (int j = 0; j < num_nodos; j++) {
            if (matriz[i][j] >0){
                conjunto.add(j);
                conjunto.addAll(vecinos(j,conjunto));
            }
        }
        return conjunto;
    }
    private boolean tieneCiclos(int nodo){
        for (int i = 0; i < num_nodos; i++)
            if (i != nodo && vecinos(i,new HashSet<Integer>()).contains(nodo))  return true;
        return false;
    }
}

public class Anillos687 {
    public static void main(String[] args){
        Grafo687 g;
        Scanner sc = new Scanner(System.in);
        int num_nodos,num_cortes;
        int origen,destino;
        String[] leido;
        boolean next;

        while (true) {
            next = false;
            num_nodos = Integer.parseInt(sc.nextLine());
            if (num_nodos == 0)
                break;
            g = new Grafo687(num_nodos);
            do{
                leido = sc.nextLine().split(" ");
                origen = Integer.parseInt(leido[0]);
                destino = Integer.parseInt(leido[1]);
                if (origen == 0 && destino == 0){
                    break;
                }
                g.addArista(origen,destino);
            }while(true);

            if(next) continue;

            num_cortes = 0;
            while(g.tieneCiclos()){
                g.removeArista();
                num_cortes++;
            }
            System.out.println(num_cortes);
        }
        sc.close();
    }

}
