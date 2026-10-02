package giis.demo.descuento.ut.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Ejecutor de los tests cucumber de este paquete
 */
@Suite
@IncludeEngines("cucumber")
@SelectPackages("giis.demo.descuento.ut.cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, 
	value = "pretty, html:target/reports/cucumber.html") // para mostrar los escenarios en la consola y html
// Especifica el glue (paquete con los steps) porque si no se indica, Cucumber busca los steps en todo el classpath:
// ademas de ser mas lento, puede fallar al cargar clases de otras librerias que dependen de clases no disponibles
// (p.e. sqlite-jdbc incluye clases que requieren GraalVM)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "giis.demo.descuento.ut.cucumber")
public class TestDescuentoRunner {
}
