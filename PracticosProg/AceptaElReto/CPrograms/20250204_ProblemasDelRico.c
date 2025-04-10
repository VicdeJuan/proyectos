#include <stdio.h>
#include <stdlib.h>

int main(){
	
	int num_options,num_compartimentos, litros_grande, diferencia_litros,volumen;

	scanf("%d",&num_options);
	for (int i = 0; i < num_options; ++i)
	{
		scanf("%d%d%d",&num_compartimentos,&litros_grande,&diferencia_litros);
		printf("%d\n", (int)(num_compartimentos*1.0/2*(2*litros_grande - diferencia_litros*(num_compartimentos-1))));
	}
}