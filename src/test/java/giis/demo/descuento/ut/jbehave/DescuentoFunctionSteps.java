package giis.demo.descuento.ut.jbehave;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import giis.demo.descuento.DescuentoModel;

/**
 * Define el mapping de los pasos para descuento-function.story
 */
public class DescuentoFunctionSteps {
	//puesto que un mismo test ejecutara varios pasos se deben guardar valores generados en un paso para uso en el siguiente
    private DescuentoModel model;
    private boolean nuevoCliente;
    boolean tieneCupon;
    boolean tieneTarjeta;
    
    @Given("un cliente $nuevo")
    public void tipoCliente(String nuevo) {
    	model=new DescuentoModel();
    	nuevoCliente="nuevo".equals(nuevo) ? true : false;
     }
    @When("$cupon cupon y $tarjeta tarjeta")
    public void tieneCuponTarjeta(String cupon, String tarjeta) {
        tieneCupon="tiene".equals(cupon) ? true : false;
        tieneTarjeta="tiene".equals(tarjeta) ? true : false;
    }
    @Then("el descuento es $descuento")
    public void valorDescuento(int descuento) {
    	assertEquals(descuento,model.getDescuento(nuevoCliente, tieneCupon, tieneTarjeta));
    }
    @Then("error $causa")
    public void errorDescuento(String mensaje) {
    	try {
    		model.getDescuento(nuevoCliente, tieneCupon, tieneTarjeta);
    		fail("Deberia causar excepcion");
    	} catch (RuntimeException e) {
    		assertEquals(mensaje,e.getMessage());
    	}
     }

 
}