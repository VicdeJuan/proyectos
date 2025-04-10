import java.util.Arrays;
import java.util.Scanner;

public class pagarConMonedas {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String[] leido;
        int[] monedasDisponibles;
        int precio = -1;

        System.out.println("Introduce las monedas disponibles (separadas por espacios):");
        leido = sc.nextLine().split(" ");
        // ERROR: leido.size no existe. Cambiamos a leido.length.
        monedasDisponibles = new int[leido.length];
        for (int i = 0; i < leido.length; i++)
            monedasDisponibles[i] = Integer.parseInt(leido[i]);


        // ordenamos las monedas
        Arrays.sort(monedasDisponibles);

        // ERROR: la condición de parada no estaba bien implementada.
        while (precio != 0) {
            System.out.println("¿Cuánto quieres pagar? (Introduce 0 para salir):");
            precio = Integer.parseInt(sc.nextLine());
            if (precio != 0) {
                //voraz(monedasDisponibles, precio,true);
                dinamico(monedasDisponibles, precio);
            }
        }
        sc.close();
    }

    static int[] voraz(int[] monedas, int cantidad, boolean print) {
        if (print)
            System.out.println("\nAlgoritmo Voraz:");
        int[] monedasUsadas = new int[monedas.length];

        int cantidadRestante = cantidad;

        for (int i = monedas.length - 1; i >= 0; i--) {
            while (cantidadRestante >= monedas[i]) {
                cantidadRestante -= monedas[i];
                monedasUsadas[i]++;
            }
        }

        // ERROR: no controlaba si el algoritmo voraz no podía dar solución.
        // ERROR: los arrays de JAVA no tienen un toString por defecto.

        if (cantidadRestante == 0) {
            if (print){
                System.out.println("Se pueden pagar " + cantidad + " con las siguientes monedas:");

                for (int i = monedas.length - 1; i >= 0; i--)
                    if (monedasUsadas[i] > 0)
                        System.out.println(monedasUsadas[i] + " moneda(s) de " + monedas[i]);
            }
            return monedasUsadas;
        } else {
            if (print)
                System.out.println("No se puede pagar exactamente " + cantidad + " con el algoritmo voraz.");
            return null;
        }

    }


    // ERROR: los bucles hasta "precio" no tienen sentido si la moneda más pequeña no es de 1
    static void dinamico(int[] monedas, int precio) {
        System.out.println("\nProgramación Dinámica:");

        int [][][] contador = new int[monedas.length][precio][monedas.length];
        int [] resvoraz,dispo;
        int moneda_actual,idx_moneda_actual;

        for (int numMonedas = 1; numMonedas <= monedas.length; numMonedas++) {
            idx_moneda_actual = numMonedas-1;
            moneda_actual = monedas[idx_moneda_actual];
            for (int cant = 1; cant <= precio; cant++) {
                dispo = Arrays.copyOfRange(monedas,0,numMonedas);
                resvoraz = voraz(dispo,cant,false);

                if (resvoraz.length != monedas.length){
                    int[] newresvoraz = new int[monedas.length];
                    for (int i = 0; i < resvoraz.length; i++) {
                        newresvoraz[i] = resvoraz[i];
                    }
                    resvoraz = newresvoraz;
                }

                if (numMonedas == 1)
                    contador[numMonedas-1][cant-1] = Arrays.copyOf(resvoraz,resvoraz.length);
                else {
                    // Por defecto, solución voraz.
                    contador[numMonedas-1][cant-1] = resvoraz;
                    // Comparo con la fila anterior
                    int monedas_arriba = _contarMonedas(contador[numMonedas-2][cant-1]);
                    if (monedas_arriba<_contarMonedas(resvoraz))
                        contador[numMonedas-1][cant-1] = contador[numMonedas-2][cant-1];

                    // Comparo con la izquierda
                    if (cant-1-moneda_actual >= 0) {
                        int monedas_izq = _contarMonedas(contador[numMonedas - 1][cant - 1 - moneda_actual]);
                        if (monedas_izq+1 < _contarMonedas(contador[numMonedas-1][cant-1])){
                            contador[numMonedas-1][cant-1] = contador[numMonedas - 1][cant - 1 - moneda_actual];
                            contador[numMonedas-1][cant-1][idx_moneda_actual]++;
                        }
                    }
                }
            }

        }
        if (contador[monedas.length-1] != null)
            if (contador[monedas.length-1][precio-1] != null)
                for (int n : contador[monedas.length-1][precio-1])
                    System.out.println(n+" ");

    }

    private static int _contarMonedas(int[] ints) {
        int retval = 0;
        for (int a:ints)
            retval+=a;
        return retval;
    }

    private static int[] _countCoins(int[] a,int[]b,int l){
        int[] retval = new int[3];
        for (int i = 0; i < l; i++) {
            if (a != null)
                if (a[i] != 0)
                    retval[0]+=a[i];
            if (b!= null)
                if (b[i] != 0)
                    retval[1]+=b[i];
        }
        return retval;
    }

    private static int[] _countCoins(int[] a,int[]b,int[] c,int l){
        int[] retval = new int[3];

        for (int i = 0; i < l; i++) {
            if (a != null)
                if (i<a.length)
                    retval[0]+=a[i];
            if (b != null)
                if (i<b.length)
                    retval[1]+=b[i];
            if (c != null)
                if (i< c.length)
                    retval[2]+=c[i];

        }
        return retval;
    }
    private static void _updateFewerCoins(int[] resultado, int[] anterior, int[] resvoraz,int length) {
        int[] coins = _countCoins(resultado,anterior,resvoraz,length);
        if (coins[1] < coins[0] && coins[1] <= coins[2] && anterior != null)
            resultado = Arrays.copyOf(anterior,anterior.length);
        else if (coins[2]<coins[0] && coins[2] <= coins[1] && resvoraz != null)
            resultado = Arrays.copyOf(resvoraz,resvoraz.length);

    }

    private static void _updateFewerCoins(int[] resultado, int[] anterior,int length) {
        int[] coins = _countCoins(resultado,anterior,length);
        if (coins[1] < coins[0] && anterior != null)
            resultado = Arrays.copyOf(anterior,length);
        else if (resultado == null)
            resultado = Arrays.copyOf(anterior,length);

    }
}