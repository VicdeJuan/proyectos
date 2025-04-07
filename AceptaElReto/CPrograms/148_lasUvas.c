#include <stdio.h>
#include <stdlib.h>

int main(){
	short int horas,minutos;

	while(1){
		scanf("%hd:%hd",&horas,&minutos);
		if (horas == 0 && minutos == 0)
			break;
		printf("%hd\n",(23-horas)*60+(60-minutos));
	}


}
