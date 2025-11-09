package giis.demo.descuento.ut.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import giis.demo.descuento.DescuentoModel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Define el mapping (glue) entre Gherkin y java para descuento-function.feature
 */
public class DescuentoFunctionSteps {
	private DescuentoModel model;
	private boolean nuevoCliente;
	boolean tieneCupon;
	boolean tieneTarjeta;

	@Given("un cliente {word}")
	public void tipoCliente(String nuevo) {
		model = new DescuentoModel();
		nuevoCliente = "nuevo".equals(nuevo);
	}

	@When("{word} cupon y {word} tarjeta")
	public void tieneCuponTarjeta(String cupon, String tarjeta) {
		tieneCupon = "con".equals(cupon);
		tieneTarjeta = "con".equals(tarjeta);
	}

	@Then("el descuento es {int}")
	public void valorDescuento(int descuento) {
		assertEquals(descuento, model.getDescuento(nuevoCliente, tieneCupon, tieneTarjeta));
	}

	@Then("error {string}")
	public void errorDescuento(String mensaje) {
		try {
			model.getDescuento(nuevoCliente, tieneCupon, tieneTarjeta);
			fail("Deberia causar excepcion");
		} catch (RuntimeException e) {
			assertEquals(mensaje, e.getMessage());
		}
	}

}