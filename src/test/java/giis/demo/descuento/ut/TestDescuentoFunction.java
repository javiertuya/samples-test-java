package giis.demo.descuento.ut;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

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
	@Test
	public void testClientesNuevos() {
		DescuentoModel model=new DescuentoModel();
		assertEquals("caso 1",15,model.getDescuento(true, false, false));
		assertEquals("caso 2",20,model.getDescuento(true, true, false));
	}
	@Test
	public void testClientesHabituales() {
		DescuentoModel model=new DescuentoModel();
		assertEquals("caso 4",0,model.getDescuento(false, false, false));
		assertEquals("caso 5",20,model.getDescuento(false, true, false));
		assertEquals("caso 6",10,model.getDescuento(false, false, true));
		assertEquals("caso 7",30,model.getDescuento(false, true, true));
	}
	/**
	 * Prueba de la clase invalida (causa excepcion)
	 */
	@Test(expected=ApplicationException.class)
	public void testClientesNuevosNoPuedenTenerTarjeta() {
		DescuentoModel model=new DescuentoModel();
		model.getDescuento(true, false, true); //caso 4
	}
	/**
	 * La misma clase invalida comprobando la excepcion al estilo JUnit 3
	 */
	@Test
	public void testClientesNuevosNoPuedenTenerTarjetaJUnit3() {
		DescuentoModel model=new DescuentoModel();
		try {
			model.getDescuento(true, false, true); //caso 4
			fail("Se deberia producir una excepcion");
		} catch (RuntimeException e) {
			assertEquals("Un cliente nuevo no puede disponer de tarjeta de fidelizacion",e.getMessage());
		}
	}

	/**
	 * Ejemplo de uso de Hamcrest matchers (con assertThat). 
	 * Para asserts complejos proporcionan mayor flexibilidad que los assert convencionales.
	 * Nota importante: Si en eclipse se define JUnit en el build path, es posible que la ejecucion desde este entorno
	 * cause errores del tipo "signer information does not match signer information of other classes in the same package·
	 * Se debe quitar esta dependencia en eclispe para que utilice la de maven definida en pom.xml
	 */
	@Test
	public void testClientesNuevosHamcrest() {
		DescuentoModel model=new DescuentoModel();
		assertThat("caso 1",15,equalTo(model.getDescuento(true, false, false)));
		assertThat("caso 2",20,equalTo(model.getDescuento(true, true, false)));
	}

}
