import java.util.Scanner;

public class Jaen283 {

    public static final int SIN_PLANTAR = 0;
    public static final int PLANTADO = 1;
    public static final int SIN_PLANTAR_VISITADO = 2;
    public static final int PLANTADO_VISITADO = 3;
    public static final boolean log = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int ancho, alto;
        int[][] visitado;

        while (sc.hasNextLine()) {
            String _leido = sc.nextLine();
            if (_leido.trim().equals(""))  // comprobamos línea vacía
                break;
            String[] leido = _leido.split(" ");
            alto = Integer.parseInt(leido[0]);
            ancho = Integer.parseInt(leido[1]);
            visitado = new int[alto][ancho];

            String linea;
            for (int i = 0; i < alto; i++) {
                linea = sc.nextLine();
                for (int j = 0; j < ancho; j++) {
                    char c = linea.charAt(j);
                    if (c == '#')
                        visitado[i][j] = PLANTADO;
                    else
                        visitado[i][j] = SIN_PLANTAR;
                }
            }
            if (log)
                System.out.println("Mapa leído.");

            int plantacion_max = -1, aux_val;

            // Recorremos la matriz utilizando [fila][columna]
            for (int i = 0; i < alto; i++) {
                for (int j = 0; j < ancho; j++) {
                    aux_val = medirPlantacion(i, j, visitado, alto, ancho);
                    if (log)
                        System.out.println("(" + i + "," + j + ") -> " + aux_val);
                    if (aux_val > plantacion_max)
                        plantacion_max = aux_val;
                }
            }

            System.out.println(plantacion_max);
        }

        sc.close();
    }

    // Aquí 'i' es el índice de fila y 'j' el de columna.
    public static int medirPlantacion(int i, int j, int[][] visitados, int max_i, int max_j) {
        // Si ya se ha visitado, se retorna 0.
        if (visitados[i][j] > 1)
            return 0;
        else if (visitados[i][j] == SIN_PLANTAR) {
            visitados[i][j] = SIN_PLANTAR_VISITADO;
            return 0;
        } else { // visitados[i][j] == PLANTADO
            visitados[i][j] = PLANTADO_VISITADO;
            int retval = 1;
            // Arriba
            if (i > 0)
                retval += medirPlantacion(i - 1, j, visitados, max_i, max_j);
            // Abajo
            if (i < max_i - 1)
                retval += medirPlantacion(i + 1, j, visitados, max_i, max_j);
            // Izquierda
            if (j > 0)
                retval += medirPlantacion(i, j - 1, visitados, max_i, max_j);
            // Derecha
            if (j < max_j - 1)
                retval += medirPlantacion(i, j + 1, visitados, max_i, max_j);

            return retval;
        }
    }
}
