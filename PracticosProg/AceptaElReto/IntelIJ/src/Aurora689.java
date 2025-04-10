import java.util.*;

class Matrix{
    private int[][] valores;
    public Matrix(int[][] valores){
        this.valores = valores;
    }

    public Matrix(int n){
        valores = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                valores[i][j] = 0;
            }
        }
    }

    public void set(int i,int j,int valor){
        valores[i][j] = valor;
    }

    public void setSim(int i,int j,int valor){
        valores[i][j] = valor;
        valores[j][i] = valor;
    }


    public Matrix transpose(){
        int filas = this.getFilas();
        int columnas = this.getColumnas();
        int n_filas = columnas;
        int n_col = filas;
        int [][] new_valores = new int[n_filas][n_col];
        for (int i = 0; i<n_filas;i++){
            for (int j = 0; j < n_col; j++){
                new_valores[i][j] = this.getValue(j,i);
            }
        }
        return new Matrix(new_valores);
    }

    int getFilas(){
        return valores.length;
    }
    int getColumnas(){
        return valores[0].length;
    }
    int getValue(int i,int j){
        return valores[i][j];
    }

    public static Matrix suma(Matrix A, Matrix B){
        if (A.getFilas() != B.getFilas() || A.getColumnas() != B.getColumnas()){
            return null;
        }
        int [][] new_valores = new int[A.getFilas()][A.getColumnas()];

        for (int i = 0; i < A.getFilas(); i++) {
            for (int j = 0; j < A.getColumnas(); j++) {
                new_valores[i][j] = A.getValue(i,j) + B.getValue(i,j);
            }
        }
        Matrix retval = new Matrix(new_valores);
        return retval;
    }

    public Matrix backwards(){
        int[][] new_val = new int[this.getFilas()][this.getColumnas()];
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                new_val[i][j] = valores[i][j] * -1;
            }
        }
        return new Matrix(new_val);
    }

    public HashSet<Integer> getEnemigos(int persona){
        HashSet<Integer> enemigos = new LinkedHashSet<Integer>();
        boolean[] comprobado = new boolean[this.getFilas()];
        for (int i = 0; i < this.getFilas(); i++) {
            comprobado[i] = true;
        }
        for (int i = 0; i < this.getFilas(); i++) {
            if (this.getValue(persona,i) == Aurora689.ENEMIGO){
                enemigos.add(i);
            }
        }
        return enemigos;
    }
    public HashSet<Integer> getAmigos(int persona){
        HashSet<Integer> amigos = new LinkedHashSet<Integer>();
        boolean[] comprobado = new boolean[this.getFilas()];
        for (int i = 0; i < this.getFilas(); i++) {
            comprobado[i] = false;
        }
        _addAmigos(persona,amigos,comprobado);
        return amigos;
    }

    private void _addEnemigos(int persona, HashSet<Integer> amigos,boolean [] comprobado){
        for (int i = 0; i<getFilas();i++){
            if (Aurora689.ENEMIGO == getValue(persona,i)) {
                amigos.add(i);
                if (!comprobado[i]) {
                    comprobado[i] = true;
                    _addAmigos(i, amigos, comprobado); // Recursivo
                }
            }
        }
    }

    private void _addAmigos(int persona, HashSet<Integer> amigos,boolean [] comprobado){
        for (int i = 0; i<getFilas();i++){
            if (Aurora689.AMIGO == getValue(persona,i)){
                amigos.add(i);
                if (!comprobado[i]) {
                    comprobado[i] = true;
                    _addAmigos(i, amigos, comprobado); // Recursivo
                }
            }else{
                if (Aurora689.ENEMIGO == getValue(persona,i)){
                    _addEnemigos(i,amigos,comprobado);
                }
            }
        }
    }

    public boolean compatible(Set<Integer> amigos){
        for (Integer i : amigos){
            for (Integer j : amigos){
                if (getValue(i,j) == Aurora689.ENEMIGO)
                    return false;
            }
        }
        return true;
    }
}

public class Aurora689 {
    public static final int ENEMIGO = -1;
    public static final int AMIGO = 1;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String[] leido = sc.nextLine().split(" ");
        int num_personas = Integer.parseInt(leido[0]);
        int num_peleas = Integer.parseInt(leido[1]);
        int aforo = Integer.parseInt(leido[2]);
        if (num_peleas == 0) {
            System.out.println(num_personas < aforo ? num_personas : aforo);
            System.exit(0);
        }
        int p1,p2;

        Matrix peleas = new Matrix(num_personas);

        for (int i =0; i< num_peleas; i++){
            leido = sc.nextLine().split(" ");
            p1 = Integer.parseInt(leido[0])-1; // Porque se empieza a contar en 0
            p2 = Integer.parseInt(leido[1])-1; // Porque se empieza a contar en 0
            if (p1 < num_personas && p2 < num_personas)
                peleas.setSim(p1,p2,ENEMIGO);
            else
                System.out.println("ERROR");
        }

        //Reflexividad de la amistad: Toda persona es amiga de sí misma.
        for (int i = 0; i< num_personas; i++)
            peleas.set(i,i,AMIGO);
        // Antirreflexividad de la enemistad: Ninguna persona es enemiga de sí misma.
        // SE DA AUTOMÁTICO

        // Simetría: Si A es amigo de B, entonces B es amigo de A. Similarmente, si A es enemigo de B, entonces B es enemigo de A.
        // COMPROBADO EN MATRIX

        // Transitividad de la amistad: Si A es amigo de B, los amigos de B son también amigos de A y los enemigos de B son también enemigos de A.
        // Anti-transitividad de la enemistad: Si A es enemigo de B, los amigos de B son enemigos de A y los enemigos de B son amigos de A.

        int max = -1,num;
        HashSet<HashSet<Integer>> sets = new HashSet<HashSet<Integer>>();

        boolean log = false;
        for (int i=0;i<num_personas; i++){
            HashSet<Integer> amigos_i = peleas.getAmigos(i);
            List<Integer> enemigos_i = new ArrayList<>();
            enemigos_i.addAll(peleas.getEnemigos(i));

            sets.add(amigos_i);
            if (log) {
                System.out.println("Persona " + i);
                System.out.println(" f: " + amigos_i);
                System.out.println(" e: " + enemigos_i);
            }
        }
        if (log) {
            System.out.println(sets);
            System.out.println(getSubsets(sets, peleas,aforo));
        }
        System.out.println(getSubsets(sets, peleas,aforo).size());


        sc.close();
/**
9 6 7
1 3
2 1
1 4
5 6
6 7
7 8

7 7 4
1 3
2 1
1 4
5 6
6 7
7 8
2 5


 */

    }
    public static HashSet<Integer> getSubsets(HashSet<HashSet<Integer>> sets, Matrix peleas, int aforo){


        HashSet<Integer> obtained,max_set = null;

        if (sets.size() == 1)
            return ((HashSet<Integer>) sets.toArray()[0]);

        HashSet<HashSet<Integer>> retval = new HashSet<HashSet<Integer>>();


        int max = 0;
        for ( HashSet<Integer> s: sets){
            if (max_set == null)
                max_set = s;
            if (max_set.size()< s.size() && s.size()<=aforo ) {
                max_set = s;
            }
            for (HashSet<Integer> j:sets){
                if(max_set.size() < j.size() && j.size()<=aforo){
                    max_set = j;
                }
                HashSet<Integer> new_set = new HashSet<Integer>(s);
                new_set.addAll(j);
                if (new_set.size() <= max_set.size() || new_set.size() == 1 || new_set.size() > aforo){
                    continue;
                }

                if (peleas.compatible(new_set)){
                    retval.addAll(sets);
                    retval.remove(s);
                    retval.remove(j);
                    retval.add(new_set);
                    obtained = getSubsets(retval,peleas,aforo);
                    if (max_set.size()<= obtained.size() && obtained.size()<=aforo)
                        max_set = obtained;
                    }
                }
            }


        return max_set;
    }
}
