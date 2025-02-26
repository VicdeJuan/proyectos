#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>


void comprobarDiabolico(int** matrix,int n){
	/*Se considera un cuadrado mágico diabolico a la disposición de una serie 
	de números enteros en un cuadrado de forma tal que:
	- la suma de los números por
		 columna, 
		 fila y 
		 diagonales principales 
	 sea la misma.*/

	int * suma_filas = (int *) calloc(n,sizeof(int));
	int * suma_columnas = (int *) calloc(n,sizeof(int));
	


	int * cifras = (int *) calloc(n*n+1,sizeof(int));
	for (int i = 0; i <= n*n; ++i)
	{
		cifras[i] = i; 
		/*En este array guardo los nuḿeros del 1 al n² para comprobar
		que sea esotérico simplemente restando. Así, al terminar el programa,
		si este array solo tiene ceros es porque se cumple la primera propiedad*/
	}

	int suma_diagonal_principal = 0;
	int suma_diagonal_secundaria = 0;

	int diabolico = 1,esoterico = 1;
	
	for (int i = 0; i < n; ++i)
	{
		for (int j = 0; j < n; ++j){
			if (i==j)
				suma_diagonal_principal += matrix[i][j];
			if (i == n-j-1)
				suma_diagonal_secundaria += matrix[i][j];
			suma_filas[i] += matrix[i][j];
			suma_columnas[j] += matrix[i][j];
			
			// 
			if (esoterico && matrix[i][j] < n*n+1)
				if (cifras[matrix[i][j]] != 0)
					cifras[matrix[i][j]] -= matrix[i][j];
				else // Solo entrará a este else si hay cifras repetidas
					esoterico = 0;
			 
		}
		printf("\n");
	}
	int constante_magica_2 = 4*suma_diagonal_principal/n;
	if (matrix[0][0] + matrix[0][n-1] + matrix[n-1][0] + matrix[n-1][n-1] != constante_magica_2)
		esoterico = 0;
	
	int mitad,mitad_1,mitad_2;
	if (n%2 == 0) { // PAR	
		mitad_1 = n/2;
		mitad_2 = mitad_1 - 1;

		if (matrix[mitad_1][0] +
		 	matrix[0][mitad_1] +
		 	matrix[mitad_1][n-1] +
		 	matrix[n-1][mitad_1] +
			matrix[mitad_2][0] +
			matrix[0][mitad_2] +
			matrix[mitad_2][n-1] +
			matrix[n-1][mitad_2] 
			!= 2* constante_magica_2){

			esoterico = 0;
		
		}else if (
			matrix[mitad_1][mitad_1] +
		 	matrix[mitad_1][mitad_2] + 
		 	matrix[mitad_2][mitad_1] + 
		 	matrix[mitad_2][mitad_2]  != constante_magica_2)
		{
			esoterico = 0;	
		}		
	}else{ // IMPAR
		mitad = (n-1)/2;
		if (matrix[mitad][0] + matrix[0][mitad] + matrix[mitad][n-1] + matrix[n-1][mitad] != constante_magica_2){
			esoterico = 0;
		}else if (4*matrix[mitad][mitad] != constante_magica_2)
		{
			esoterico = 0;
		}
	}
	


	for (int i = 0; i < n; ++i){
		if (suma_filas[i] != suma_diagonal_principal)
			diabolico = 0;
		if (suma_columnas[i] != suma_diagonal_principal)
			diabolico = 0;
	}

	if (suma_diagonal_secundaria != suma_diagonal_principal)
		diabolico = 0;
	
	if (diabolico){
		for (int i = 0; i <= n*n; ++i)
			if (cifras[i] != 0){
				esoterico = 0;
				break;
			}
		
		if (esoterico)
			printf("ESOTERICO\n");
		else
			printf("DIABOLICO\n");
	}else
		printf("NO\n");
	
	
	free(suma_filas);
	free(suma_columnas);
	free(cifras);
	return;
}

int main(){
	int n;

	scanf(" %d",&n);
	while (n!=0) {


		int** matrix = (int **) malloc(n*sizeof(int*));
		if(matrix == NULL)
			return EXIT_FAILURE;
		for (int i = 0; i < n; ++i)
		{
			matrix[i] = (int *) malloc(n*sizeof(int));
			if (matrix[i] == NULL){
				for (int j = 0; j < i; ++j)
					free(matrix[j]);
				free(matrix);
				return EXIT_FAILURE;
			}
		}

		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				scanf(" %d",&matrix[i][j]);
			
		

		comprobarDiabolico(matrix,n);


		for (int i = 0; i < n; ++i)
		{
			free(matrix[i]);
		}
		free(matrix);
		scanf(" %d",&n);
	}
}