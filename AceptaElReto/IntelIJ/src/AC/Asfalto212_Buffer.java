package AC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Asfalto212_Buffer {

    public static void main(String[] args) throws IOException {


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int num_calles,num_nodos;
        int[][] visitado;
        int fila,columna;
        StringTokenizer leido;
        int total = 0;
        boolean sol_encontrada;
        int grados[];

        while (true) {

            num_calles = Integer.parseInt(br.readLine());
            if (num_calles == 0 )
                break;
            num_nodos = Integer.parseInt(br.readLine());

            visitado = new int[num_nodos][num_nodos];
            grados = new int[num_nodos];

            // Relleno la matriz de adyacencia.
            for (int i = 0; i < num_calles; i++) {
                leido = new StringTokenizer(br.readLine());
                fila = Integer.parseInt(leido.nextToken())-1;
                columna = Integer.parseInt(leido.nextToken())-1;
                grados[fila]++;
                grados[columna]++;
                total++;
            }

            int grado;
            boolean respondido = false;
            int num_impares = 0;
            for (int i = 0; i < num_nodos; i++) {
                if (grados[i] % 2 != 0){
                    num_impares++;
                    if (num_impares>2) {
                        System.out.println("NO");
                        respondido = true;
                        break;
                    }
                }

            }
            if (! respondido)
                System.out.println("SI");
        }
        br.close();
    }

    private static int[][] nuevo(int [][] antiguo,int n){
        int [][] retval = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                retval[i][j] = antiguo[i][j];

        return retval;

    }

    public static boolean es_solucion(int f,int c, int[][] matrix, int n, int num_aristas_por_recorrer){

        // Si he entrado en aquí buscando, es que no hay solución si ya he recorrido todas las arista.
        if (num_aristas_por_recorrer == 0 && matrix[f][c] == 0)
            return true;

        else if (matrix[f][c] == 0 || num_aristas_por_recorrer == 0)
            return false;

        matrix[f][c]--; // Marco como arista recorrida 1 vez y me voy a ese nodo
        num_aristas_por_recorrer--;

        boolean solucion_subllamada;
        // Visito todas las aristas conectadas con el siguiente nodo.
        for (int i = 0; i < f; i++) {
            solucion_subllamada = es_solucion(i,c,nuevo(matrix,n),n,num_aristas_por_recorrer);
            if (solucion_subllamada)
                return true;
        }
        // Si he llegado aquí, es que no he encontrado una solución por los nodos conectados con este primero.

        // Visito todas las aristas conectadas con el siguiente nodo.
        for (int i = 0; i < f; i++) {
            solucion_subllamada = es_solucion(i,f,nuevo(matrix,n),n,num_aristas_por_recorrer);
            if (solucion_subllamada)
                return true;
        }

        return false;
    }
    public static boolean es_solucion_deep(int f,int c, int[][] matrix, int n,int total){
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