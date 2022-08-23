Funcion de calculo de descuento de clientes (problema 3a)

!-- Ejemplo basico de definición de escenarios de una historia de prueba en el lenguaje Gherkin de JBehave
!-- https://jbehave.org/reference/stable/developing-stories.html
!-- En teoría, debería haber un solo un paso When/Then en cada escenario, pero en ocasiones puede 
!-- haber varios pasos para compactar los escenarios (como cuando se implementan varios casos de prueba en JUnit)
!-- Aquí se reproduce una estructura similar a los casos implementados con JUnit				 
Scenario:  Descuento para clientes nuevos (casos 1-3, el 3 invalido)
Given un cliente nuevo
When no tiene cupon y no tiene tarjeta
Then el descuento es 15
When tiene cupon y no tiene tarjeta
Then el descuento es 20
When no tiene cupon y tiene tarjeta
Then error Un cliente nuevo no puede disponer de tarjeta de fidelizacion

Scenario:  Descuento para clientes habituales (casos 4-7)
Given un cliente habitual
When no tiene cupon y no tiene tarjeta
Then el descuento es 0
When tiene cupon y no tiene tarjeta
Then el descuento es 20
When no tiene cupon y tiene tarjeta
Then el descuento es 10
When tiene cupon y tiene tarjeta
Then el descuento es 30

!-- Mejor solucion con todos los casos validos anteriores (similar a las pruebas JUnit parametrizadas)
!-- definiendo escenarios parametrizados (ejemplos)
!-- https://jbehave.org/reference/stable/parametrised-scenarios.html
!-- El caso inválido requerirá un escenario separado como en las anteriores
Scenario: Descuento para clientes utilizando escenarios parametrizados
Given un cliente <tipo>
When <cupon> cupon y <tarjeta> tarjeta
Then el descuento es <descuento>
Examples:
|tipo    |cupon   |tarjeta |descuento|
|nuevo   |no tiene|no tiene|15|
|nuevo   |tiene   |no tiene|20|
|habitual|no tiene|no tiene| 0|
|habitual|tiene   |no tiene|20|
|habitual|no tiene|tiene   |10|
|habitual|tiene   |tiene   |30|
