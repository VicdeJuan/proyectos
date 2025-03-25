import java.util.Scanner;
public class Diagoland169 {

    static final int OUT = 0;
    static final int IN = 1;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int ancho,largo;
        String[] leido = null;
        String linea;
        int in_out = OUT;
        double[] res = new double[2];


        while (true) {
            // Reiniciamos valores
            res[IN] = 0;
            res[OUT] = 0;
            // Leemos línea nueva
            leido = sc.nextLine().split(" ");
            ancho = Integer.parseInt(leido[0]);
            largo = Integer.parseInt(leido[1]);

            // Condición de parada
            if (ancho == 0 && largo == 0)
                break;

            for (int i = 0; i < largo; i++) {
                in_out = OUT;
                linea = sc.nextLine();
                for (int j = 0; j < ancho; j++) {
                    if (linea.charAt(j) == '.'){
                        res[in_out] += 1;
                    } else{
                        if (in_out == IN)
                            in_out = OUT;
                        else
                            in_out = IN;

                        res[IN] += 1.0/2;
                        res[OUT] += 1.0/2;
                    }
                }
            }
            //System.out.println(res[IN]+" + " + res[OUT] + "= "+ancho*largo);
            System.out.println((int)res[IN]);
            //assert res[IN] + res[OUT] == ancho*largo : "No suma el área total :(";

        }

        sc.close();
    }
}
