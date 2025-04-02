import java.util.*;

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

    public void añadirArista(Arista a) {
        aristas.add(a);
    }

    public List<Arista> getArista(){
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
    double[][] matrizAdyacencia;
    int num_nodos;

    public Grafo(int num_nodos) {
        this.num_nodos = num_nodos;
        listaAdyacencia = new ArrayList<>(); // Muchas lecturas, pocas escrituras.
        for (int i = 0; i < num_nodos; i++) {
            listaAdyacencia.add(new Nodo(i+1));
        }
        matrizAdyacencia = new double[num_nodos][num_nodos];
    }


    public void añadirNodo(Nodo n) {
        listaAdyacencia.add(n);
    }

    public Nodo getNode_byID(int idx) {
        return listaAdyacencia.get(idx - 1);
    }

    public void setPeso(int origen, int destino, int peso, boolean dirigido) {
        origen--; destino--;

        matrizAdyacencia[origen][destino] = peso;
        if (!dirigido)
            matrizAdyacencia[destino][origen] = peso;
    }


    public List<Nodo> BusquedaExhaustivaImcompleta(Nodo origen, Nodo destino){
        class Tupla<T1,T2>{
            T1 val1;
            T2 val2;

            public Tupla(T1 v1, T2 v2){
                val1 = v1;
                val2 = v2;
            }
            public T1 getFirst(){
                return val1;
            }
            public T2 getSecond(){
                return val2;
            }
        }

        // https://aceptaelreto.com/problem/statement.php?id=504&cat=43

        /* Vamos a guardar todos los posibles caminos en un mapa que asocia:
        a cada nodo una lista de parejas (coste, camino_para_llegar_al_nodo).
        Guardamos la lista de parejas para poder acceder a todos los posibles caminos.

        Es ineficiente para este problema, pero estamos programando una solución lo más general posible.

        TODO: la lista de etiquetas debe mantenerse ordenada según el coste acumulado.
        */
        HashMap<
                Nodo,
                List<
                        Tupla<Integer,List<Nodo>>
                >> tags = new HashMap<Nodo,List<Tupla<Integer,List<Nodo>>>>();

        //Inicializo el mapa con la primera etiqueta.
        int coste_acum = 0,min_weight_to_here;
        Tupla<Integer,List<Nodo>> tuple_to_add = new Tupla<>(coste_acum,new LinkedList<>());
        List<Tupla<Integer,List<Nodo>>> list_of_tuples = new LinkedList<>();
        List<Nodo> path_to_here;
        list_of_tuples.add(tuple_to_add);
        tags.put(origen,list_of_tuples);

        Nodo nodo_actual;
        Queue<Nodo> visitados = new PriorityQueue<>();
        visitados.add(origen);

        // ITERAMOS:

        while(true/*cola no vacía*/){
            nodo_actual = visitados.poll();

            if (nodo_actual == destino)
                continue;

            for (Arista a : nodo_actual.getArista()){

                // NO IR HACIA ATRÁS

                if (!tags.containsKey(a.getDestino())){
                    //[a.getDestino(),tags.get(nodo_actual).getFirst().getFirst(),tags.get(nodo_actual).getFirst().getFirst()]
                    // Obtengo el camino para llegar al nodo actual
                    path_to_here = new LinkedList<>(tags.get(nodo_actual).getFirst().getSecond());

                    // Para llegar al destino, tengo que pasar por el nodo actual
                    path_to_here.add(nodo_actual);

                    // Como hay varios caminos para llegar al nodo_actual, elegimos la primera tupla que representará
                    // el camino más corto porque la lista de tuplas está ordenada.
                    min_weight_to_here = tags.get(nodo_actual).getFirst().getFirst();


                    // Creo la tupla para añadir a la lista de tuplas.
                    tuple_to_add = new Tupla<>(min_weight_to_here+a.getPeso(),path_to_here);

                    // Creo la lista de tuplas porque vacía y añado la tupla creada.
                    list_of_tuples = new LinkedList<>();
                    list_of_tuples.add(tuple_to_add);

                    // Creo la entrada en el mapa con la lista de tuplas que solo contiene 1.
                    tags.put(a.getDestino(),list_of_tuples);
                }else{
                    // Si ya hay una lista de tuplas, recupero la lista de tuplas
                    list_of_tuples = tags.get(a.getDestino());

                    // Obtengo el menor peso para llegar aquí
                    min_weight_to_here = tags.get(nodo_actual).getFirst().getFirst();

                    // Creo una lista nueva a partir del camino para llegar hasta aquí. El motivo de crear una lista
                    // nueva es que no se sobreescriban sus valores anteriores.
                    path_to_here = new LinkedList<>(tags.get(nodo_actual).getFirst().getSecond());
                    //Añado el nodo actual para llegar aquí.
                    path_to_here.add(nodo_actual);

                    // Creo la tupla que me interesa
                    tuple_to_add = new Tupla<>(min_weight_to_here+a.getPeso(),path_to_here);

                    list_of_tuples.add(tuple_to_add);
                    list_of_tuples.sort((t1, t2) -> t1.getFirst()-t2.getFirst());

                    // Añado al mapa la etiqueta del nodo destino
                    tags.put(a.getDestino(),list_of_tuples);
                }
                if (!visitados.contains(a.getDestino()))
                    visitados.add(a.getDestino());
            }

        }


    }

    public void Dijkstra504(int origen_id, int destino_id){
        boolean menor_num_caminos = false;
        Nodo origen = this.getNode_byID(origen_id);
        Nodo destino = this.getNode_byID(destino_id);

        Queue<Nodo> cola = new LinkedList<>();
        cola.add(origen);

        HashMap<Nodo,Integer> distancias = new HashMap<>();
        HashMap<Nodo,Integer> num_saltos_min = new HashMap<>();
        HashMap<Nodo,Integer> num_saltos = new HashMap<>();

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

            for (Arista a : nodo_actual.getArista()){
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
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int dest;
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
        sb.append("Matriz de adyacencia:\n");
        for (int i = 0; i < num_nodos; i++) {
            for (int j = 0; j < num_nodos; j++) {
                sb.append(matrizAdyacencia[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}

public class estudiandoGrafos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num_aristas, num_nodos;
        String[] leido;
        String __leido;
        int origen, destino, peso;
        boolean dirigido = false;

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

                // Relleno la matriz de adyacencia por si acaso
                g.setPeso(origen, destino, peso, dirigido);

            }
            // Una vez procesado el grafo:
            System.out.println(g);

            int num_consultas = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < num_consultas; i++) {
                leido = sc.nextLine().split(" ");
                origen = Integer.parseInt(leido[0]);
                destino = Integer.parseInt(leido[1]);
                g.Dijkstra504(origen,destino);
            }


        }
        sc.close();
    }
}
