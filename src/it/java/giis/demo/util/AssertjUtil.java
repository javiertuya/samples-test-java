package giis.demo.util;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

import java.io.File;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.image.ScreenshotTaker;

/**
 * Utilidades varias para uso en los tests de swing con AssertJ Swing
 */
public class AssertjUtil {

	/**
	 * Abre la ventana de aplicacion bajo test y obtiene el FrameFixture a utilizar en el resto de pasos.
	 * Se invocara en el setup.
	 * Abre primero la ventana giis.demo.util.SwingMain, selecciona la opcion correspondiente a la aplciacion
	 * y obtiene el FrameFixture de la ventana que se abre tras la seleccion.
	 * 
	 * @param mainButtonText Texto del boton en SwingMain que abre la ventana de la aplicacion bajo prueba
	 * @param applicationFrameName Nombre del frame tal como se ha definido en la vista de la aplicacion bajo prueba
	 * @return el FrameFixture que apunta a la ventana abierta
	 */
	public static FrameFixture getApplicationFixture(String mainButtonText, String applicationFrameName) {
		//a veces falla la localizacion de la ventana, espera un poco
		delay(2000);
		
		//instancia el lanzador (SwingMain) por el punto de entrada principal obteniendo su frame en window
		SwingMain mainClass = GuiActionRunner.execute(() -> new SwingMain());
		final FrameFixture menu = new FrameFixture(mainClass.getFrame());

		
		//da mas tiempo a a los eventos
		menu.robot().settings().delayBetweenEvents(200);
		menu.robot().settings().eventPostingDelay(200);

		menu.show();
		menu.focus();
		
		//Lanzo la ventana a probar (obtengo primero JButtonFixture, en lo sucesivo se hara junto con click)
		//la busqueda en AssertJ Swing se realiza por name, 
		//en el codigo generado por windowbuilder no se pone este atributo, busco por el texto
		//http://joel-costigliola.github.io/assertj/assertj-swing-lookup.html
		//delay(500);
		//JButtonFixture button=window.button(withText(mainButtonText));
		JButtonFixture button=GuiActionRunner.execute(() -> menu.button(withText(mainButtonText)));
		button.click();
		//delay(500);

		//Posiciono en el frame de la ventana que se acaba de abrir
		//https://joel-costigliola.github.io/assertj/swing/api/org/assertj/swing/finder/package-summary.html
		final FrameFixture window = WindowFinder.findFrame(applicationFrameName).using(menu.robot());
		//delay(500);
		window.focus();
		return window;
	}
	/**
	 * Toma una imagen del frame actual y lo guarda en target.
	 * 
	 * @param name nombre que se dara a la imagen (le anyade un timestamp para diferenciar imagenes guardadas en la misma sesion)
	 */
	public static void takeScreenshot(FrameFixture window, String name) {
		delay(100);
		String imageFolderPath = "target/screenshots";
		ScreenshotTaker screenshotTaker = new ScreenshotTaker();
		String timestamp = String.valueOf(System.currentTimeMillis()); //diferencia archivos en varias ejecuciones
		String fileName=imageFolderPath + "/" + timestamp + "-" + name + "-frame.png";
		new File(imageFolderPath).mkdir(); //asegura que existe la carpeta de screenshots
		screenshotTaker.saveComponentAsPng(window.target(), fileName);
		//Si se usa saveDesktopAsPng mostrara el escritorio completo
		delay(100);
	}
	/**
	 * Establece un tiempo de retraso para permitir visualizar el ui y dar tiempo a algunos eventos
	 */
	public static void delay(long millisecons) {
		try { Thread.sleep(millisecons); } catch (Exception e1) { } //NOSONAR
	}
}
