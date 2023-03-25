package giis.demo.descuento.ut;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import giis.demo.descuento.DescuentoModel;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Pruebas del ejemplo de descuentos de clientes (Problema 3a) usando JUnitParams para parametrizar las pruebas.
 * Las pruebas en TestDescuentoFunction son repetitivas, diferenciandose solamente en los
 * valores a usar en la prueba.
 * Parametrizando estos valores se simplifica la prueba,
 * evitando incluir varios casos de prueba en un mismo metodo 
 * y se separan los datos de prueba del codigo a probar.
 * 
 * JUnit4 permite parametrizar las pruebas (https://github.com/junit-team/junit4/wiki/parameterized-tests),
 * pero lo realiza a nivel de todos los tests de una clase.
 * Existen otros componentes que simplifican y flexibilizan las pruebas como:
 * zohhak (https://github.com/piotrturski/zohhak) o
 * JUnitParams (https://github.com/Pragmatists/JUnitParams). Aqui se usara JUnitParams
 */
@RunWith(JUnitParamsRunner.class)
public class TestDescuentoFunctionParameters {
	/**
	 * Los parametros se pueden especificar en el codigo como un array de strings,
	 * con los diferentes valores separados por coma.
	 * Cada fila de datos se mapea a una ejecucion de este metodo con 
	 * los parametros en el orden en el que se han escrito
	 */
	@Test
	@Parameters({ 
		"15, true, false, false", 
		"20, true, true, false", 
		"0, false, false, false", 
		"20, false, true, false", 
		"10, false, false, true", 
		"30, false, true, true"})  
	public void testClientesParametrized(int expected, boolean nuevo, boolean cupon, boolean tarjeta) {
		DescuentoModel model=new DescuentoModel();
		assertEquals(expected,model.getDescuento(nuevo,cupon,tarjeta));
	}
	/**
	 * Los parametros tambien se pueden especificar en un fichero externo (csv)
	 */
	@Test
	@FileParameters("src/test/resources/test-parameters.csv")  
	public void testClientesParametrizedFile(int expected, boolean nuevo, boolean cupon, boolean tarjeta) {
		DescuentoModel model=new DescuentoModel();
		assertEquals(expected,model.getDescuento(nuevo,cupon,tarjeta));
	}

}
