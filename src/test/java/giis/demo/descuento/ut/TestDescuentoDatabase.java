package giis.demo.descuento.ut;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import giis.demo.descuento.DescuentoDisplayDTO;
import giis.demo.descuento.DescuentoModel;
import giis.demo.util.Database;
import giis.demo.util.Util;

/**
 * Pruebas del ejemplo de informe de descuentos de clientes leidos desde la base de datos (Problemas 3b y 3c).
 * Realiza las comparaciones directamente de estructuras bidimensionales (lista de atributos de objetos)
 * Situaciones a cubrir:
 * Las mismas que TestDescuentoFunction mas
<pre>
Filtrado por edad
  No especificado
  Cliente filtrado (en VL)
  Cliente no filtrado (en VL)
</pre>
 */
public class TestDescuentoDatabase {
	private static Database db=new Database();

	@BeforeClass
	public static void setUpClass() {
		//Aqui se pueden incluir inicializaciones comunes para toda la clase
	}
	@Before
	public void setUp() {
		db.createDatabase(true); //solo la creara la primera vez (para mejorar rendimiento)
		loadCleanDatabase(db); 
	}
	@After
	public void tearDown(){
		//aqui se cerrarian los objetos abiertos para el test, si no se ha hecho ya
	}
	
	/**
	 * Datos de prueba: base de datos definida para cubrir las situaciones del disenyo de la prueba.
	 * La base de datos se crea (si no existe) en el setup, pero debe limpiarse
	 * porque podrian quedar datos de tests ejecutados con anterioridad
	 */
	public static void loadCleanDatabase(Database db) {
		//Otra alternativa es utilizar un script externo con las queries y ejecutarlo con db.executeScript
		//(en este caso se pondria en src/test/resources)
		db.executeBatch(new String[] {
				"delete from clientes",
				"insert into clientes(id,edad,nuevo,cupon,tarjeta) values (1,18,'S','N','N')",
				"insert into clientes(id,edad,nuevo,cupon,tarjeta) values (2,38,'S','S','N')",
				"insert into clientes(id,edad,nuevo,cupon,tarjeta) values (3,21,'S','N','S')",
				"insert into clientes(id,edad,nuevo,cupon,tarjeta) values (4,25,'N','N','N')",
				"insert into clientes(id,edad,nuevo,cupon,tarjeta) values (5,40,'N','S','N')",
				"insert into clientes(id,edad,nuevo,cupon,tarjeta) values (6,42,'N','N','S')",
				"insert into clientes(id,edad,nuevo,cupon,tarjeta) values (7,39,'N','S','S')",
			});
	}
	
	/**
	 * Para la consulta sin parametros simplemente invoca el metodo del modelo que obtiene una lista de objetos (DTO)
	 * y la comparacion se realiza transformando estos con un metodo de utilidad que convierte la lista anterior
	 * a formato CSV.
	 */
	@Test
	public void testConsultaSinParametro() {
		DescuentoModel model=new DescuentoModel();
		List<DescuentoDisplayDTO> descuentos=model.getListaDescuentos();
        assertEquals("1,15\n"
        		+"2,20\n"
        		+"5,20\n"
        		+"6,10\n"
        		+"7,30\n", 
        		Util.pojosToCsv(descuentos,new String[] {"id","descuento"}));
 	}
	/**
	 * La misma forma de probar cuando hay parametros.
	 */
	@Test
	public void testConsultaConParametro() {
		DescuentoModel model=new DescuentoModel();
		List<DescuentoDisplayDTO> descuentos=model.getListaDescuentos(40);
        assertEquals("5,20\n"
        		+"6,10\n", 
        		Util.pojosToCsv(descuentos,new String[] {"id","descuento"}));
 	}

}
