package AC;

import java.util.Scanner;

public class Jaen283Grafo {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        boolean keep_going = true;
        int ancho,alto;

        while(keep_going) {
            String _leido;
            try {
                _leido = sc.nextLine();
            } catch (Exception e) {
                break;
            }

            if (_leido == "")
                break;
            String[] leido = _leido.split(" ");
            alto = Integer.parseInt(leido[0]);
            ancho = Integer.parseInt(leido[1]);

            int[][] matrix = new int[ancho][alto];
            String linea;
            char c;

            for (int i = 0; i < ancho; i++) {
                linea = sc.nextLine();
                for (int j = 0; j < alto; j++) {
                    c = linea.charAt(j);

                }
            }
        }


            sc.close();
    }
}
