#!/bin/bash
SRC=".."
DEST="../../samples-test-dev"
echo "copy src to dest"
cp -vR $SRC/src $DEST/src/
cp -v $SRC/pom.xml $DEST/pom.xml
cp -v $SRC/.classpath $DEST/
cp -v $SRC/.project $DEST/
cp -v $SRC/.gitignore $DEST/

echo "delete descuento source folders"
rm -vR $DEST/src/main/java/giis/demo/descuento
rm -vR $DEST/src/test/java/giis/demo/descuento
rm -vR $DEST/src/it/java/giis/demo/descuento
rm -vR $DEST/src/it/java/giis/demo

echo "rename project"
#sed -i "s/samples-test-java/samples-test-dev/g" $DEST/index.html
sed -i "s/samples-test-java/samples-test-dev/g" $DEST/pom.xml
sed -i "s/samples-test-java/samples-test-dev/g" $DEST/.project
#sed -i "s/samples-test-java/samples-test-dev/g" $DEST/sonar-project.properties

echo "filter marks"
chmod u+x ./app-filter.sh
./file-filter.sh true  $DEST/pom.xml "BEGINDESCUENTO" "ENDDESCUENTO"
./file-filter.sh true  $DEST/src/main/resources/data.sql "BEGINDESCUENTO" "ENDDESCUENTO"
./file-filter.sh true  $DEST/src/main/resources/schema.sql "BEGINDESCUENTO" "ENDDESCUENTO"
./file-filter.sh true  $DEST/src/main/java/giis/demo/util/SwingMain.java "BEGINDESCUENTO" "ENDDESCUENTO"
./file-filter.sh true  $DEST/src/main/java/overview.html "BEGINDESCUENTO" "ENDDESCUENTO"
./file-filter.sh false $DEST/src/main/java/overview.html "BEGINJDBC" "ENDJDBC"
./file-filter.sh true  $DEST/src/main/java/overview.html "BEGINIT" "ENDIT"
