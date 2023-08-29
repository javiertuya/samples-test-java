#!/bin/bash
# Realiza una copia parcial del proyecto samples-test-java para producir 
# una version simplificada (samples-test-dev) para uso en las practicas de IS
# El proyecto samples-test-dev debe estar en al mismo nivel que samples-test-java

# se ejecuta desde la carpeta donde reside este script
# usara otro script (file-filter.sh) para eliminar partes del codigo marcadas
SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")
echo $SCRIPTPATH
cd $SCRIPTPATH

SRC=".."
DEST="../../samples-test-dev"

echo "copy src to dest"
cp -vR $SRC/src $DEST/
cp -v $SRC/pom.xml $DEST/pom.xml
#cp -v $SRC/.classpath $DEST/
cp -v $SRC/.project $DEST/
cp -v $SRC/.gitignore $DEST/

echo "delete descuento & it source folders"
rm -R $DEST/src/main/java/giis/demo/descuento
rm -R $DEST/src/test/java/giis/demo/descuento
rm -R $DEST/src/test/resources/test-parameters.csv
rm -R $DEST/src/it

echo "touch additional files"
sed -i "s/samples-test-java/samples-test-dev/g" $DEST/src/main/java/overview.html
sed -i "s/samples-test-java/samples-test-dev/g" $DEST/pom.xml
sed -i "s/samples-test-java/samples-test-dev/g" $DEST/.project
if [[ "$OSTYPE" == "msys" ]]; then # preserve CRLF when running from vs studio terminal in windows
  unix2dos $DEST/src/main/java/overview.html
  unix2dos $DEST/pom.xml
  unix2dos $DEST/.project
fi

echo "filter files"
chmod u+x ./file-filter.sh
./file-filter.sh true  $DEST/pom.xml "BEGINREDUCE" "ENDREDUCE"
./file-filter.sh true  $DEST/src/main/resources/data.sql "BEGINREDUCE" "ENDREDUCE"
./file-filter.sh true  $DEST/src/main/resources/schema.sql "BEGINREDUCE" "ENDREDUCE"
./file-filter.sh true  $DEST/src/main/java/giis/demo/util/SwingMain.java "BEGINREDUCE" "ENDREDUCE"
