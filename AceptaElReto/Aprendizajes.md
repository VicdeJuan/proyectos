# Aprendizajes

Ojo, utilizar HashMap<Posicion,valor> requiere 2 cosas:
- Sobreescribir equals de la clase Posición
- Sobreescribir hashCode de la clase Posición.

HashMap accede primero con el hashcode y, si hay empate, tira de equals. Por eso hay que sobreescribir los 2
