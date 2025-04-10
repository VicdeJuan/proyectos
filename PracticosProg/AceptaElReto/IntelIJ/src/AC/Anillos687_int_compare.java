package AC;

import java.util.*;

/**
 * https://aceptaelreto.com/problem/statement.php?id=687&cat=43
 *
 *
 *
 */


public class Anillos687_int_compare {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int num_nodos,num_cortes;
        int origen,destino;
        String[] leido;
        boolean next;
        int[][] matriz;
        int num_aristas;
        while (true) {
            next = false;
            num_aristas = 0;
            num_nodos = Integer.parseInt(sc.nextLine());
            if (num_nodos == 0)
                break;
            matriz = new int[num_nodos][num_nodos];

            do{
                leido = sc.nextLine().split(" ");
                origen = Integer.parseInt(leido[0]);
                destino = Integer.parseInt(leido[1]);
                if (origen == 0 && destino == 0){
                    break;
                }
                matriz[origen-1][destino-1]++;
                num_aristas++;
            }while(true);

            if(next) continue;


            System.out.println(Math.max(num_aristas - (num_nodos)+1,0));
        }
        sc.close();
    }

}
