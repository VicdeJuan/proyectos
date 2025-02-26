#include <stdio.h>
#include <stdlib.h>

typedef enum {DESAYUNO, COMIDA, MERIENDA, CENA, COPA} tipo;


int main(){

	char * cats[10] = { "DESAYUNOS", "COMIDAS", "MERIENDAS", "CENAS", "COPAS", "EMPATE"};	
	char tipo_leido = 0;
	float valor_leido = 0;

	float valores[5]={0};
	float cantidades[5]={0};

	// LEER VALORES
	while (scanf(" %c%f", &tipo_leido, &valor_leido) == 2 && tipo_leido != 'N') {
		
		switch(tipo_leido){
			case 'D': //Desayuno
				valores[DESAYUNO]+=valor_leido;
				cantidades[DESAYUNO]++;
				break;
			case 'A': //Comida
				valores[COMIDA]+=valor_leido;
				cantidades[COMIDA]++;
				break;
			case 'M': //Merienda
				valores[MERIENDA]+=valor_leido;
				cantidades[MERIENDA]++;
				break;
			case 'I': //Cena
				valores[CENA]+=valor_leido;
				cantidades[CENA]++;
				break;
			case 'C': //Copas
				valores[COPA]+=valor_leido;
				cantidades[COPA]++;
				break;
		}
	}

	float suma_total = 0,cantidad_total=0;
	
	int min = 0, max = 0;
	float max_val = -10000;
	float min_val = 10000;

	// CALCULAR MEDIAS
	
	for (int i = 0; i < 5; ++i)
	{
		suma_total+=valores[i];
		cantidad_total+=cantidades[i];
		
		if (cantidades[i]>0)
			valores[i] /= cantidades[i];
		
		if (valores[i]>max_val){
			max = i;
			max_val = valores[i];
		} else if (valores[i] == max_val){
			max = 5;
		}

		if (valores[i]<min_val){
			min_val = valores[i];
			min = i;
		}else if (valores[i] == min_val)
		{
			min = 5;
		}
	}

	printf("%s#%s#%s\n", 
		cats[max],
		cats[min], 
		suma_total/cantidad_total < valores[COMIDA] ? "SI" : "NO" );
	

}