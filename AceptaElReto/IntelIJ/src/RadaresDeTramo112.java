import java.util.Scanner;
public class RadaresDeTramo112 {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String[] leido;
        int distancia = -1, vel_lim = -1, tiempo_s = -1;
        double vel_media;
        boolean continuar = true;

        while(continuar){
            leido = sc.nextLine().split(" ");
            distancia = Integer.parseInt(leido[0]);
            vel_lim = Integer.parseInt(leido[1]);
            tiempo_s = Integer.parseInt(leido[2]);
            vel_media = (distancia * 1.0 / 1000) / (tiempo_s *1.0/3600); // Velocidad tramo en Km/h
            if (distancia == 0  && vel_lim == 0 && tiempo_s == 0)
                continuar = false;
            else if (distancia <= 0 || vel_lim <= 0 || tiempo_s <= 0)
                System.out.println("ERROR");
            else if (vel_media >= 1.2 * vel_lim)
                System.out.println("PUNTOS");
            else if(vel_media >= vel_lim)
                System.out.println("MULTA");
            else
                System.out.println("OK");
        }
        sc.close();
    }
}
