import java.util.*;

public class ADN656HappyIDea {
    public final static int GUION = 0;
    public final static int A = 1;
    public final static int C = 2;
    public final static int G = 3;
    public final static int T = 4;
    public final static int BASE = 5;

    public static boolean compare(long l1, long l2) {
        if (l1 == l2)
            return true;

        // Si no son iguales, resto y comparo las diferencias para ver si son guiones.


        // Se recorre posición a posición (en base BASE) hasta agotar los dígitos.
        while(l1 != 0 || l2 != 0) {
            int c1 = (int)(l1 % BASE);
            int c2 = (int)(l2 % BASE);
            // Si en la posición ambos son fijos y diferentes, no coinciden.
            if(c1 != GUION && c2 != GUION && c1 != c2)
                return false;
            l1 /= BASE;
            l2 /= BASE;
        }
        return true;
    }

    public static long transform(String s) {
        boolean prefix = true;
        boolean central = false;
        long retval = 0;
        long multiplier = 1;
        int num_guiones_intermedios = 0;
        char c;
        for (int i = 0; i < s.length(); i++){
            int value;
            c = s.charAt(i);
            if (prefix &&  c != '-'){
                prefix = false;
                central = true;
            } else if(central && num_guiones_intermedios <= 2){
                num_guiones_intermedios++;
            } else if(central && num_guiones_intermedios > 2){
                central = false;
                break;
            }

            if(central){
                switch(c) {
                    case '-': value = GUION; break;
                    case 'A': value = A; break;
                    case 'C': value = C; break;
                    case 'G': value = G; break;
                    case 'T': value = T; break;
                    default: value = 0;
                }
                retval += value * multiplier;
                multiplier *= BASE;
            }
        }
        return retval;
    }

    public static long _transform(String s) {
        long retval = 0;
        long multiplier = 1;
        for (int i = 0; i < s.length(); i++){
            int value;
            char c = s.charAt(i);
            switch(c) {
                case '-': value = GUION; break;
                case 'A': value = A; break;
                case 'C': value = C; break;
                case 'G': value = G; break;
                case 'T': value = T; break;
                default: value = 0;
            }
            retval += value * multiplier;
            multiplier *= BASE;
        }
        return retval;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String leido;
        int numCadenas, contador;
        long[] listaCadenas;
        int[] contadores;


        while (true) {
            contador = 0;
            try {
                leido = sc.nextLine();
            } catch (Exception e) {
                break;
            }
            if (leido == "")
                break;
            numCadenas = Integer.parseInt(leido);


            listaCadenas = new long[numCadenas];
            contadores = new int[numCadenas];

            for (int i = 0; i < numCadenas; i++) {
                listaCadenas[i] = transform(sc.nextLine());
                if (listaCadenas[i] == 0){
                    for (int j = 0; j < i; j++)
                        contadores[j]++;
                    contadores[i] = i;
                    continue;
                }
                for (int j = 0; j < i; j++) {
                    if (compare(listaCadenas[i],listaCadenas[j])) {
                        contadores[i]++;
                        contadores[j]++;
                    }
                }
            }
            for (int i = 0; i < numCadenas - 1; i++)
                System.out.print(contadores[i] + " ");
            System.out.println(contadores[numCadenas - 1]);


        }
        sc.close();
    }
}
