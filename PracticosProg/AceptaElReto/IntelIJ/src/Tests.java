import java.util.ArrayList;
import java.util.List;

    public class Tests {

        public static void main(String[] args) {
            Integer a = 2;

            Integer b = a;

            if (a==b){
                System.out.println("Equals");
            }

        }
        public static void not_main(String[] args) {
            // Array de dígitos del 0 al 9
            int[] digits = new int[10];
            for (int i = 0; i < 10; i++) {
                digits[i] = i;
            }

            // Lista para almacenar las combinaciones
            List<int[]> combinations = new ArrayList<>();

            // Array auxiliar para la combinación actual (longitud 9)
            int[] currentCombination = new int[10];
            // Array para llevar el control de los dígitos ya usados
            boolean[] used = new boolean[10];

            // Generar todas las combinaciones
            generateCombinations(digits, used, currentCombination, 0, combinations);

            // Mostrar el número total de combinaciones generadas
            System.out.println("Total de combinaciones generadas: " + combinations.size());

            // Mostrar las primeras 10 combinaciones para verificar
            for (int i = 0; i <  combinations.size(); i++) {
                printArray(combinations.get(i));
            }
        }

        /**
         * Método recursivo para generar las combinaciones.
         * @param digits Array con los dígitos disponibles (0 a 9)
         * @param used Arreglo booleano para marcar los dígitos que ya se han utilizado en la combinación actual
         * @param currentCombination Array que almacena la combinación actual
         * @param index Posición actual en la combinación
         * @param combinations Lista donde se almacenan todas las combinaciones generadas
         */
        private static void generateCombinations(int[] digits, boolean[] used, int[] currentCombination,
                                                 int index, List<int[]> combinations) {
            if (index == currentCombination.length) {
                // Se ha completado una combinación de 9 dígitos: se agrega una copia del array actual
                combinations.add(currentCombination.clone());
                return;
            }

            // Probar cada dígito disponible que no haya sido usado
            for (int i = 0; i < digits.length; i++) {
                if (!used[i]) {
                    used[i] = true; // Marcar el dígito como usado
                    currentCombination[index] = digits[i];

                    // Llamada recursiva para la siguiente posición
                    generateCombinations(digits, used, currentCombination, index + 1, combinations);

                    // Desmarcar el dígito para poder usarlo en otra combinación
                    used[i] = false;
                }
            }
        }

        /**
         * Método auxiliar para imprimir un array de enteros.
         * @param arr Array a imprimir
         */
        private static void printArray(int[] arr) {
            for (int num : arr) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }


