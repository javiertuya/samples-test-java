package giis.demo.descuento.ut;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import giis.demo.descuento.DescuentoModel;

/**
 * Pruebas del ejemplo de descuentos de clientes (Problema 3a) usando pruebas parametrizadas.
 * Las pruebas en TestDescuentoFunction son repetitivas, diferenciandose solamente en los valores a usar en la prueba.
 * Requiere la dependancia junit-jupiter-params
 */
public class TestDescuentoFunctionParameters {
	/**
	 * Los parametros se pueden especificar en el codigo como un array de strings,
	 * con los diferentes valores separados por coma.
	 * Cada fila de datos se mapea a una ejecucion de este metodo con 
	 * los parametros en el orden en el que se han escrito
	 * Si se prueba con JUnit4 la dependencia a usar es JUnitParams con las anotaciones @Test y @Parameters
	 */
	@ParameterizedTest
	@CsvSource({ 
		"15, true, false, false", 
		"20, true, true, false", 
		"0, false, false, false", 
		"20, false, true, false", 
		"10, false, false, true", 
		"30, false, true, true"})  
	public void testClientesParametrized(int expected, boolean nuevo, boolean cupon, boolean tarjeta) {
		DescuentoModel model = new DescuentoModel();
		assertEquals(expected, model.getDescuento(nuevo, cupon, tarjeta));
	}
	
	/**
	 * Los parametros tambien se pueden especificar en un fichero externo (csv)
	 */
	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/test-parameters.csv")
	public void testClientesParametrizedFile(int expected, boolean nuevo, boolean cupon, boolean tarjeta) {
		DescuentoModel model = new DescuentoModel();
		assertEquals(expected, model.getDescuento(nuevo, cupon, tarjeta));
	}

}
