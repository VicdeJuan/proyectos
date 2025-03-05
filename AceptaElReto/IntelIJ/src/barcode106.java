import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/** VEREDICTO: Wrong Answer **/


/**
 * CASOS DE PRUEBA

 65839522
 65839521
 8414533043847
 5029365779425
 5129365779425
 0
 */
public class barcode106 {

    public static int[] reverse(int[] arg,  boolean EAN13){

        int n = EAN13 ? 13 : 8;
        int[] retval = new int[n];


        for(int i = n-1; i>=0;i--)
            retval[n-1-i] = i<arg.length ? arg[i] : 0;


        return retval;
    }

    public final static Map<Integer,String> tabla = Map.of(0,"EEUU",
            539,"Irlanda",
            759,"Venezuela",
            380,"Bulgaria",
            560,"Portugal",
            850,"Cuba",
            50,"Inglaterra",
            70,"Noruega",
            890,"India");

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String readed = sc.next();

        while(!readed.equals("0")) {


            int num_char = readed.length();
            boolean EAN13;

            EAN13 = num_char > 8;

            int[] numeros = readed.chars().map(c -> c - '0').toArray();
            int[] numeros_reversed = reverse(numeros, EAN13);

            int sum = 0, country_code_2_digits = 0, country_code_3_digits = 0;

            // Empezamos en 1 porque el 0 será el de control
            int limit = EAN13 ? 13 : 8;
            int last_char_country_code = num_char - 3;

            for (int i = 1; i < limit; i++) {
                if (EAN13) {
                    switch (i - last_char_country_code) {
                        case 0:
                            country_code_3_digits += numeros_reversed[i];
                            break;
                        case 1:
                            country_code_2_digits += numeros_reversed[i];
                            country_code_3_digits += 10 * numeros_reversed[i];
                            break;
                        case 2:
                            country_code_2_digits += 10*numeros_reversed[i];
                            country_code_3_digits += 100* numeros_reversed[i];
                            break;
                    }
                }
                if (i % 2 == 1) //Posición impar
                    sum += 3 * numeros_reversed[i];
                else
                    sum += numeros_reversed[i];
            }

            int control = (sum / 10 + 1) * 10 - sum;

            String toprint = "", country = "";

            if (control == numeros_reversed[0])
                toprint += "SI";
            else
                toprint += "NO";

            if (EAN13 && toprint.equals("SI")) {
                toprint += " ";
                if (tabla.containsKey(country_code_2_digits))
                    toprint += tabla.get(country_code_2_digits);
                else if (tabla.containsKey(country_code_3_digits))
                    toprint += tabla.get(country_code_3_digits);
                else
                    toprint += "Desconocido";


            }
            System.out.println(toprint);
            readed = sc.next();
        }




        sc.close();
    }

}
