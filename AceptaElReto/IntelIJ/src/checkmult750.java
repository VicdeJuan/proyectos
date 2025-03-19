import java.util.Scanner;

public class checkmult750 {

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int leido = -1, num ,ceros = 0, checkmult=1;
        boolean fin;
        int digit;

        while(leido != 0) {
            // Reiniciamos valores;
            ceros = 0;
            checkmult = 1;
            fin = false;
            // Lemos siguiente números

            num = sc.nextInt();
            leido = num;
            // Empieza iteración

            while (num != 0 && !fin){
                // Calcular checkmult de un número
                while (num != 0) {
                    digit = num % 10;
                    if (digit == 0)
                        ceros++;
                    else
                        checkmult *= digit;
                    num /= 10;
                }
                num = checkmult;
                checkmult = 1;
                if (num /10 == 0)
                    fin = true;

            }
            //Imprimimos solo si no se ha dado un 0 como primera opción
            if (num == 0 && ceros == 0){

            }else {
                System.out.println("" + num + "" + ceros);
            }
        }
        sc.close();
    }
}
