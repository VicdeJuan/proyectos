#include <stdio.h>
#include <stdlib.h>

#define TRUE 1
#define FALSE 0

int comprobarComponentesConexas(int ** matrix, int num_nodos){
	int visitado[num_nodos],origenes[num_nodos];
	for (int i=0; i<num_nodos;i++){
		visitado[i] = FALSE;
		origenes[i] = FALSE;
	}

	int visitas = 0,origen=0,old_origen;
	int loop = TRUE,componentesConexas=0;

	

	// Guardo el origen de la primera componente
	origenes[componentesConexas] = origen;

	while(loop == TRUE){
		// Recorro hacia adelante
		old_origen = origen;

		
		for(int j=0;j<num_nodos;j++){
			// Repito moverme de nodo tantas veces como nodos haya.
			for(int i=0; i<num_nodos; i++){
				// Busco a quién regala y me voy a ese;
				if (matrix[origen][i] == 1 && visitado[i] == FALSE){
					printf("Recorriendo (%d,%d)\n",origen+1,i+1);
					visitado[origen]=TRUE;
					origen = i;
					visitas++;
					break;
				}
			}
		}
		
		// Recorro hacia atrás para empezar en el principio de la cadena.
		for (int i = 0; i < num_nodos; ++i)
		{
			for (int j = 0; j < num_nodos; ++j)
			{
				if (matrix[j][old_origen] == 1 && visitado[j] == FALSE){
					printf("Recorriendo (%d,%d)\n",j+1,old_origen+1);
					old_origen = j;
					visitado[j] = TRUE;
					visitas++;
					break;
				}
			}
		}

		// Ya he hecho todo el recorrido. Busco la siguiente componente conexa

		origen = -1;
		// Busco un nodo que regale a alguien y lo guardo en origen.
		for (int i = 0; i < num_nodos && origen == -1; ++i)
		{
			if (visitado[i] == FALSE){
				for (int j = 0; j < num_nodos && origen == -1; ++j)
					if (matrix[i][j] == 1)
						origen=i;
			}			
		}

		if (origen == -1)
			loop = FALSE; // No hay más posibilidades de recorridos.
		else{
			componentesConexas++;
			origenes[componentesConexas] = origen;
		}
	}


	// Comprobamos si las diferentes componentes conexas 
	if (componentesConexas == 0 && visitas + 1 >= num_nodos-1)
		return TRUE;
	else if (componentesConexas == 0 && visitas >= num_nodos)
		return TRUE;
	else if (componentesConexas == 1 && visitas == num_nodos)
		return FALSE;
	else if (componentesConexas > 1)
		return FALSE;
	else{
		// Comprobamos si las componentes conexas solo se pueden regalar de una forma
		// Busco un nodo no visitado (que por tanto no regala a nadie)
		for (int i = 0; i < num_nodos; ++i)
			if (visitado[i] == FALSE){
				origen=i;
				break;
			}
		// ¿A cuántos puede regalar?
		return FALSE;
		
	}
}

int main(){
	int num_nodos=-1, num_aristas=-1;

	int** matrix;
	while(1){
		scanf("%d %d",&num_nodos,&num_aristas);
		if (num_nodos == 0 && num_aristas == 0){
			break;
		}
		matrix = (int **) calloc(num_nodos,sizeof(int*));
		if (matrix == NULL)
			return -1;
		for(int i=0;i<num_nodos;i++){
			matrix[i] = (int*) calloc(num_nodos,sizeof(int));
			if (matrix[i] == NULL){
				for(int j=0;j<i;j++)
					free(matrix[j]);
				free(matrix);
				return -1;
			}
		}
		int origen,destino;
		for(int i = 0;i<num_aristas;i++){
			scanf("%d %d",&origen,&destino);
			matrix[origen-1][destino-1]=1;
		}

		if (comprobarComponentesConexas(matrix,num_nodos))
			printf("SI\n");
		else
			printf("NO\n");
		for(int i=0; i<num_nodos; i++)
			free(matrix[i]);
		free(matrix);
	}
	
}
