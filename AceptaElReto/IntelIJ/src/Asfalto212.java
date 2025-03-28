import java.util.Scanner;

public class Asfalto212 {

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        int num_calles,num_nodos;
        int[][] visitado;
        int fila,columna;
        String[] leido;
        int total = 0;
        boolean sol_encontrada;

        while (sc.hasNextLine()) {
            num_calles = Integer.parseInt(sc.nextLine());
            if (num_calles == 0 )
                break;
            num_nodos = Integer.parseInt(sc.nextLine());
            
            visitado = new int[num_nodos][num_nodos];
            for (int i = 0; i < num_calles; i++) {
                leido = sc.nextLine().split(" ");
                fila = Integer.parseInt(leido[0])-1;
                columna = Integer.parseInt(leido[1])-1;
                visitado[fila][columna]+=1;
                visitado[columna][fila]+=1;
                total++;
            }

            sol_encontrada = false;
            for (int i = 0; i < num_nodos && !sol_encontrada; i++) {
                for (int j = 0; j < num_nodos && !sol_encontrada; j++) {
                    if (es_solucion(i,j,visitado.clone(),num_nodos,num_calles)) {
                        System.out.println("SI");
                        sol_encontrada = true;
                    }
                }
            }
            if (!sol_encontrada)
                System.out.println("NO");

        }
        sc.close();
    }

    public static boolean es_solucion(int f,int c, int[][] matrix, int n,int total){
        boolean retval = true;

        // Intuición. Si he entrado aquí buscando, es que no hay solución si no quedan aristas.
        if (total == 0)
            return false;

        if (matrix[f][c] <= 0)
            return false;
        matrix[f][c]-=1;
        matrix[c][f]-=1;

        // A todos los conectados con la fila

        for(int _f : matrix[c]){
            if (_f >= 1)
                retval = retval && es_solucion(c,_f,matrix,n,--total);
        }

        return retval;
    }
}