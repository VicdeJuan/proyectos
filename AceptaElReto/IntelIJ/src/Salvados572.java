import java.util.Scanner;

public class Salvados572 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int vueltas = sc.nextInt();
        sc.nextLine(); // Consumimos salto de línea

        boolean cero = false, nueve = false;

        double valor = -1;

        for (int i =0; i< vueltas; i++){
            for (String nota : sc.nextLine().split(" ")) {
                valor = Double.parseDouble(nota);
                if (valor == 0)
                    cero = true;
                else if (valor >= 9)
                    nueve = true;
            }
            if (cero && ! nueve)
                System.out.println("SUSPENSO DIRECTO");
            else
                System.out.println("MEDIA");

            nueve = false; // Me lo olvidé en el papel
            cero = false; // Me lo olvidé en el papel
        }

        sc.close();
    }
}
