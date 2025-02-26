package aceptaElReto;

import java.io.IOException;
import java.util.Scanner;


public class Herencias_103 {

	public final static double UMBRAL = 0.001 ;
	
	public static double compute_value(int[] pol,double x) {
		double retval = 0;
		for (int i = 0; i<pol.length; i++) 
			retval += pol[i]*Math.pow(x, i);
		
		if (retval >= 1)
			return 1;
		else if (retval < 0)
			return 0;
		
		return retval;
	}
	
	public static void main(String[] args) throws IOException {
		// Java program to demonstrate BufferedReader

		// Enter data using BufferReader
		Scanner scanner = new Scanner(System.in);
        
		
		// Reading data using readLine
		int degree = scanner.nextInt();
		while(degree <20) {
			int[] polinomio = new int[degree+1];
			
			for(int i = polinomio.length-1 ; i>=0; i--)
				polinomio[i] = scanner.nextInt();
			
			
			
			int particion = scanner.nextInt();
			double fraction = 1/((double) particion);
			
			// Printing the read line
			double area_cain = 0;
			for (int i=0; i<particion; i++) {
				area_cain += compute_value(polinomio,i*fraction) * fraction;
			}
			double area_abel = 1 - area_cain;
			    
			/*System.out.println(area_cain);
			System.out.println(area_abel);*/
			
			if (Math.abs(area_abel - area_cain ) <= UMBRAL )
				System.out.println("JUSTO");
			else if (area_abel > area_cain)
				System.out.println("ABEL");
			else 
				System.out.println("CAIN");
			degree = scanner.nextInt();
		}	
		scanner.close();
		
	}


	

}
