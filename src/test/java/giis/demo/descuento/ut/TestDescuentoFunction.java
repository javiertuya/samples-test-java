package giis.demo.descuento.ut;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import giis.demo.descuento.DescuentoModel;
import giis.demo.util.ApplicationException;

/**
 * Pruebas del ejemplo de descuentos de clientes (Problema 3a).
 * Situaciones a cubrir:
<pre>
Entradas (Combinación parcial tipo cliente y cupones/fidelizacion)
  Nuevo Cliente
    Sin cupón descuento ni tarjeta fideliz.
    Con cupón descuento
    Con tarjeta fidelización (inválida)
  Cliente existente
    Sin cupón descuento ni tarjeta fideliz.
    Con cupón descuento
    Con tarjeta de fidelizacion
Salidas
  Acumulacion de descuentos
    Se acumulan
    No se acumulan
  Descuento aplicado (%)
    15; 10; 20; 0
Los casos de prueba se implementan cada uno en un assert y se organizan en diferentes metodos de prueba
-para clientes nuevos
-para clientes habituales
-para las clases invalidas
</pre>
 */
public class TestDescuentoFunction {
	
	/**
	 * Primera version, agrupando en dos metodos de prueba los tests validos para clientes nuevos y habituales.
	 * En JUnit4 los mensajes opcionales de los assert se indicaban como primer parametro.
	 */
	@Test
	public void testClientesNuevos() {
		DescuentoModel model = new DescuentoModel();
		assertEquals(15, model.getDescuento(true, false, false), "caso 1");
		assertEquals(20, model.getDescuento(true, true, false), "caso 2");
	}

	@Test
	public void testClientesHabituales() {
		DescuentoModel model = new DescuentoModel();
		assertEquals(0, model.getDescuento(false, false, false), "caso 4");
		assertEquals(20, model.getDescuento(false, true, false), "caso 5");
		assertEquals(10, model.getDescuento(false, false, true), "caso 6");
		assertEquals(30, model.getDescuento(false, true, true), "caso 7");
	}

	/**
	 * En los anteriores, si falla un assert, no se ejecutan los siguientes.
	 * Desde JUnit5 existe la posibilidad de ejecutar todos los asserts, evitando este problema.
	 * A modo de ejemplo se muestra para los clientes nuevos, 
	 * aunque en este caso, seria mejor usar pruebas parametrizadas (ejemplo incluido en otra clase)
	 */
	@Test
	public void testClientesNuevosGroupAssertion() {
		DescuentoModel model = new DescuentoModel();
		assertAll("Casos 1 y 2",
			() -> assertEquals(15, model.getDescuento(true, false, false), "caso 1"),
			() -> assertEquals(20, model.getDescuento(true, true, false), "caso 2")
		);
	}

	/**
	 * Prueba de la clase invalida (causa excepcion)
	 */
	@Test
	public void testClientesNuevosNoPuedenTenerTarjeta() {
		DescuentoModel model = new DescuentoModel();
		ApplicationException exception=assertThrows(ApplicationException.class, () -> {
			model.getDescuento(true, false, true); // caso 4
		});
		assertEquals("Un cliente nuevo no puede disponer de tarjeta de fidelizacion", exception.getMessage());
	}

	/**
	 * Ejemplo de uso de Hamcrest matchers (con assertThat). 
	 * Para asserts complejos proporcionan mayor flexibilidad que los assert convencionales.
	 * Notar que el orden de los parametros no es el mismo que el de los asserts de junit5.
	 * En JUnit4 venian incluidos con la dependencia, pero a partir de JUnit5 no,
	 * por lo que debe incluirse explicitamente al dependencia org.hamcrest:hamcrest.
	 */
	@Test
	public void testClientesNuevosHamcrest() {
		DescuentoModel model = new DescuentoModel();
		assertThat("caso 1", model.getDescuento(true, false, false) , equalTo(15));
		assertThat("caso 2", model.getDescuento(true, true, false), equalTo(20));
	}

}
