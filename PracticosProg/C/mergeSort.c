#include <stdio.h>
#include <stdlib.h>
#include <string.h>


/**
 *  Ejercicio resuelto a papel con los siguientes errores cometidos:
 *      - No se puede hacer aritmética de punteros con void. Hay que convertir a char
 *          (que ocupan un byte) para poder hacer la aritmética
 *      - He querido ahorrarme una comparación extra (elementos disponibles) a ver 
 *          si funcionaba, pero no. Así que tuve que ponerla.
 *      - Me olvidé de incrementar el puntero auxiliar de la fusión tras la inserción
 *          y, una vez corregido eso, me olvidé de guardar el valor inicial de ese
 *          puntero para la posterior actualización de arr. 
 * 
 **/

void mergesort( void * array, int num_elem, int size_elem, int (*comp) (void*,void*),int level);

void printArrOfInt( int * arr, int n );

int compareInt( void * , void * );

int main() {

    int num_elem;

    printf("Introduzca el número de enteros:");
    scanf("%d", &num_elem);

    int * array = (int *) calloc(num_elem,sizeof(int));

    printf("Introduzca los elementos:");

    for (int i=0; i < num_elem ; i++)
        scanf("%d", array + i);

    printArrOfInt( array, num_elem );
    printf("\n");

    printf("Ordenado ... \n");

    mergesort( array, num_elem, sizeof(int), compareInt,0);

    printf("Ordenado:\n");
    printArrOfInt( array, num_elem );
    printf("\n");

    free(array);

}

int compareInt ( void * a , void * b) {
    return (*(int *)a) - (*(int *)b);
}

void printArrOfInt( int * arr, int n ) {
    for (int i=0; i < n ; i++)
        printf("%d ", arr[i]);
    
}

void mergesort (void * arr, int n, int size, int (*compare)(void *, void *),int level) {
    

    if (n == 1)
        return;
    
    printf("Nvl: %d - Recibido (n,size,[arr]): (%d,%d,[",level,n,size);
    printArrOfInt((int*)arr,n);
    printf("])\n");
    
    int elems_izq = n/2;
    int elems_dcha = n-n/2;

    mergesort( arr, elems_izq, size, compare ,level+1); // ordeno izq
    mergesort( ((char *) arr) + elems_izq * size,elems_dcha , size, compare ,level+1); // ordeno dcha

    printf("Nvl: %d - PostMergeSort (n,size,[arr]): (%d,%d,[",level,n,size);
    printArrOfInt((int*)arr,n);
    printf("])\n");

    void * first = arr;
    void * second = ((char *)arr) + n/2*size;

    void * ret = malloc (n*size);
    void * old_ret = ret;

    int offset = size;
    for (int i=0; i < n ; i++) { // Fusionar en ret
        if ((elems_izq > 0 && elems_dcha == 0) || (elems_izq > 0 && compare(first, second) < 0)) {
            memcpy(ret, first, offset );
            first = ((char *)first) + offset; // avanzar
            ret = ((char *)ret) + offset; // avanzar
            elems_izq--;
        } else {
            memcpy( ret, second, offset );
            second = ((char *) second) + offset;
            ret = ((char *)ret) + offset; // avanzar
            elems_dcha--;
        } 
    }


    memcpy( arr, old_ret, n*size );
    free( old_ret );


    printf("Nvl: %d - PostFusion(n,size,[arr]): (%d,%d,[",level,n,size);
    printArrOfInt((int*)arr,n);
    printf("])\n");

    return;
}
