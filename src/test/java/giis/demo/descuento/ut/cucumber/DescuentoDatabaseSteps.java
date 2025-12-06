package giis.demo.descuento.ut.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import giis.demo.descuento.DescuentoDisplayDTO;
import giis.demo.descuento.DescuentoModel;
import giis.demo.util.Database;
import giis.demo.util.Util;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Define el mapping (glue) de los pasos para descuento-database.feature.
 * Maneja las estructuras tabulares que representan tablas en base de datos o en la salida de la aplicacion
 */
public class DescuentoDatabaseSteps {
	private Database db;
	private int edad;

	@Before
	public void beforeEachScenario() {
		db = new Database();
		db.createDatabase(true);
		// los datos de prueba se cargaran a partir de la historia escrita en gherkin, pero antes limpio la BD
		db.executeUpdate("delete from clientes");
	}

	@Given("los clientes en base de datos:")
	public void setClientes(List<Map<String, String>> clientes) {
		String sql = "insert into clientes(id,edad,nuevo,cupon,tarjeta) values (?,?,?,?,?)";
		for (Map<String, String> row : clientes) {
			db.executeUpdate(sql, row.get("id"), row.get("edad"), row.get("nuevo"), row.get("cupon"), row.get("tarjeta"));
		}
	}

	@When("ver informe descuentos clientes de edad {word}")
	public void setEdad(String edadOrCualquiera) {
		// 0 es la edad minima a partir de la que se muestran resultados (asume edad no puede ser negativa)
		edad = "cualquiera".equals(edadOrCualquiera) ? 0 : Integer.parseInt(edadOrCualquiera);
	}

	@Then("los descuentos son:")
	public void getDescuentos(List<Map<String, String>> descuentos) {
		DescuentoModel model = new DescuentoModel();
		List<DescuentoDisplayDTO> descuentosDTO = model.getListaDescuentos(edad);
		// convierte la lista de DTOs obtenida de la BD y la lista de maps obtenida tras ejecutar cucumber
		// utilizando una utilidad para convertir a csv, indicando los separadores de Gherkin
		String actual = Util.pojosToCsv(descuentosDTO, new String[] { "id", "descuento" }, true, "|", "|", "|", "");
		String expected = Util.mapsToCsv(descuentos, new String[] { "id", "descuento" }, true, "|", "|", "|", "");
		assertEquals(expected, actual);
	}
}