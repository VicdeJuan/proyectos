package WA;

import java.util.Scanner;

//https://aceptaelreto.com/problem/statement.php?id=110

/*
4
Era un simple clerigo, pobre de clerecia,
dicie cutiano missa de la sancta Maria;
non sabie decir otra, diciela cada dia,
mas la sabie por uso qe por sabiduria.
3
Un manotazo duro, un golpe helado,
un hachazo invisible y homicida,
un empujon brutal te ha derribado.

El segundo caso lo hago bien, pero el primero no.

¿Cómo reconocer la última sílaba? Podrían ser palabras que terminaran en 2 vocales siendo agudas?

Un bello anillo, brillante y sombrío,
fue el mejor y más preciado obsequio.

¿Esto qué es? Si el de María es, este también debería.
 */
public class estrofas110{

    public static boolean esVocal(char c){
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int numversos = -1,flag = 0;
        String palabra = "", linea = "";
        String[] rimacons = null, rimaas = null;


        while(numversos != 0){
            numversos = sc.nextInt();
            linea = sc.nextLine();// Consumimos el salto de línea
            rimacons = new String[numversos];
            rimaas = new String[numversos];

            for(int i=0;i<numversos;i++){
                linea = sc.nextLine();
                rimacons[i] = "";
                rimaas[i] = "";
                flag = 0;
                for (int j=linea.length()-1;j>=0;j--){

                    char c = linea.charAt(j);
                    if (!(97<=c && c<=122 ) // minuscula
                            && !(65<=c && c<= 90)) // Mayúscula
                        continue;
                    rimacons[i] += (""+c);
                    if (esVocal(c))
                        rimaas[i] += (""+c);
                    if (flag == 2 && esVocal(c)){
                        flag = 3;
                        break;
                    }else if(flag == 1 && !esVocal(c)){
                        flag = 2;
                    } else if (flag == 0 && esVocal(c)){
                        flag = 1;
                    }
                }
            }
            // Leidos los versos:

            if (numversos == 2){
                if (rimacons[0].equals(rimacons[1]))
                    System.out.println("PAREADO");
                else
                    System.out.println("DESCONOCIDO");
            }
            if (numversos == 3){
                if (rimacons[0].equals(rimacons[2]) && !rimacons[0].equals(rimacons[1]))
                    System.out.println("TERCETO");
                else
                    System.out.println("DESCONOCIDO");
            }
            if (numversos == 4){
                if (rimacons[0].equals(rimacons[3]) && rimacons[1].equals(rimacons[2]) && rimacons[0].equals(rimacons[1])){
                    System.out.println("CUADERNA VIA");
                } else if (rimacons[0].equals(rimacons[3]) && rimacons[1].equals(rimacons[2]) && !rimacons[0].equals(rimacons[1])){
                    System.out.println("CUARTETO");
                }else if (rimacons[0].equals(rimacons[2]) && rimacons[1].equals(rimacons[3]) && !rimacons[0].equals(rimacons[1])){
                    System.out.println("CUARTETA");
                } else if(rimaas[1].equals(rimaas[3]) && !rimacons[1].equals(rimacons[3])){
                    if (!rimaas[1].equals(rimaas[2]) && !rimaas[1].equals(rimaas[0]) && !rimaas[0].equals(rimaas[2])){
                        System.out.println("SEGUIDILLA");
                    } else{
                        System.out.println("DESCONOCIDO");
                    }
                }else{
                    System.out.println("DESCONOCIDO");
                }
            }

        }
        sc.close();
    }

}