package AC;

import TLE.FastReader;

import java.io.IOException;

public class Asfalto212_fastest {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        while (true) {
            int num_calles = reader.nextInt();
            if (num_calles == 0)
                break;
            int num_nodos = reader.nextInt();

            int[][] visitado = new int[num_nodos][num_nodos];
            int[] grados = new int[num_nodos];

            // Relleno la matriz de adyacencia y calculo los grados
            for (int i = 0; i < num_calles; i++) {
                int fila = reader.nextInt() - 1;
                int columna = reader.nextInt() - 1;
                visitado[fila][columna]++;
                visitado[columna][fila]++;
                grados[fila]++;
                grados[columna]++;
            }

            boolean respondido = false;
            int num_impares = 0;
            for (int i = 0; i < num_nodos; i++) {
                if (grados[i] % 2 != 0) {
                    num_impares++;
                    if (num_impares > 2) {
                        System.out.println("NO");
                        respondido = true;
                        break;
                    }
                }
            }
            if (!respondido)
                System.out.println("SI");
        }
    }

    private static int[][] nuevo(int [][] antiguo, int n) {
        int [][] retval = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                retval[i][j] = antiguo[i][j];
        return retval;
    }

    public static boolean es_solucion(int f, int c, int[][] matrix, int n, int num_aristas_por_recorrer) {
        // Si he recorrido todas las aristas y no hay más en (f,c)
        if (num_aristas_por_recorrer == 0 && matrix[f][c] == 0)
            return true;
        else if (matrix[f][c] == 0 || num_aristas_por_recorrer == 0)
            return false;

        matrix[f][c]--; // Marco la arista como recorrida
        num_aristas_por_recorrer--;

        boolean solucion_subllamada;
        // Recorro los nodos conectados a 'c'
        for (int i = 0; i < f; i++) {
            solucion_subllamada = es_solucion(i, c, nuevo(matrix, n), n, num_aristas_por_recorrer);
            if (solucion_subllamada)
                return true;
        }
        // Recorro los nodos conectados a 'f'
        for (int i = 0; i < f; i++) {
            solucion_subllamada = es_solucion(i, f, nuevo(matrix, n), n, num_aristas_por_recorrer);
            if (solucion_subllamada)
                return true;
        }
        return false;
    }

    public static boolean es_solucion_deep(int f, int c, int[][] matrix, int n, int total) {
        boolean retval = true;
        // Si no quedan aristas, no hay solución
        if (total == 0)
            return false;
        if (matrix[f][c] <= 0)
            return false;

        matrix[f][c] -= 1;
        matrix[c][f] -= 1;
        // Recorro los nodos conectados a 'c'
        for (int _f : matrix[c]) {
            if (_f >= 1)
                retval = retval && es_solucion(c, _f, matrix, n, --total);
        }
        return retval;
    }
}
