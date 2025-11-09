package giis.demo.descuento.it.cucumber;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.assertj.swing.fixture.FrameFixture;

import giis.demo.util.AssertjUtil;
import giis.demo.util.Database;
import giis.demo.util.Util;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ITDescuentoSteps {
	private Database db;
	private FrameFixture window;

	@Before
	public void beforeEachScenario() {
		db = new Database();
		db.createDatabase(true);
		db.executeUpdate("delete from clientes");
	}

	@After
	public void afterEachScenario() {
		AssertjUtil.takeScreenshot(window, "Cucumber-final");
		window.cleanUp();
	}

	@Given("los clientes en base de datos:")
	public void setClientes(List<Map<String, String>> clientes) {
		String sql = "insert into clientes(id,edad,nuevo,cupon,tarjeta) values (?,?,?,?,?)";
		for (Map<String, String> row : clientes) {
			db.executeUpdate(sql, row.get("id"), row.get("edad"), row.get("nuevo"), row.get("cupon"), row.get("tarjeta"));
		}
	}

	@When("se inicia la ventana")
	public void setApplication() {
		window = AssertjUtil.getApplicationFixture("Ejecutar giis.demo.descuento", "Descuento");
		AssertjUtil.takeScreenshot(window, "Cucumber-inicial");
	}

	@When("se cambia la edad a {word}")
	public void setEdad(String edad) {
		window.textBox("txtAnyos").setText(edad);
		window.button("btnAplicarFiltro").click();
		AssertjUtil.takeScreenshot(window, "Cucumber-cambio-edad-" + edad);
	}

	@When("se elimina la edad")
	public void setEdad() {
		window.textBox("txtAnyos").setText("");
		window.button("btnAplicarFiltro").click();
		AssertjUtil.takeScreenshot(window, "Cucumber-elimina-edad");
	}

	@Then("los descuentos visualizados son:")
	public void getDescuentos(List<Map<String, String>> descuentos) {
		String actual = Util.arraysToCsv(window.table("tabDescuentos").contents(), new String[] { "id", "descuento" }, "|", "|", "|");
		String expected = Util.mapsToCsv(descuentos, new String[] { "id", "descuento" }, true, "|", "|", "|", "");
		assertEquals(expected, actual);
	}
}