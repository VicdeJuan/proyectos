package TLE;

import java.util.*;

import java.io.DataInputStream;
import java.io.IOException;



class Cadena{
    final private String valor;

    public Cadena(String cadena){
        // Por si acaso la referencia que me llega es modificada posteriormente
        this.valor = new String(cadena);
    }

    private int getLength(){
        return this.valor.length();
    }

    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (!(o instanceof Cadena)) return false;

        Cadena other = ((Cadena) o);


        for (int i=0; i< this.getLength(); i++) {
            // En cuanto un nucleótido sea distinto, devuelvo false.
            if (! compareNucelotides(valor.charAt(i),other.valor.charAt(i)))
                return false;
        }

        // Si ningún nucleótido ha sido distinto, todos coinciden, así que devuelvo true.
        return true;
    }


    private boolean compareNucelotides(char c1, char c2){
        if (c1 == c2)
            return true;
        else if (c1 == '-' || c2 == '-')
            return true;
        else
            return false;
    }


}

public class ADN656 {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String leido;
        int numCadenas,contador;
        ArrayList<Cadena> listaCadenas;


        while(true){
            contador=0;
            try {
                leido = sc.nextLine();
            } catch (Exception e){
                break;
            }
            if (leido == "0")
                break;
            numCadenas = Integer.parseInt(leido);
            // Creamos un arrayList (porque vamos a leer mucho y escribir poco)
            // con tantos elementos como vayamos a necesitar.
            listaCadenas = new ArrayList<>();

            for (int i = 0;i<numCadenas ;i++ )
                listaCadenas.add(new Cadena(sc.nextLine()));

            for (int i=0; i<numCadenas; i++) {
                for (int j=0; j<numCadenas; j++) {
                    if (i==j) continue;
                    if (listaCadenas.get(i).equals(listaCadenas.get(j)))
                        contador++;
                }
                System.out.print(contador);
                if (i<numCadenas-1)
                    System.out.print(" ");
                contador=0;
            }
            System.out.println("");

        }
        sc.close();
    }


}
