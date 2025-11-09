# Ejemplo basico de definición de escenarios de una historia de prueba en el lenguaje Gherkin de Cucumber

Feature: Funcion de calculo de descuento de clientes (problema 3a)

  # En teoría, debería haber un solo un paso When/Then en cada escenario, pero en ocasiones puede 
  # haber varios pasos para compactar los escenarios (como cuando se implementan varios casos de prueba en JUnit)
  # Aquí se reproduce una estructura similar a los casos implementados con JUnit				 

  Scenario:  Descuento para clientes nuevos (casos 1-3, el 3 invalido)
    Given un cliente nuevo
    When sin cupon y sin tarjeta
    Then el descuento es 15
    When con cupon y sin tarjeta
    Then el descuento es 20
    When sin cupon y con tarjeta
    Then error "Un cliente nuevo no puede disponer de tarjeta de fidelizacion"

  Scenario:  Descuento para clientes habituales (casos 4-7)
    Given un cliente habitual
    When sin cupon y sin tarjeta
    Then el descuento es 0
    When con cupon y sin tarjeta
    Then el descuento es 20
    When sin cupon y con tarjeta
    Then el descuento es 10
    When con cupon y con tarjeta
    Then el descuento es 30

  # Mejor solucion con todos los casos validos anteriores (similar a las pruebas JUnit parametrizadas)
  # definiendo escenarios parametrizados (Examples)
  # El caso invalido requerira un escenario separado como en las anteriores
  Scenario: Descuento para clientes utilizando escenarios parametrizados
    Given un cliente <tipo>
    When <cupon> cupon y <tarjeta> tarjeta
    Then el descuento es <descuento>
    Examples:
      |tipo    |cupon  |tarjeta|descuento|
      |nuevo   |sin    |sin    |15|
      |nuevo   |con    |sin    |20|
      |habitual|sin    |sin    | 0|
      |habitual|con    |sin    |20|
      |habitual|sin    |con    |10|
      |habitual|con    |con    |30|
    