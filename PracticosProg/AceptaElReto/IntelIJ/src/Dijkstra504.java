import java.util.*;

/* Código de solución del problema https://aceptaelreto.com/problem/statement.php?id=504&cat=43

ESTADO: TLE
 */

class Arista {
    int peso;
    Nodo destino;
    boolean dirigido;

    public Arista(Nodo dest, int peso, boolean es_dirigido) {
        this.destino = dest;
        this.peso = peso;
        this.dirigido = es_dirigido;

    }

    public int getPeso() {
        return peso;
    }

    public Nodo getDestino(){
        return destino;
    }

}

class Nodo {
    int id;
    String tag; // Para futuras ocasiones.
    LinkedList<Arista> aristas;

    public Nodo(int id) {
        this.id = id;
        aristas = new LinkedList<>();
    }

    public List<Arista> getAristas(){
        return aristas;
    }

    @Override
    public String toString(){
        return ""+id;
    }

}


class Grafo {

    //TODO: no tengo claro que funcione para grafos dirigidos. Al menos, el toString;

    List<Nodo> listaAdyacencia;
    
    int num_nodos;

    public Grafo(int num_nodos) {
        this.num_nodos = num_nodos;
        listaAdyacencia = new ArrayList<>(); // Muchas lecturas, pocas escrituras.
        for (int i = 0; i < num_nodos; i++) {
            listaAdyacencia.add(new Nodo(i+1));
        }
        
    }

    public Nodo getNode_byID(int idx) {
        return listaAdyacencia.get(idx - 1);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grafo con ").append(num_nodos).append(" nodos.\n");
        sb.append("Lista de adyacencia:\n");
        for (Nodo nodo : listaAdyacencia) {

            sb.append("Nodo ").append(nodo.id).append(": ");
            for (Arista arista : nodo.aristas) {

                // Me salto las aristas que llegan al nodo.

                sb.append("->(")
                        .append(arista.destino.id)
                        .append(", peso=")
                        .append(arista.peso)
                        .append(", dirigido=")
                        .append(arista.dirigido)
                        .append(") ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

public class Dijkstra504 {

    static HashMap<Nodo,Integer> distancias;
    public static void Dijkstra504(Grafo g,int origen_id, int destino_id){
        boolean menor_num_caminos = false;
        Nodo origen = g.getNode_byID(origen_id);
        Nodo destino = g.getNode_byID(destino_id);


        distancias = new HashMap<>();
        HashMap<Nodo,Integer> num_saltos_min = new HashMap<>();
        HashMap<Nodo,Integer> num_saltos = new HashMap<>();

        Queue<Nodo> cola = new PriorityQueue<Nodo>(11, new Comparator<Nodo>() {
            @Override
            public int compare(Nodo n1, Nodo n2) {
                return distancias.get(n1) - distancias.get(n2);
            }
        });
        cola.add(origen);

        HashMap<Nodo,Boolean> visitado = new HashMap<>();
        distancias.put(origen,0);
        num_saltos_min.put(origen,0);
        num_saltos.put(origen,0);


        Nodo nodo_actual,vecino;
        int distancia_hasta_alli,num_saltos_hasta_alli;

        boolean rodeo = false;

        while(!cola.isEmpty()){
            nodo_actual = cola.remove();
            if (nodo_actual == destino)
                continue;
            if (visitado.containsKey(nodo_actual))
                continue;
            visitado.put(nodo_actual,true);

            for (Arista a : nodo_actual.getAristas()){
                vecino = a.getDestino();
                if (distancias.containsKey(vecino)) {
                    if (distancias.get(nodo_actual) + a.getPeso() < distancias.get(vecino)) {
                        distancia_hasta_alli = distancias.get(nodo_actual) + a.getPeso();
                        num_saltos.put(vecino,num_saltos.get(nodo_actual)+1);
                    }else
                        distancia_hasta_alli = distancias.get(vecino);

                    if (num_saltos_min.get(nodo_actual) + 1 < num_saltos_min.get(vecino)) {
                        num_saltos_hasta_alli = num_saltos_min.get(nodo_actual) + 1;
                    }else
                        num_saltos_hasta_alli = num_saltos_min.get(vecino);

                }else {
                    distancia_hasta_alli = distancias.get(nodo_actual) + a.getPeso();
                    num_saltos_hasta_alli = num_saltos_min.get(nodo_actual)+1;
                    num_saltos.put(vecino,num_saltos_hasta_alli);
                }
                distancias.put(vecino,distancia_hasta_alli);
                num_saltos_min.put(vecino,num_saltos_hasta_alli);
                cola.offer(vecino);
            }
        }
        if (distancias.containsKey(destino))
            System.out.println(distancias.get(destino)+" " +
                    (num_saltos_min.get(destino).equals(num_saltos.get(destino)) ? "SI":"NO")
            );
        else
            System.out.println("SIN CAMINO");

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num_aristas, num_nodos;
        String[] leido;
        String __leido;
        int origen, destino, peso;
        boolean dirigido = false,log = false;

        Grafo g;
        while (sc.hasNextLine()) {
            __leido = sc.nextLine();
            if (__leido.equals(""))
                break;

            num_nodos = Integer.parseInt(__leido);
            if (num_nodos == 0)
                break;
            g = new Grafo(num_nodos);

            num_aristas = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < num_aristas; i++) {
                leido = sc.nextLine().split(" ");
                origen = Integer.parseInt(leido[0]);
                destino = Integer.parseInt(leido[1]);
                peso = Integer.parseInt(leido[2]);

                // Creo una arista nueva. El method crear arista modifica los nodos.
                g.getNode_byID(origen).aristas.add(new Arista(g.getNode_byID(destino),peso,dirigido));
                if (!dirigido)
                    g.getNode_byID(destino).aristas.add(new Arista(g.getNode_byID(origen), peso, dirigido));

            }
            // Una vez procesado el grafo:
            if (log)
                System.out.println(g);

            int num_consultas = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < num_consultas; i++) {
                leido = sc.nextLine().split(" ");
                origen = Integer.parseInt(leido[0]);
                destino = Integer.parseInt(leido[1]);
                Dijkstra504(g,origen,destino);
            }
            System.out.println("----");


        }
        sc.close();
    }
}
