package giis.demo.descuento.ut.jbehave;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import giis.demo.descuento.DescuentoDisplayDTO;
import giis.demo.descuento.DescuentoModel;
import giis.demo.util.Database;
import giis.demo.util.Util;

/**
 * Define el mapping de los pasos para descuento-database.story.
 * Maneja las estructuras tabulares que representan tablas en base de datos o en la salida de la aplicacion
 * representadas con variables de tipo ExamplesTable 
 * (https://jbehave.org/reference/stable/tabular-parameters.html,
 * https://jbehave.org/reference/stable/javadoc/core/org/jbehave/core/model/ExamplesTable.html).
 * Las comparaciones de tablas se realizan comparando la representacion de estas tablas como strings.
 */
public class DescuentoDatabaseSteps {
	//puesto que un mismo test ejecutara varios pasos se deben guardar valores generados en un paso para uso en el siguiente
    private Database db;
    private int edad;
    
    @BeforeScenario
    public void beforeEachScenario() {
    	db=new Database();
		db.createDatabase(true);
		//los datos de prueba se cargaran a partir de la historia escrita en gherkin, pero antes limpio la BD
		db.executeUpdate("delete from clientes");
    }
    @Given("los clientes en base de datos: $clientesbd")
    public void setClientes(ExamplesTable clientes) {
        String sql="insert into clientes(id,edad,nuevo,cupon,tarjeta) values (?,?,?,?,?)";
        for (Map<String,String> row : clientes.getRows()) {
            db.executeUpdate(sql, row.get("id"), row.get("edad"), row.get("nuevo"), row.get("cupon"), row.get("tarjeta"));
        }
    }
    @When("ver informe descuentos clientes de edad $edad")
    public void setEdad(String edadOrCualquiera) {
    	if ("cualquiera".equals(edadOrCualquiera))
    		edad=0; //edad minima a partir de la que se muestran resultados (asume edad no puede ser negativa)
    	else
    		edad=Integer.parseInt(edadOrCualquiera);
    }
    @Then("los descuentos son: $descuentos")
    public void getDescuentos(ExamplesTable descuentos) {
    	DescuentoModel model=new DescuentoModel();
    	List<DescuentoDisplayDTO> descuentosDTO=model.getListaDescuentos(edad);
    	//convierte la lista de DTOs utilizando una utilidad para convertir a csv, indicando los separadores de JBehave
    	String actual=Util.pojosToCsv(descuentosDTO, new String[] {"id","descuento"},true,"|","|","|","");
    	String expected=descuentos.asString();
    	assertEquals(expected,actual);
     }
}