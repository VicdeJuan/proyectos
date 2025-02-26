#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define DIGITOS 4	



int main(){
	
	int num,loops,digit,desc_colocado=0,asc_colocado=0,vueltas=0,i=0,j=0;
	int num_asc=0,num_desc=0;
	
	int desc[4] = {0};
	int asc[4] = {0};

	/**int * desc = (int *) calloc(4,sizeof(int));
	
	if(desc == NULL){
		return EXIT_FAILURE;
	}

	int * asc = (int *) calloc(4,sizeof(int));
	
	if(asc == NULL){
		return EXIT_FAILURE;
	}*/

	scanf("%d",&loops);

	for ( i = 0,j=0; i < loops; ++i,j=0)
	{
		scanf("%d",&num);
		vueltas = 0;
		while (num != 6174 && vueltas <8){ // Repito el proceso

			while(num != 0){
				desc_colocado=0;
				asc_colocado=0;
				
				digit = num%10;
				
				// Reviso el array descendente y coloco el dígito en su posición
				for (int k = 0; k < j; ++k)
				{
					if ( desc[k] < digit){
						memcpy(&desc[k+1],&desc[k],(j-k)*sizeof(int));
						desc[k] = digit;
						desc_colocado = 1;
						break;
					}

				}
				if (desc_colocado == 0){
					desc[j] = digit;
				}
				
				// Reviso el array ascendente y coloco el dígito en su posición
				for (int k = 0; k < j; ++k)
				{
					if ( asc[k] > digit){
						memcpy(&asc[k+1],&asc[k],(j-k)*sizeof(int));
						asc[k] = digit;
						asc_colocado = 1;
						break;
					}

				}
				if (asc_colocado == 0){
					asc[j] = digit;
				}

				j++;
				num/=10;
			}

			// Si j != 4, quiere decir que el número tiene menos de 4 cifras, así que tengo que desplazarlo hacia la derecha.
			if (j<4)
			{
				for (i = 3; i>=4-j; i--)
				{
					asc[i] = asc[i-1];
					
				}
				for (int k = 0; k <=i ; ++k)
				{
					asc[k]=0;
				}
				/*memmove(&asc[4-j],&asc[0],(j)*sizeof(int));
				memset(asc,0,(4-j)*sizeof(int));
				
				memmove(&desc[4-j],&desc[0],(4-j)*sizeof(int));
				memset(desc,0,(4-j)*sizeof(int));*/
			}
			
			num_asc=0,num_desc=0;
			for ( i = 0; i < 4; ++i){
				num_asc+=asc[i]*pow(10,4-i-1);
				num_desc+=desc[i]*pow(10,4-i-1);
			}
			printf("%d - %d = %d\n",num_desc,num_asc,num_desc-num_asc);
			

			// Reinicializo las variables que se utilizan
			num = num_desc-num_asc;
			for ( i = 0; i < 4; ++i)
			{
				asc[i]=0;
				desc[i]=0;
			}
			//memset(asc,0,4*sizeof(int));
			//memset(desc,0,4*sizeof(int));
			vueltas++;
			j=0;
		}
		printf("%d\n",vueltas);
	}

	/*
	free(desc);
	free(asc);*/
	return EXIT_SUCCESS;
}