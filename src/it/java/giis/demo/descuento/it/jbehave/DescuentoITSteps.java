package giis.demo.descuento.it.jbehave;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.assertj.swing.fixture.FrameFixture;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import giis.demo.util.AssertjUtil;
import giis.demo.util.Database;
import giis.demo.util.Util;

/**
 * Define el mapping de los pasos para descuento-function.story
 */
public class DescuentoITSteps {
	//puesto que un mismo test ejecutara varios pasos se deben guardar valores generados en un paso para uso en el siguiente
    private Database db;
    private FrameFixture window;
    
    @BeforeScenario
    public void beforeEachScenario() {
    	db=new Database();
		db.createDatabase(true);
		db.executeUpdate("delete from clientes");
    }
    @AfterScenario
    public void afterEachScenario() {
		AssertjUtil.takeScreenshot(window,"JBehave-final");	
		window.cleanUp();
    }
    @Given("los siguientes clientes en base de datos: $clientesbd")
    public void setClientes(ExamplesTable clientes) {
        String sql="insert into clientes(id,edad,nuevo,cupon,tarjeta) values (?,?,?,?,?)";
        for (Map<String,String> row : clientes.getRows()) {
            db.executeUpdate(sql, row.get("id"), row.get("edad"), row.get("nuevo"), row.get("cupon"), row.get("tarjeta"));
        }
    }
    @When("Se inicia la ventana")
    public void setApplication() {
		window=AssertjUtil.getApplicationFixture("Ejecutar giis.demo.descuento", "Descuento");
		AssertjUtil.takeScreenshot(window,"JBehave-inicial");	
    }
    @When("se cambia la edad a $edad")
    public void setEdad(String edad) {
		window.textBox("txtAnyos").setText(edad);
		window.button("btnAplicarFiltro").click();
		AssertjUtil.takeScreenshot(window,"JBehave-cambio-edad-"+edad);	
    }
    @When("se elimina la edad")
    public void setEdad() {
		window.textBox("txtAnyos").setText("");
		window.button("btnAplicarFiltro").click();
		AssertjUtil.takeScreenshot(window,"JBehave-elimina-edad");	
   }
    @Then("los descuentos visualizados son: $descuentos")
    public void getDescuentos(ExamplesTable descuentos) {
    	String expected=descuentos.asString();
		String actual=Util.arraysToCsv(window.table("tabDescuentos").contents(),new String[] {"id","descuento"},"|","|","|");
    	assertEquals(expected,actual);
     }
}