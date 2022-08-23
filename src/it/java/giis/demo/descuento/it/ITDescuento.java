package giis.demo.descuento.it;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.junit.*;
import giis.demo.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Pruebas de la interaccion del usuario con la aplicacion swing del ejemplo de descuentos a clientes 
 * (Problema 3d)
 * utilizando AssertJ Swing: http://joel-costigliola.github.io/assertj/assertj-swing.html.
Disenyo (Situaciones a cubrir):
  Las definidas en las pruebas unitarias (Ejemplos 3b y 3c), mas:
<pre>
  Acciones Usuario
    Cambio filtro edad
      Se pone
      Se cambia valor
      Se quita
</pre>
Resulta en un caso de prueba que contiene una secuencia de cuatro pasos
 */
public class ITDescuento {
	private FrameFixture window; //ventana que esta siendo objeto de prueba en cada momento

	private Database db=new Database();
	@BeforeClass
	  public static void setUpOnce() {
	    FailOnThreadViolationRepaintManager.install();
	  }
	@Before
	public void setUp() {
		db.createDatabase(true);
		//Utiliza el mismo setup de datos que en los tests ut
		giis.demo.descuento.ut.TestDescuentoDatabase.loadCleanDatabase(db); 
		//Lanza main y selecciona la opcion para abrir la ventana bajo prueba (ver implementacion de este metodo)
		window=AssertjUtil.getApplicationFixture("Ejecutar giis.demo.descuento", "Descuento");
	}
	@After
	public void tearDown(){
		window.cleanUp();
	}
	/**
	 * Escenario de prueba de la pantalla. 
	 * Cuatro pasos cambiando el filtro por edad, comprobando en cada uno el valor de la tabla de descuentos
	 */
	@Test
	public void testDescuentoScenario() {
		AssertjUtil.takeScreenshot(window,"descuentoBefore");	
		//Paso1: estado inicial, no hay seleccion.
		window.textBox("txtAnyos").requireText("");
		AssertjUtil.takeScreenshot(window,"descuentoPaso1");	
		assertEquals("1,15\n2,20\n5,20\n6,10\n7,30\n", 
        		Util.arraysToCsv(window.table("tabDescuentos").contents()));
		//Paso2: incluir filtro a 40
		window.textBox("txtAnyos").setText("40");
		window.button("btnAplicarFiltro").click();
		AssertjUtil.takeScreenshot(window,"descuentoPaso2");
		assertEquals("5,20\n6,10\n", 
        		Util.arraysToCsv(window.table("tabDescuentos").contents()));
		//Paso3: pasar filtrro de 40 a 39
		window.textBox("txtAnyos").setText("39");
		window.button("btnAplicarFiltro").click();
		AssertjUtil.takeScreenshot(window,"descuentoPaso3");	
		assertEquals("5,20\n6,10\n7,30\n", 
        		Util.arraysToCsv(window.table("tabDescuentos").contents()));
		//Paso4: quitar filtro
		window.textBox("txtAnyos").setText("");
		window.button("btnAplicarFiltro").click();
		AssertjUtil.takeScreenshot(window,"descuentoPaso4");	
		assertEquals("1,15\n2,20\n5,20\n6,10\n7,30\n", 
        		Util.arraysToCsv(window.table("tabDescuentos").contents()));
	}
	
}
