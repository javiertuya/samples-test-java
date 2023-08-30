[![Build Status](https://github.com/javiertuya/samples-test-java/actions/workflows/test.yml/badge.svg)](https://github.com/javiertuya/samples-test-java/actions/workflows/test.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=my%3Asamples-test-java&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=my%3Asamples-test-java)
[![Javadoc](https://img.shields.io/badge/%20-javadoc-blue)](https://javiertuya.github.io/samples-test-java/)

# samples-test-java

Este proyecto es utilizado a modo de ejemplo para ilustrar algunos aspectos del desarrollo y automatización de pruebas para
las asignaturas relacionadas con ingenieria del software, sistemas de información y pruebas de software.

[Descargar la última versión](https://github.com/javiertuya/samples-test-java/releases) - 
[Ver más detalles en el javadoc](https://javiertuya.github.io/samples-test-java/)

## Contenido

Permite ilustrar, entre otros:
- Repaso del uso de JDBC para acceder a bases de datos
- Un conjunto de utilidades para simplificar el acceso a base de datos y el uso de tablas en Swing
- Implementación de MVC con Swing
- Automatización de pruebas unitarias con varias versiones de JUnit
- Automatización de pruebas de un interfaz de usuario AssertJ Swing
- Automatización de pruebas BDD con JBehave (unitarias y de interfaz de usuario)
- Estructura y configuración de un proyecto Maven 
  con separacion de pruebas unitarias (UT) y de integración (IT) y diferentes reports

Contiene los siguientes paquetes principales:
- `giis.demo.jdbc`: Repaso de acceso a base de datos con jdbc
- `giis.demo.tkrun`: Ilustra estructura de proyecto MVC con Swing (TicketRun)
- `giis.demo.tkrun.ut`: Ilustra pruebas con JUnit para TicketRun
- `giis.demo.tkrun.it`: Ilustra pruebas con AssertJ Swing para TicketRun
- `giis.demo.descuento.*`: Ilustra lo anterior para Descuento, más pruebas BDD con JBehave
- `giis.demo.util`: Diferentes utilidades de uso por parte de los anteriores

La estructura es la estándar de maven:
- `src/main/java`: Codigo fuente de aplicación
- `src/test/java`: Pruebas unitarias
- `src/it/java`: Pruebas de integración con el interfaz de usuario
- `target`: Generado con el codigo objeto y reports

## Requisitos e Instalación

- [Descargar la última versión](https://github.com/javiertuya/samples-test-java/releases) 
  y disponer al menos de Java 8 JDK

- Desde línea de comandos con [Apache Maven](https://maven.apache.org/download.cgi):
  - Asegurarse de que JAVA_HOME apunta a un JDK y no JRE
  - Ejecución completa: `mvn install`, incluye generación del Javadoc
  - Solo pruebas unitarias: `mvn test`, todas las pruebas: `mvn verify`
  - Ejecución sin tests: `mvn install -DskipTests=true`, genera todos los jar incluyendo javadoc

- Desde Eclipse con M2Eclipse instalado (las distribuciones recientes ya lo incluyen).
  Desde la raiz del proyecto:
  - Asegurarse de que esta configurado JDK: Desde build path, editar JRE System Library y en Environment
	comprobar que JavaSE-1.8 apunta a un JDK en vez de un JRE
  - *Maven->Update Project*: Actualiza todas las dependencias y permite usar el proyecto como si hubiera sido creado desde el propio Eclipse
  - *Run As->Maven install*: Ejecuta este (o otros) comandos maven desde Eclipse

Programa principal (aplicaciones swing): `giis.demo.util.SwingMain`

## Reports

La instalacion anterior compilará, ejecutará pruebas y dispondrá de los reports en carpetas dentro de `target`:
- `site/testapidocs/index.html`: javadoc del proyecto
- `site/surefire-report.html`: report de las pruebas unitarias (ut)
- `site/failsafe-report.html`: report de las pruebas del interfaz de usuario (it)
- `site/junit*`: report consolidado de todas las pruebas con el formato que genera junit
- `site/jacoco*`: reports de cobertura de código consolidado, y separado para ut e it
- `jbehave`: reports estandar de jbehave 
