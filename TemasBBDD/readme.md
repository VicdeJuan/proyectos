#1: Añadir el prompt en el índice
#2: Separar en archivos:
	'''{bash}
	csplit --digit=2 --prefix=askChapters/outfile indice.md "/\n/+1" "{*}"
	'''
#3: ejecutar el script dando como argumento el tema
#4: compilar con xelatex
#5: tal vez haya que sustituir
	'''
	find . -type f -exec sed -i 's/verbatim/lstlisting/' {} \;
	'''