#include <stdlib.h>
#include <stdio.h>


/**

Síes:


6 5
1 2 
2 1
3 4
4 3
5 6

6 4
1 2
2 3
3 1
4 5


6 5
1 2
2 3
3 1
4 5
5 6

2 4
1 2
3 4
4 1
2 3

5 4
1 2
2 3
3 4

Noes:





 
 **/

int comprobarCicloCerrado(int*regala,int*es_regalado,int num_nodos){
	int sin_regalar = 0;
	int sin_ser_regalado = 0;
	int sin_ambos = 0;
	int i=0,j=0,k;

	for(i=0;i<num_nodos;i++){
		if (regala[i] == -1 && es_regalado[i] != -1)
			sin_regalar++;
		else if (regala[i] != -1 && es_regalado[i] == -1)
			sin_ser_regalado++;
		else if (regala[i] == es_regalado[i] && regala[i] == -1) // No se puede regalar a sí mismo.
			sin_ambos++;
	}


	// Busco ciclos
	int visitado[num_nodos];
	for(i = 0; i<num_nodos;i++)
		visitado[i] = 0;

	int origen=0,color=1,por_visitar = num_nodos;
	for(j=0;j<num_nodos;j++){
		for (i = 0; i < num_nodos; ++i){
			if(visitado[origen] != 0){
				color++;
				break;
			}
			if (regala[origen] != -1){
				visitado[origen] = color;
				origen=regala[origen];
				por_visitar--;				
			}
		}
		if (por_visitar >= sin_regalar){
			// Todavía puedo visitar alguno más
			for(k = 0; k<num_nodos;k++){
				if (visitado[k] == 0 && regala[k] != -1){
					origen = k;
					color++;
					break;
				}
			}
		}
	}
			

	if (sin_regalar <= 1 && sin_ambos == 0)
		return 1;
	
	if(sin_regalar == 1 && sin_ambos == 1)
		return 2;


	if (color > 2)
		return 0;

	if (sin_ambos > 1)
		return 0;



	return 0;

}

int main(){
	int num_nodos=-1, num_aristas=-1,i=0;

	int * regala;
	int * es_regalado;

	while(1){
		scanf("%d %d",&num_nodos,&num_aristas);
		
		if (num_nodos == 0 && num_aristas == 0){
			break;
		}
		
		if (num_aristas < num_nodos-2){
			printf("NO\n");
			continue;
		}

		regala = (int *) malloc(num_nodos * sizeof(int));
		if(regala == NULL){
			printf("NO");
			continue;
		}

		es_regalado = (int *) malloc(num_nodos * sizeof(int));
		if(es_regalado == NULL){
			printf("NO");
			continue;
		}

		if(regala == NULL)
			printf("NO");

		for(i=0;i<num_nodos;i++){
			regala[i] = -1;	
			es_regalado[i] = -1;
		} 

		if (regala == NULL || es_regalado == NULL)
			return -1;
		

		int origen,destino,error = 0;

		for(i = 0;i<num_aristas;i++){
			scanf("%d %d",&origen,&destino);
			// Traducimos a contar desde el 0.
			origen--;
			destino--;
			if (error == 1 || destino >= num_nodos || origen >= num_nodos || destino < 0 || origen < 0)
				error=1;
			else{
				if (regala[origen] != -1 || es_regalado[destino] != -1){
					if (destino != regala[origen]){
						error=1;
					}
				}
				regala[origen] = destino;
				es_regalado[destino] = origen;
			}
		}

		if (num_nodos == 1)
			printf("SI\n");
		else if (error)
			printf("NO\n");
		else{
			int ret = comprobarCicloCerrado(regala,es_regalado,num_nodos);
			if (ret)
				//printf("SI %d\n",ret);
				printf("SI\n");
			else
				printf("NO\n");
		}

		free(regala);
		free(es_regalado);
	}

	return 0;
	
}
