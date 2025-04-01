import java.util.Scanner;

/*
SOLUCIÓN DEL PRÁCTICO DE CASTILLA Y LEÓN 2020, OPCIÓN 1.

MIS ERRORES:
- Algún índice mezclado en el método "adjunto"
- No multiplicar por Math.pow(-1,i) en el cálculo del determinante por el desarrollo por adjunto de la primera fila.
- this(n) no estaba puesto en la primera fila porque realizaba alguna comprobación atnerior.
- Inconsistencia en los métodos get y set (no los incorporé desde el principio)


*/
public class matrizCuadrada {
    private double[][]matriz;

    public matrizCuadrada(double[][] array){
        /* TODO: Querría hacer comprobaciones antes de llamar al otro constructor,  pero tengo entendido que no es posible. Ahora lo pruebo*/
        this(array.length);
        int n = array.length;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matriz[i][j] = array[i][j];
    }

    public matrizCuadrada(int n){
        matriz = new double[n][n];
    }

    public void leerMatrizCuadrada(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la dimensión:");
        int n = Integer.parseInt(sc.nextLine());
        matriz = new double[n][n];
        System.out.println("Introduce cada fila separada por espacios");
        for (int i = 0,j=0; i < n; i++,j=0)
            for (String s: sc.nextLine().split(" "))
                matriz[i][j++] = Double.parseDouble(s);
        sc.close();
    }
    public int getDimension(){
        return this.matriz.length;
    }

    public double get(int i,int j){
        return matriz[i][j];
    }

    public void set(int i, int j, double val){
        matriz[i][j] = val;
    }

    public matrizCuadrada divideMatrizPorEscalar (double lambda) {
        int n = getDimension();
        matrizCuadrada m = new matrizCuadrada(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m.set(i, j, get(i, j) * 1 / lambda);
            }

        }
        return m;
    }

    public matrizCuadrada multiplicaMatriz(matrizCuadrada m) {

        if (getDimension() != m.getDimension()) {
            System.out.println("ERROR: Dimensiones diferentes");
            return null;
        }
        int n = m.getDimension();
        matrizCuadrada retval = new matrizCuadrada(n);
        double d = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    d += get(i, k) * m.get(k, j);
                }
                retval.set(i, j, d);
                d = 0;
            }
        }
        return retval;
    }

    public matrizCuadrada divideMatriz(matrizCuadrada m){
        return multiplicaMatriz(m.matrizInversa());
    }

    public matrizCuadrada matrizAdjunta(){
        matrizCuadrada retval = new matrizCuadrada(getDimension());
        int n = retval.getDimension();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                retval.set(i,j,adjunto(i,j)*Math.pow(-1,i+j+2));

        return retval;
    }

    public matrizCuadrada matrizTraspuesta(){
        matrizCuadrada retval = new matrizCuadrada(getDimension());
        int n = retval.getDimension();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                retval.set(i,j,this.get(j,i));

        return retval;
    }


    // A papel lo hice mal. Confundí los índices en un par de sitios.
    public double adjunto (int fila,int columna){
        int n = this.getDimension();
        matrizCuadrada retval = new matrizCuadrada(n-1);
        for (int _i = 0,i=0; _i < n; _i++) {
            if (_i == fila) continue;
            for (int _j = 0,j=0; _j < n; _j++) {
                if (_j == columna) continue;
                retval.set(i,j,this.get(_i,_j));
                j++;
            }
            i++;
        }
        return retval.calculaDeterminante();
    }

    public double calculaDeterminante(){
        double retval = 0;
        if (getDimension() == 1){
            return matriz[0][0];
        }
        double[] fila = matriz[0];
        for (int i = 0; i < getDimension(); i++)
            retval += fila[i]*this.adjunto(0,i)*Math.pow(-1,i);
        if (retval == 0){
            System.out.println("Determinante 0");
        }
        return retval;
    }

    public matrizCuadrada matrizInversa(){
        return (this.matrizAdjunta()).matrizTraspuesta().divideMatrizPorEscalar(this.calculaDeterminante());
    }

    public void mostrarMatriz(){
        for (double[] fila : matriz) {
            for (double e : fila)
                System.out.print(e + " ");
            System.out.println("");
        }
    }

    public static void main(String[] args){
        matrizCuadrada m = new matrizCuadrada(3);
        m.leerMatrizCuadrada();
        (m.multiplicaMatriz(m.matrizInversa())).mostrarMatriz();
    }

}
