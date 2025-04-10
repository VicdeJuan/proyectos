package AC;

import java.util.ArrayList;
import java.util.Scanner;

class orbital{
    int nivel,max;
    char letra;

    public orbital(int n,char l){
        nivel = n;
        letra = l;
        if (l == 's')
            max = 2;
        else if (l== 'p')
            max = 6;
        else if (l == 'd')
            max = 10;
        else
            max = 14;
    }

    public String print(int n){
        return ""+nivel + letra+ (n<max?n:max);
    }
    public int getMax(){
        return max;
    }
}

public class quimica111{


    public static ArrayList<orbital> rellenarOrbitales(){
        ArrayList<orbital> retval = new ArrayList<orbital>();

        retval.add(new orbital(1,'s'));
        retval.add(new orbital(2,'s'));
        retval.add(new orbital(2,'p'));
        retval.add(new orbital(3,'s'));
        retval.add(new orbital(3,'p'));
        retval.add(new orbital(4,'s'));
        retval.add(new orbital(3,'d'));
        retval.add(new orbital(4,'p'));
        retval.add(new orbital(5,'s'));
        retval.add(new orbital(4,'d'));
        retval.add(new orbital(5,'p'));
        retval.add(new orbital(6,'s'));
        retval.add(new orbital(4,'f'));
        retval.add(new orbital(5,'d'));
        retval.add(new orbital(6,'p'));
        retval.add(new orbital(7,'s'));
        retval.add(new orbital(5,'f'));
        retval.add(new orbital(6,'d'));
        retval.add(new orbital(7,'p'));

        return retval;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        ArrayList<orbital> orbitales = rellenarOrbitales();

        int electrones;
        String elemento = sc.nextLine();
        while( !elemento.equals("Exit")){
            electrones = sc.nextInt();
            elemento=sc.nextLine(); // Consumimos el salto de línea
            if (electrones == 0){
                System.out.println("1s0");
                elemento = sc.nextLine();
                continue;
            }
            for (orbital o : orbitales){
                System.out.print(o.print(electrones));
                electrones -= o.getMax();
                if (electrones<=0){
                    System.out.println("");
                    break;
                }else{
                    System.out.print(" ");
                }
            }
            // Hemos terminado con el elemento, siguiente vuelta del bucle
            elemento = sc.nextLine();
        }

        sc.close();

    }



}