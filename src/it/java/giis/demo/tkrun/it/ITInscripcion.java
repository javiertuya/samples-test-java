package giis.demo.tkrun.it;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.Assert.assertEquals;

import org.assertj.swing.data.TableCell ;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.finder.JOptionPaneFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import giis.demo.util.AssertjUtil;
import giis.demo.util.Database;
import giis.demo.util.Util;

/**
 * Pruebas de la interaccion del usuario con la aplicacion swing del ejemplo de Inscripciones en carreras populares
 * utilizando AssertJ Swing: http://joel-costigliola.github.io/assertj/assertj-swing.html.
 * Disenyo (Situaciones a cubrir):
<pre>
   Seleccion tabla carreras
     no hay seleccion
     seleccion de primera fila
     seleccion de ultima
     seleccion multiple (no permitido)
   Fila seleccionada al cambiar la tabla
     seleccion desaparece
     seleccion se mantiene
     seleccion cambia de fila en la tabla
   Validacion formato fecha
</pre>    
 */
public class ITInscripcion {
	private FrameFixture window; //ventana que esta siendo objeto de prueba en cada momento

	//valores esperados de la tabla de carreras y de los detalles de carreras a seleccionar
	private static final String CARRERAS4_CSV="101,en fase 3,(Abierta)\n102,en fase 2,(Abierta)\n103,en fase 1,(Abierta)\n104,antes inscripcion,\n";
	private static final String CARRERA_EMPTY="";
	private static final String CARRERA0="id,101\ninicio,2016-10-05\nfin,2016-10-25\nfecha,2016-11-10\ndescr,en fase 3\n";
	private static final String CARRERA3="id,104\ninicio,2016-11-11\nfin,2016-11-15\nfecha,2016-11-22\ndescr,antes inscripcion\n";

	private Database db=new Database();
	@BeforeClass
	  public static void setUpOnce() {
		// https://joel-costigliola.github.io/assertj/assertj-swing-edt.html
		FailOnThreadViolationRepaintManager.install();
	  }
	@Before
	public void setUp() {
		db.createDatabase(true);
		//Utiliza el mismo setup de datos que en los tests ut
		giis.demo.tkrun.ut.TestInscripcion.loadCleanDatabase(db); 
		//Lanza main y selecciona la opcion para abrir la ventana bajo prueba (ver implementacion de este metodo)
		window=AssertjUtil.getApplicationFixture("Ejecutar giis.demo.tkrun", "Carreras");
	}
	@After
	public void tearDown(){
		window.cleanUp();
	}
	/**
	 * Seleccion en la tabla de carreras.
	 * Comprueba detalles de la tabla de carreras, seleccion, detalle y porcentaje de descuento
	 */
	@Test
	public void testCarreraSelectList() {
		//estado inicial, no hay seleccion.
		//Asserj tiene asserts especificos (.require) para comprobar el estado del interfaz
		AssertjUtil.takeScreenshot(window,"carreraSelectList-Before");		
		window.table("tabCarreras").requireRowCount(4);
		window.table("tabCarreras").requireNoSelection();
		window.label("descuento").requireText("N/A");
		//tambien se pueden usar assert convencionales, p.e. para comprobar el contenido en forma de string csv
		//para facilitar las comparaciones
		assertEquals(CARRERAS4_CSV, Util.arraysToCsv(window.table("tabCarreras").contents()));
		assertEquals(CARRERA_EMPTY, Util.arraysToCsv(window.table("tabDetalle").contents()));
		
		//seleccion de ultima fila
		window.table("tabCarreras").selectRows(3,3);
		AssertjUtil.takeScreenshot(window,"carreraSelectList-SelectLast");		
		window.table("tabCarreras").requireRowCount(4);
		window.table("tabCarreras").requireSelectedRows(3);
		window.label("descuento").requireText("N/A");
		assertEquals(CARRERAS4_CSV, Util.arraysToCsv(window.table("tabCarreras").contents()));
		assertEquals(CARRERA3, Util.arraysToCsv(window.table("tabDetalle").contents()));
		
		//seleccion de primera fila
		window.table("tabCarreras").selectRows(0,0); //OJO, si es la primera SELECCIONA LA 1 y luego la 0!!
		AssertjUtil.takeScreenshot(window,"carreraSelectList-SelectFirst");		
		window.table("tabCarreras").requireRowCount(4);
		window.table("tabCarreras").requireSelectedRows(0);
		window.label("descuento").requireText("50%");
		assertEquals(CARRERAS4_CSV, Util.arraysToCsv(window.table("tabCarreras").contents()));
		assertEquals(CARRERA0, Util.arraysToCsv(window.table("tabDetalle").contents()));
		
		//seleccion multiple (no permitido)
		window.table("tabCarreras").selectRows(0,1,2);
		AssertjUtil.takeScreenshot(window,"carreraSelectList-SelectMultiple");		
		window.table("tabCarreras").requireSelectedRows(2);
		window.label("descuento").requireText("-30%"); //anyadido para probar un segundo valor ademas de N/A
		//para modificar valores (si se permitiese)
		//window.table("tabCarreras").cell(TableCell.row(1).column(1)).enterValue("XXX");

		AssertjUtil.takeScreenshot(window,"carreraSelectList-After");		
		}
	/**
	 * Cambio de tabla de carreras, seleccion desaparece
	 */
	@Test
	public void testCarreraSelectionDisappear() {
		//inicalmente hay cuatro filas, selecciona la primera
		AssertjUtil.takeScreenshot(window,"carreraSelectionDisappear-Before");		
		window.textBox("txtFechaHoy").requireText("2016-11-10");
		window.table("tabCarreras").selectRows(0,0);
		window.table("tabCarreras").requireRowCount(4);
		window.table("tabCarreras").requireSelectedRows(0);
		assertEquals(CARRERA0, Util.arraysToCsv(window.table("tabDetalle").contents()));
		
		//fila seleccionada desaparece, la tabla quedara sin seleccion
		window.textBox("txtFechaHoy").setText("2016-11-11");
		window.button(withText("Ver carreras en esta tabla")).click();
		AssertjUtil.takeScreenshot(window,"carreraSelectionDisappear-ViewForDate");		
		window.table("tabCarreras").requireRowCount(3);
		window.table("tabCarreras").requireNoSelection();
		//otra forma de comprobar los valores, el id de cada una de las filas
		window.table("tabCarreras").cell(TableCell.row(0).column(0)).requireValue("102");
		window.table("tabCarreras").cell(TableCell.row(1).column(0)).requireValue("103");
		window.table("tabCarreras").cell(TableCell.row(2).column(0)).requireValue("104");
		assertEquals(CARRERA_EMPTY, Util.arraysToCsv(window.table("tabDetalle").contents()));
		AssertjUtil.takeScreenshot(window,"carreraSelectionDisappear-After");		
	}
	/**
	 * Cambio de tabla de carreras, seleccion se mantiene, pero cambia de fila en la tabla
	 */
	@Test
	public void testCarreraSelectionKeep() {
		AssertjUtil.takeScreenshot(window,"carreraSelectionKeep-Before");		
		window.textBox("txtFechaHoy").setText("2016-11-11");
		window.button(withText("Ver carreras en esta tabla")).click();
		AssertjUtil.takeScreenshot(window,"carreraSelectionKeep-ViewForDate");		
		window.table("tabCarreras").selectRows(2,2);
		window.table("tabCarreras").requireSelectedRows(2);
		window.table("tabCarreras").cell(TableCell.row(2).column(0)).requireValue("104");
		assertEquals(CARRERA3, Util.arraysToCsv(window.table("tabDetalle").contents()));
		
		//fila seleccionada se mantiene, aunque ahora este en otra fila de la tabla
		window.textBox("txtFechaHoy").setText("2016-11-10");
		window.button(withText("Ver carreras en esta tabla")).click();
		AssertjUtil.takeScreenshot(window,"carreraSelectionKeep-ViewForOtherDate");		
		window.table("tabCarreras").requireRowCount(4);
		window.table("tabCarreras").requireSelectedRows(3);
		window.table("tabCarreras").cell(TableCell.row(3).column(0)).requireValue("104");
		assertEquals(CARRERA3, Util.arraysToCsv(window.table("tabDetalle").contents()));

		AssertjUtil.takeScreenshot(window,"carreraSelectionKeep-After");		
	}
	/**
	 * Validacion formato fecha
	 */
	@Test
	public void testCarreraValidate() {
		//con un elemento seleccionado pongo fecha formato incorrecto
		AssertjUtil.takeScreenshot(window,"ITcarreraFechaValidate-Before");		
		window.table("tabCarreras").selectRows(3,3);
		AssertjUtil.takeScreenshot(window,"ITcarreraFechaValidate-Select");		
		assertEquals(CARRERAS4_CSV, Util.arraysToCsv(window.table("tabCarreras").contents()));
		assertEquals(CARRERA3, Util.arraysToCsv(window.table("tabDetalle").contents()));
		window.textBox("txtFechaHoy").setText("2016-1110");
		window.button(withText("Ver carreras en esta tabla")).click();
		AssertjUtil.takeScreenshot(window,"ITcarreraFechaValidate-ViewForDate");		
		//aparece el dialogo con boton de aceptar (generado en el exceptionWrapper del controlador)
		JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().withTimeout(10000).using(window.robot());
		optionPane.requireTitle("Informacion")
			.requireMessage("Formato ISO incorrecto para fecha: 2016-1110")
			.buttonWithText("ACEPTAR").click();
		//los datos no cambian
		assertEquals(CARRERAS4_CSV, Util.arraysToCsv(window.table("tabCarreras").contents()));
		assertEquals(CARRERA3, Util.arraysToCsv(window.table("tabDetalle").contents()));

		AssertjUtil.takeScreenshot(window,"ITcarreraFechaValidate-After");		
	}
	
}
