import java.util.Scanner;

public class moviles104_recursivo {


    public static int calcularPesoSubmovil(Scanner scanner){
        int pi = scanner.nextInt();
        int di = scanner.nextInt();
        int pd = scanner.nextInt();
        int dd = scanner.nextInt();

        if (pi == 0)
            pi = calcularPesoSubmovil(scanner);
        if (pi == -1)
            return -1;

        if (pd == 0)
            pd = calcularPesoSubmovil(scanner);
        if (pd == -1)
            return -1;

        // Si no está equilibrado, return false
        if (pi*di != pd*dd)
            return -1;

        // Si está equilibrado, devuelvo su peso.
        return pi + pd;
    }

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);

        int pi = -1;
        int di = -1;
        int pd = -1;
        int dd = -1;
        int izq = 0,dcha=0;
        pi = scanner.nextInt();
        di = scanner.nextInt();
        pd = scanner.nextInt();
        dd = scanner.nextInt();

        while (pi+di+pd+dd != 0){
            if(pi == 0){
                izq = di * calcularPesoSubmovil(scanner);
            } else{
                izq = di*pi;
            }
            if (pd == 0){
                dcha = dd * calcularPesoSubmovil(scanner);
            }else {
                dcha = dd * pd;
            }

            if (izq == -1 || dcha == -1)
                System.out.println("NO");
            else if (izq == dcha)
                System.out.println("SI");
            else
                System.out.println("NO");

            pi = scanner.nextInt();
            di = scanner.nextInt();
            pd = scanner.nextInt();
            dd = scanner.nextInt();
        }

        scanner.close();
    }
}
