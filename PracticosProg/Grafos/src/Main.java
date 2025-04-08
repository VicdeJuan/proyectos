import java.util.*;

class Arista implements Comparable{
    int origen,destino;
    double coste;
    public Arista(int o,int d,double c){
        origen=o;coste=c; destino=d;
    }

    @Override
    public boolean equals(Object c){
        Arista b = ((Arista) c);
        return b.origen == this.origen && b.destino == this.destino && b.coste == this.coste;
    }

    @Override
    public int compareTo(Object o) {
        // Cast a entero del ceil. Así no se crean ceros por error.
        // 0.5 - 0.2 = 0.3 -> 1 [Sin el ceil daría 0].
        return (int) Math.floor(0.5 + coste - ((Arista) o).coste);
    }
    @Override
    public String toString(){
        return "("+this.origen+","+this.destino+","+this.coste+")";
    }
}


class Grafo {
    double[][] matrix;
    int num_nodos;
    boolean[] visitado;

    public Grafo(int n) {
        matrix = new double[n][n];
        num_nodos = n;
        visitado = new boolean[n];
    }

    /**
     * Esta implementación no permite encontrar el camino más corto, puesto que para ello
     * es necesario recorrer los vecinos en orden o algo así.
     *
     * Por este motivo, es necesario una estructura más elaborada tipo Nodo, Ruta o similar.
     *
     * Otra opción es recurrir a Dijkstra que es BFS
     */
    public List<Integer> dfs(int origen, int destino, boolean[] stack, List<Integer> path, boolean add) {
        if (visitado[origen]) return null;
        if (add) path.add(origen);
        if (!visitado[origen]) {
            visitado[origen] = true;
        }

        for (int i = 0; i < num_nodos; i++) {
            if (matrix[origen][i] == 0) continue;

            if (i == destino) {
                path.add(i);
                return path;
            }

            if (!stack[i]) {
                stack[i] = true;
                List<Integer> ret = dfs(i, destino, stack, path, true);
                if (ret != null) {
                    return path;
                }
            }
        }

        return null;
    }


    public List<Integer> bfs(int origen, int destino) {
        Queue<Arista> para_visitar = new PriorityQueue<>();

        visitado[origen] = true;
        for (int i = 0; i < num_nodos; i++) {
            if (matrix[origen][i] > 0)
                para_visitar.offer(new Arista(origen, i, matrix[origen][i]));
        }

        Arista nodo_actual;

        int[] padre  = new int[num_nodos];
        padre[origen] = -1;

        while (!para_visitar.isEmpty()) {
            // Compruebo si es el destino. Si lo es, termino.
            nodo_actual = para_visitar.poll();
            if (visitado[nodo_actual.destino])
                continue;
            visitado[nodo_actual.destino] = true;

            if (nodo_actual.destino == destino) {
                System.out.println("Encontrado camino mínimo");
                padre[nodo_actual.destino] = nodo_actual.origen;
                visitado[nodo_actual.destino] = true;
                break;
            } else {
                for (int i = 0; i < num_nodos; i++) {
                    if (matrix[nodo_actual.destino][i] != 0 && !visitado[i]) {
                        System.out.println("Recorriendo " + nodo_actual);


                        Arista toadd = new Arista(nodo_actual.destino, i, matrix[nodo_actual.destino][i]);
                        para_visitar.offer(toadd);
                        visitado[i] = true;
                        padre[i] = nodo_actual.destino;
                        System.out.println(para_visitar);
                    }
                }
            }

        }
        List<Integer> camino = new ArrayList<>();
        for (int at = destino; at != -1; at = padre[at]) {
            camino.add(0, at); // Insertar al inicio
        }
        return camino;
    }

}
public class Main {

    public static void main(String[] args) {
        Grafo grafo = new Grafo(7);

        // Conectamos los nodos (grafo no dirigido)
        grafo.matrix[0][1] = 1;
        grafo.matrix[0][2] = 1;
        grafo.matrix[1][3] = 1;
        grafo.matrix[1][4] = 1;
        grafo.matrix[2][5] = 1;
        grafo.matrix[5][6] = 100;
        grafo.matrix[4][6] = 1;

        // Puedes conectar en ambas direcciones si es no dirigido:
        grafo.matrix[1][0] = 1;
        grafo.matrix[2][0] = 1;
        grafo.matrix[3][1] = 1;
        grafo.matrix[4][1] = 1;
        grafo.matrix[5][2] = 1;
        grafo.matrix[6][5] = 100;
        grafo.matrix[6][4] = 1;

        int origen = 0;
        int destino = 6;

        boolean[] stack = new boolean[7];
        List<Integer> path = new ArrayList<>();

        List<Integer> resultado = grafo.dfs(origen, destino, stack, path, true);
        List<Integer> resultado2 = grafo.bfs(origen, destino);

        if (resultado != null) {
            System.out.println("Camino de " + origen + " a " + destino + ": " + resultado2);
        } else {
            System.out.println("No hay camino de " + origen + " a " + destino);
        }
    }
}
