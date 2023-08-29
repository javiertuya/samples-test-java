#!/bin/bash
#crea un nuevo fichero $filename con el mismo nombre excluyendo bloques de lineas entre $begin y $end
#Si $removeblock="false" solo excluye las lineas que contienen $begin y $end
# ejemplo: ./app-filter.sh false pom.xml "BEGINDESCUENTO" "ENDDESCUENTO"
removeblock=$1
filename=$2
begin=$3
end=$4
#escribira en app-filter.tmp y luego sobrescribira filename con su contenido
rm app-filter.tmp 2> /dev/null
inside=false
echo "-------------------- $filename" --------------------
while IFS="" read -r line 
do 
    delete=false
    #determina el estado
    if [[ $line == *"$begin"* ]]; then
        delete=true
        inside=true
    elif [[ $line == *"$end"* ]]; then
        delete=true
        inside=false
    fi
    #los begin y end ya estan marcados para borrar, 
    #si se indico removeblock y esta inside tambien se borra la linea
    if [[ "$inside" = true && "$removeblock" = true ]]; then
        delete=true
    fi    
    #escribe linea o la borra segun el estado
    if [ "$delete" = true ]; then
        echo "Filter line: $line"
    else
        echo "$line" >> app-filter.tmp
    fi
done < $filename
#cat app-filter.tmp
cp app-filter.tmp $filename
if [[ "$OSTYPE" == "msys" ]]; then # preserve CRLF when running from vs studio terminal in windows
  unix2dos $filename
fi
rm app-filter.tmp