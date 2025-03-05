import java.util.Scanner;

public class escaleras688 {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int diff_contra_consec,diff_contra_global;
        int num;
        int altura_escalon[];
        int contrahuellas[];
        boolean terminar = true;

        do {
            terminar = true;

            diff_contra_consec = sc.nextInt();
            if (diff_contra_consec != 1)
                terminar = false;

            diff_contra_global = sc.nextInt();
            if(diff_contra_global != 3 && terminar)
                terminar = false;


            num = sc.nextInt();
            if (num != 5 && terminar)
                terminar = false;

            altura_escalon = new int[num + 1];
            contrahuellas = new int[num];


            altura_escalon[0] = sc.nextInt();
            if (altura_escalon[0] != 0 && terminar)
                terminar = false;
            int sum = 0;
            for (int i = 1; i <= num; i++) {
                altura_escalon[i] = sc.nextInt();
                sum += i;
                if (sum != altura_escalon[i] && terminar)
                    terminar = false;
                contrahuellas[i - 1] = altura_escalon[i] - altura_escalon[i - 1];
            }

            // Una vez leidas todas las alturas:
            boolean tropiezo = false;
            for (int i = 1; i < num && !tropiezo; i++) {
                if (contrahuellas[i] - contrahuellas[i - 1] > diff_contra_consec)
                    tropiezo = true;
                for (int j = 0; j < i && !tropiezo; j++)
                    if (contrahuellas[i] - contrahuellas[j] > diff_contra_global)
                        tropiezo = true;
            }
            if (tropiezo)
                System.out.println("Tropiezo");
            else
                System.out.println("Ok");
        } while (!terminar);
        sc.close();
    }
}
