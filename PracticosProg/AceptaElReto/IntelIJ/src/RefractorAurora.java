import java.util.*;

class RelationshipMatrix {
    private int[][] values;

    // Constructor a partir de una matriz ya creada.
    public RelationshipMatrix(int[][] values) {
        this.values = values;
    }

    // Constructor para una matriz de tamaño n x n, inicializada a 0.
    public RelationshipMatrix(int size) {
        values = new int[size][size];
        // Los valores en Java se inicializan a 0 por defecto.
    }

    // Establece el valor en la posición (i, j)
    public void set(int i, int j, int value) {
        values[i][j] = value;
    }

    // Establece de forma simétrica el valor en (i, j) y (j, i)
    public void setSymmetric(int i, int j, int value) {
        values[i][j] = value;
        values[j][i] = value;
    }

    // Devuelve la cantidad de filas de la matriz
    public int getRows() {
        return values.length;
    }

    // Devuelve la cantidad de columnas de la matriz
    public int getColumns() {
        return (values.length == 0 ? 0 : values[0].length);
    }

    // Obtiene el valor almacenado en (i, j)
    public int getValue(int i, int j) {
        return values[i][j];
    }

    // Retorna la matriz transpuesta
    public RelationshipMatrix transpose() {
        int rows = getRows();
        int cols = getColumns();
        int[][] transposedValues = new int[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposedValues[i][j] = values[j][i];
            }
        }
        return new RelationshipMatrix(transposedValues);
    }

    // Suma dos matrices y devuelve el resultado (si tienen las mismas dimensiones)
    public RelationshipMatrix add(RelationshipMatrix other) {
        if (this.getRows() != other.getRows() || this.getColumns() != other.getColumns()) {
            return null;
        }
        int rows = getRows();
        int cols = getColumns();
        int[][] sumValues = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sumValues[i][j] = this.getValue(i, j) + other.getValue(i, j);
            }
        }
        return new RelationshipMatrix(sumValues);
    }

    // Retorna una matriz con todos los valores negados
    public RelationshipMatrix negate() {
        int rows = getRows();
        int cols = getColumns();
        int[][] negatedValues = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                negatedValues[i][j] = -values[i][j];
            }
        }
        return new RelationshipMatrix(negatedValues);
    }

    // Obtiene el conjunto de índices de enemigos de una persona dada.
    public Set<Integer> getEnemies(int person) {
        Set<Integer> enemies = new LinkedHashSet<>();
        int n = getRows();
        for (int i = 0; i < n; i++) {
            if (getValue(person, i) == RefractorAurora.ENEMY) {
                enemies.add(i);
            }
        }
        return enemies;
    }

    // Obtiene recursivamente el conjunto de amigos (y, a través de la anti-transitividad, de enemigos) de una persona.
    public Set<Integer> getFriends(int person) {
        Set<Integer> friends = new LinkedHashSet<>();
        boolean[] visited = new boolean[getRows()];
        Arrays.fill(visited, false);
        addFriendsRecursive(person, friends, visited);
        return friends;
    }

    // Método recursivo que agrega a los amigos (y sus derivados) en el grupo.
    private void addFriendsRecursive(int person, Set<Integer> group, boolean[] visited) {
        int n = getRows();
        for (int i = 0; i < n; i++) {
            if (getValue(person, i) == RefractorAurora.FRIEND) {
                group.add(i);
                if (!visited[i]) {
                    visited[i] = true;
                    addFriendsRecursive(i, group, visited);
                }
            } else if (getValue(person, i) == RefractorAurora.ENEMY) {
                addEnemiesRecursive(i, group, visited);
            }
        }
    }

    // Método recursivo que agrega a los enemigos (y sus amigos) en el grupo.
    private void addEnemiesRecursive(int person, Set<Integer> group, boolean[] visited) {
        int n = getRows();
        for (int i = 0; i < n; i++) {
            if (getValue(person, i) == RefractorAurora.ENEMY) {
                group.add(i);
                if (!visited[i]) {
                    visited[i] = true;
                    addFriendsRecursive(i, group, visited);
                }
            }
        }
    }

    // Verifica si todos los elementos del grupo son compatibles entre sí (es decir, ninguno es enemigo de otro).
    public boolean isCompatible(Set<Integer> group) {
        for (Integer personA : group) {
            for (Integer personB : group) {
                if (getValue(personA, personB) == RefractorAurora.ENEMY) {
                    return false;
                }
            }
        }
        return true;
    }
}

public class RefractorAurora {
    public static final int ENEMY = -1;
    public static final int FRIEND = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] header = scanner.nextLine().split(" ");
        int numPersons = Integer.parseInt(header[0]);
        int numFights = Integer.parseInt(header[1]);
        int capacity = Integer.parseInt(header[2]);

        // Caso especial: si no hubo peleas, se puede invitar a todos (limitado por la capacidad).
        if (numFights == 0) {
            System.out.println(Math.min(numPersons, capacity));
            return;
        }

        RelationshipMatrix relationships = new RelationshipMatrix(numPersons);

        // Procesar peleas: se marca cada enfrentamiento de forma simétrica.
        for (int i = 0; i < numFights; i++) {
            header = scanner.nextLine().split(" ");
            int person1 = Integer.parseInt(header[0]) - 1;
            int person2 = Integer.parseInt(header[1]) - 1;
            if (person1 < numPersons && person2 < numPersons) {
                relationships.setSymmetric(person1, person2, ENEMY);
            } else {
                System.out.println("ERROR: Índice de persona fuera de rango.");
            }
        }

        // Reflexividad de la amistad: cada persona es amiga de sí misma.
        for (int i = 0; i < numPersons; i++) {
            relationships.set(i, i, FRIEND);
        }

        // Se construyen los grupos de amigos de cada persona.
        Set<Set<Integer>> friendGroups = new HashSet<>();
        for (int i = 0; i < numPersons; i++) {
            Set<Integer> friends = relationships.getFriends(i);
            friendGroups.add(friends);
        }

        // Se obtiene el grupo compatible máximo que cumpla con la restricción de capacidad.
        Set<Integer> maxCompatibleGroup = getMaxCompatibleSubset(friendGroups, relationships, capacity);
        System.out.println(maxCompatibleGroup.size());

        scanner.close();
    }

    /**
     * Intenta combinar grupos de amigos para obtener el subconjunto compatible más grande
     * que no supere la capacidad permitida.
     *
     * @param groups     Conjunto de grupos (cada grupo es un conjunto de índices de personas).
     * @param relationships  La matriz de relaciones.
     * @param capacity   Aforo máximo permitido.
     * @return           El mayor grupo compatible (en tamaño) que cumpla con la capacidad.
     */
    public static Set<Integer> getMaxCompatibleSubset(Set<Set<Integer>> groups, RelationshipMatrix relationships, int capacity) {
        // Caso base: si solo hay un grupo, lo retornamos.
        if (groups.size() == 1) {
            return groups.iterator().next();
        }

        // Se busca el grupo de mayor tamaño que no exceda la capacidad.
        Set<Integer> bestGroup = new HashSet<>();
        for (Set<Integer> group : groups) {
            if (group.size() > bestGroup.size() && group.size() <= capacity) {
                bestGroup = group;
            }
        }

        // Se intenta combinar de dos en dos los grupos.
        Set<Set<Integer>> newGroups = new HashSet<>();
        List<Set<Integer>> groupList = new ArrayList<>(groups);
        int n = groupList.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Set<Integer> combinedGroup = new HashSet<>(groupList.get(i));
                combinedGroup.addAll(groupList.get(j));
                if (combinedGroup.size() > bestGroup.size() &&
                        combinedGroup.size() <= capacity &&
                        relationships.isCompatible(combinedGroup)) {
                    newGroups.add(combinedGroup);
                    if (combinedGroup.size() > bestGroup.size()) {
                        bestGroup = combinedGroup;
                    }
                }
            }
        }

        // Si se han logrado combinar grupos, se intenta combinar aún más
        if (!newGroups.isEmpty()) {
            Set<Set<Integer>> combinedGroups = new HashSet<>(groups);
            combinedGroups.addAll(newGroups);
            Set<Integer> furtherBest = getMaxCompatibleSubset(combinedGroups, relationships, capacity);
            if (furtherBest.size() > bestGroup.size() && furtherBest.size() <= capacity) {
                bestGroup = furtherBest;
            }
        }

        return bestGroup;
    }
}
