import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Utilities{
    public static boolean looksLike(String s1, String s2){
        return s1.equalsIgnoreCase(s2);
    }
}
class IES{
    String nombre = "";
    int codigo;
    HashMap<String,HashMap<String,Integer>> ciclos;
    // SMR -> {"diurno: 3", "vespertino: 0"}


    public IES(String archivo) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String currLine,codigo_str="";
        boolean acumToNombre = false,acumToCodigo=false;

        while((currLine = String.valueOf(br.readLine())) != null){
            if (acumToNombre)
                nombre += currLine;
            else if (acumToCodigo)
                codigo_str += currLine;
            if (Utilities.looksLike(currLine,"nombre del centro")){
                acumToNombre=true;
            } else if (Utilities.looksLike(currLine,"código del centro")) {
                acumToNombre = false;
                acumToCodigo = true;
            }else if (Utilities.looksLike(currLine,"tipo:")){
                acumToCodigo = false;
            }

        }
        codigo = Integer.parseInt(codigo_str);

    }

    @Override
    public String toString() {
        return nombre+codigo;
    }
}

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        IES a = null;
        try {
            a = new IES("FICHA_CENTRO_AFFNTA.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(a);
    }
}